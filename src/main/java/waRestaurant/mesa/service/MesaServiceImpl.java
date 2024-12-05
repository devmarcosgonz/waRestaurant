package waRestaurant.mesa.service;

import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.MESA_EXIST_ERROR;
import static waRestaurant.commons.ErrorsEnum.MESA_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.MESA_SAVE_ERROR;
import static waRestaurant.commons.ErrorsEnum.ORDER_SAVE_ERROR;
import static waRestaurant.mesa.domain.MesaState.ABIERTO;
import static waRestaurant.mesa.domain.MesaState.CERRADO;
import static waRestaurant.mesa.mapper.MesaMapper.mapMesaEntityToMesasDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waRestaurant.commons.CustomException;
import waRestaurant.commons.ErrorsEnum;
import waRestaurant.mesa.controller.MesaInput;
import waRestaurant.mesa.domain.DetailsDto;
import waRestaurant.mesa.domain.HeaderDto;
import waRestaurant.mesa.domain.MesaDto;
import waRestaurant.mesa.domain.MesasDto;
import waRestaurant.mesa.repository.MesaEntity;
import waRestaurant.mesa.repository.MesaRepository;
import waRestaurant.order.controller.OrderInput;
import waRestaurant.order.domain.OrderDetailsDto;
import waRestaurant.order.service.OrderService;
import waRestaurant.products.domain.ProductDto;
import waRestaurant.products.service.ProductService;

@Service
public class MesaServiceImpl implements MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    
    @Override
    public List<MesasDto> getAllMesas() {
        return mesaRepository.findAll().stream()
                .map(mapMesaEntityToMesasDto())
                .toList();
    }

  @Override
  public MesaDto getMesaById(String number) {
    MesaEntity mesa = mesaRepository.findByNumber(number)
        .orElseThrow(errorSupplier(MESA_NOT_FOUND));
    if (mesa.getState().equals(ABIERTO.name())) {
      OrderDetailsDto order = orderService.getAllOrdersByMesa(mesa.getId());
      return buildMesaDto(mesa, order);
    }
    return null;
  }

  private MesaDto buildMesaDto(MesaEntity mesa, OrderDetailsDto order) {
    return MesaDto.builder()
        .header(HeaderDto.builder()
            .mesaId(mesa.getId())
            .total(order.getTotalPrice())
            .initAt(order.getInitAt())
            .state(mesa.getState())
            .clientId(order.getClient().getClientId())
            .build())
        .details(order.getProducts().stream()
            .map(products -> DetailsDto.builder()
                .productId(products.getProductId())
                .quantity(products.getQuantity())
                .build())
            .toList())
        .build();
  }

  @Override
  public void createMesa(String mesaNumber) {
    try {
      boolean exist = mesaRepository.existsByNumber(mesaNumber);
      if (!exist) {
        mesaRepository.save(MesaEntity.builder()
            .number(mesaNumber)
            .state(CERRADO.name())
            .build());
      } else {
        throw throwError(MESA_EXIST_ERROR);
      }
    } catch (Exception e) {
      throw throwError(MESA_SAVE_ERROR, "Error saving mesa", e);
    }
  }

    @Override
    public void closeMesa(String number) {
        mesaRepository.findByNumber(number)
                .ifPresentOrElse(mesaEntity -> {
                    if (mesaEntity.getState().equalsIgnoreCase(ABIERTO.name())) {
                        boolean existOrder = orderService.existOrderEntity(mesaEntity.getId());
                        if (existOrder) {
                            orderService.closeOrder(mesaEntity.getId());
                        }
                        mesaRepository.save(mesaEntity.toBuilder().state(CERRADO.name()).build());
                    }
                }, () -> {
                    throw new CustomException(MESA_NOT_FOUND);
                });

    }

    @Override
    public void assignMesa(MesaInput mesaInput) {
        try {
            MesaEntity mesa = mesaRepository.findByNumberAndState(mesaInput.getNumber(), CERRADO.name())
                    .orElseThrow(errorSupplier(MESA_NOT_FOUND));
            orderService.createOrder(mesa.getId(), mesaInput.getClient());
            mesaRepository.save(mesa.toBuilder().state(ABIERTO.name()).build());
        } catch (Exception e) {
            throw throwError(ORDER_SAVE_ERROR, "Error saving order", e);
        }
    }

    @Override
    public void changeClientMesa(MesaInput mesaInput) {
        mesaRepository.findByNumber(mesaInput.getNumber())
                .ifPresentOrElse(mesaEntity -> {
                    if (mesaEntity.getState().equalsIgnoreCase(ABIERTO.name())) {
                        orderService.updateOrderChangeClient(mesaEntity.getId(), mesaInput.getClient());
                    }
        }, () -> {throw new CustomException(MESA_NOT_FOUND);});
    }

    @Override
    @Transactional
    public void assignConsumptionToMesa(String mesaNumber, List<DetailsDto> details) {
        MesaEntity mesa = mesaRepository.findByNumber(mesaNumber)
                .orElseThrow(() -> new CustomException(MESA_NOT_FOUND));

        if (!mesa.getState().equals(ABIERTO.name())) {
            throw new CustomException(ErrorsEnum.MESA_NOT_FOUND);
        }
        for (DetailsDto detail : details) {
            productService.updateStock(detail.getProductId(), detail.getQuantity());
            OrderInput orderInput = OrderInput.builder()
                    .mesaId(mesa.getId())
                    .productId(detail.getProductId())
                    .quantity(detail.getQuantity())
                    .build();

            orderService.addOrder(orderInput);
        }
    }
}

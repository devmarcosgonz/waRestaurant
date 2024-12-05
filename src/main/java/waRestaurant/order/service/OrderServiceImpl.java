package waRestaurant.order.service;

import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.CLIENT_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.ORDER_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.ORDER_SAVE_ERROR;
import static waRestaurant.commons.ErrorsEnum.PRODUCT_NOT_FOUND;
import static waRestaurant.order.mapper.OrderMapper.mapOrderEntityToOrderDetailsDto;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waRestaurant.client.repository.ClientEntity;
import waRestaurant.client.repository.ClientRepository;
import waRestaurant.commons.CustomException;
import waRestaurant.order.controller.OrderInput;
import waRestaurant.order.domain.OrderDetailsDto;
import waRestaurant.order.repository.OrderDetailsEntity;
import waRestaurant.order.repository.OrderDetailsRepository;
import waRestaurant.order.repository.OrderEntity;
import waRestaurant.order.repository.OrderRepository;
import waRestaurant.products.repository.ProductEntity;
import waRestaurant.products.repository.ProductRepository;
import waRestaurant.products.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDetailsRepository repository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ProductService productService;

  @Override
  public boolean existOrderEntity(Long mesaId) {
    return orderRepository.existsByMesaId(mesaId);
  }

  @Override
  public OrderDetailsDto getAllOrdersByMesa(Long mesaId) {
    return orderRepository.findByMesaIdAndCloseAtIsNull(mesaId)
        .map(mapOrderEntityToOrderDetailsDto())
        .map(OrderDetailsDto::calculateTotalPrice)
        .orElseThrow(errorSupplier(ORDER_NOT_FOUND));
  }

  @Override
  public void createOrder(Long mesaId, Long clientId) {
    try {
      ClientEntity client = clientRepository.findById(clientId)
          .orElseThrow(errorSupplier(CLIENT_NOT_FOUND));
      if (client != null) {
        orderRepository.save(buildOrder(mesaId, client));
      }
    } catch (Exception e) {
      throw throwError(ORDER_SAVE_ERROR, "Error saving order", e);
    }
  }

  @Override
  public void addOrder(OrderInput order) {
    Optional<OrderEntity> orderEntity = orderRepository.findByMesaIdAndCloseAtIsNull(order.getMesaId());
    if (orderEntity.isPresent()) {
      ProductEntity product = productRepository.findById(order.getProductId())
          .orElseThrow(errorSupplier(PRODUCT_NOT_FOUND));
      repository.save(buildOrderDetails(orderEntity.get(), product, order));
      productService.updateStock(product.getId(), order.getQuantity());
    } else {
      throw throwError(ORDER_NOT_FOUND);
    }
  }

  @Override
  public void updateOrderChangeClient(Long mesaId, Long clientId) {
    Optional<OrderEntity> orderEntity = orderRepository.findByMesaIdAndCloseAtIsNull(mesaId);
    if (orderEntity.isPresent()) {
      ClientEntity client = clientRepository.findById(clientId)
          .orElseThrow(errorSupplier(CLIENT_NOT_FOUND));
      orderRepository.save(orderEntity.get().toBuilder().client(client).build());
    } else {
      throw throwError(ORDER_NOT_FOUND);
    }
  }

  @Override
  public void closeOrder(Long mesaId) {
    orderRepository.findByMesaIdAndCloseAtIsNull(mesaId)
        .ifPresentOrElse(orderEntity -> {
              orderRepository.save(orderEntity.toBuilder().closeAt(LocalDateTime.now()).build());
            },
            () -> {throw new CustomException(ORDER_NOT_FOUND);}
        );
  }

  private OrderDetailsEntity buildOrderDetails(OrderEntity orderEntity, ProductEntity product,  OrderInput order) {
    return OrderDetailsEntity.builder()
        .order(orderEntity.getId())
        .product(product)
        .quantity(order.getQuantity())
        .build();
  }

  private OrderEntity buildOrder(Long mesaId, ClientEntity client) {
    return OrderEntity.builder()
        .mesaId(mesaId)
        .client(client)
        .createdAt(LocalDateTime.now())
        .build();
  }

  @Override
  public void deleteOrder(Long orderDetail) {
    repository.findById(orderDetail)
        .ifPresentOrElse(
            repository::delete,
            () -> { throw new CustomException(ORDER_NOT_FOUND); });
  }
}

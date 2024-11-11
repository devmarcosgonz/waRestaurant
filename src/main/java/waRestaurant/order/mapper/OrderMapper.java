package waRestaurant.order.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import waRestaurant.mesa.domain.MesasDto;
import waRestaurant.mesa.repository.MesaEntity;
import waRestaurant.order.domain.OrderClientDto;
import waRestaurant.order.domain.OrderDetailsDto;
import waRestaurant.order.domain.OrderProductDto;
import waRestaurant.order.repository.OrderDetailsEntity;
import waRestaurant.order.repository.OrderEntity;

public class OrderMapper {

  public static Function<OrderEntity, OrderDetailsDto> mapOrderEntityToOrderDetailsDto() {
    return orderEntity ->  OrderDetailsDto.builder()
        .client(OrderClientDto.builder()
            .clientId(orderEntity.getClient().getId())
            .name(orderEntity.getClient().getName())
            .build())
        .initAt(orderEntity.getCreatedAt())
        .products(orderEntity.getOrderDetails().stream()
            .map(mapOrderDetailsEntityToOrderProductDto())
            .toList())
        .build();
  }

  public static Function<OrderDetailsEntity, OrderProductDto> mapOrderDetailsEntityToOrderProductDto() {
    return orderDetailsEntity -> OrderProductDto.builder()
        .productId(orderDetailsEntity.getProduct().getId())
        .name(orderDetailsEntity.getProduct().getName())
        .quantity(orderDetailsEntity.getQuantity())
        .price(orderDetailsEntity.getProduct().getPrice())
        .build();
  }
}

package waRestaurant.order.service;

import waRestaurant.order.controller.OrderInput;
import waRestaurant.order.domain.OrderDetailsDto;
import waRestaurant.order.domain.CommandsDto;

public interface OrderService {

  boolean existOrderEntity(Long mesaId);

  OrderDetailsDto getAllOrdersByMesa(Long mesaId);

  void createOrder(Long mesaId, Long clientId);

  CommandsDto addOrder(OrderInput order);

  void updateOrderChangeClient(Long mesaId, Long clientId);

  void closeOrder(Long mesaId);

  void deleteOrder(Long id);

}

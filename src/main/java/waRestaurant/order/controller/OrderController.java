package waRestaurant.order.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import waRestaurant.order.service.OrderService;
import waRestaurant.order.domain.CommandsDto;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping
  public ResponseEntity<Object> getAllOrderByMesa(@RequestParam Long number) {
    return ResponseEntity.ok(orderService.getAllOrdersByMesa(number));
  }


  @PostMapping
  public ResponseEntity<CommandsDto> addOrder(@RequestBody @Valid OrderInput order) {
    return ResponseEntity.ok(orderService.addOrder(order));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMesa(@PathVariable Long id) {
    orderService.deleteOrder(id);
  }
}

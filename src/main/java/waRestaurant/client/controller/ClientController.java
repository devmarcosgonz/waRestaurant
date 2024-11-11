package waRestaurant.client.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waRestaurant.client.domain.ClientDto;
import waRestaurant.client.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @GetMapping
  public ResponseEntity<List<ClientDto>> getAllClients(@RequestParam(required = false) String name) {
    return ResponseEntity.ok(clientService.getAllClients(name));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
    return ResponseEntity.of(Optional.of(clientService.getClientById(id)));
  }

  @PostMapping
  public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientInput client) {
    return ResponseEntity.ok(clientService.createClient(client));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientDto> updateCategory(@PathVariable Long id,
                                                    @RequestBody ClientInput client) {
    return ResponseEntity.ok(clientService.updateClient(id, client));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCategory(@PathVariable Long id) {
    clientService.deleteClient(id);
  }
}

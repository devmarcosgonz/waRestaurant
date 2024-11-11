package waRestaurant.client.service;

import java.util.List;
import waRestaurant.client.controller.ClientInput;
import waRestaurant.client.domain.ClientDto;

public interface ClientService {

  List<ClientDto> getAllClients();

  ClientDto getClientById(Long id);

  ClientDto createClient(ClientInput client);

  ClientDto updateClient(Long id, ClientInput client);

  void deleteClient(Long id);
}

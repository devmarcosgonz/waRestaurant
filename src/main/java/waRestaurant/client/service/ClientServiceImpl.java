package waRestaurant.client.service;

import static waRestaurant.client.mapper.ClientMapper.mapClientEntityToClientDto;
import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.CLIENT_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.CLIENT_SAVE_ERROR;
import static waRestaurant.commons.ErrorsEnum.CLIENT_UPDATE_ERROR;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waRestaurant.client.controller.ClientInput;
import waRestaurant.client.domain.ClientDto;
import waRestaurant.client.repository.ClientEntity;
import waRestaurant.client.repository.ClientRepository;
import waRestaurant.commons.CustomException;
import waRestaurant.commons.ErrorsEnum;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public List<ClientDto> getAllClients(String name) {
    return clientRepository.findAllByNameLike(name).stream()
        .map(mapClientEntityToClientDto())
        .toList();
  }

  @Override
  public ClientDto getClientById(Long id) {
    return clientRepository.findById(id)
        .map(mapClientEntityToClientDto())
        .orElseThrow(errorSupplier(CLIENT_NOT_FOUND));
  }

  @Override
  @Transactional
  public ClientDto createClient(ClientInput client) {
    return buildClientEntity(client)
        .map(clientRepository::save)
        .map(mapClientEntityToClientDto())
        .orElseThrow(errorSupplier(CLIENT_SAVE_ERROR));
  }

  @Override
  public ClientDto updateClient(Long id, ClientInput client) {
    return clientRepository.findById(id)
        .map(updateClientValues(client))
        .map(clientRepository::save)
        .map(mapClientEntityToClientDto())
        .orElseThrow(errorSupplier(CLIENT_UPDATE_ERROR));
  }

  @Override
  public void deleteClient(Long id) {
    try {
      clientRepository.findById(id)
          .ifPresentOrElse(
              clientRepository::delete,
              () -> { throw new CustomException(CLIENT_NOT_FOUND); });
    } catch (Exception e) {
      throw throwError(ErrorsEnum.CLIENT_DELETE_ERROR, "Error while deleting client", e);
    }
  }

  private Optional<ClientEntity> buildClientEntity(ClientInput client) {
    return Optional.of(ClientEntity.builder()
            .name(client.getName())
            .phone(client.getPhone())
            .email(client.getEmail())
        .build());
  }

  private Function<ClientEntity, ClientEntity> updateClientValues(ClientInput client) {
    return clientEntity -> clientEntity.toBuilder()
        .name(client.getName() != null ? client.getName() : clientEntity.getName())
        .phone(client.getPhone() != null ? client.getPhone() : clientEntity.getPhone())
        .email(client.getEmail() != null ? client.getEmail() : clientEntity.getEmail())
        .build();
  }
}

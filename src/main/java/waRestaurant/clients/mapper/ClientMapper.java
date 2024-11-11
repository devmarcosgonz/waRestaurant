package waRestaurant.client.mapper;

import java.util.function.Function;
import waRestaurant.client.domain.ClientDto;
import waRestaurant.client.repository.ClientEntity;

public class ClientMapper {

  public static Function<ClientEntity, ClientDto> mapClientEntityToClientDto() {
    return clientEntity ->  ClientDto.builder()
        .clientId(clientEntity.getId())
        .name(clientEntity.getName())
        .phone(clientEntity.getPhone())
        .email(clientEntity.getEmail())
        .build();
  }
}

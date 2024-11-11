package waRestaurant.mesa.mapper;

import java.util.function.Function;
import waRestaurant.mesa.domain.MesasDto;
import waRestaurant.mesa.repository.MesaEntity;

public class MesaMapper {

  public static Function<MesaEntity, MesasDto> mapMesaEntityToMesasDto() {
    return mesaEntity ->  MesasDto.builder()
        .id(mesaEntity.getId())
        .number(mesaEntity.getNumber())
        .state(mesaEntity.getState())
        .build();
  }
}

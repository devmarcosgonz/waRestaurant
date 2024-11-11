package waRestaurant.mesa.service;

import java.util.List;
import waRestaurant.mesa.controller.MesaInput;
import waRestaurant.mesa.domain.MesaDto;
import waRestaurant.mesa.domain.MesasDto;

public interface MesaService {

  List<MesasDto> getAllMesas();

  MesaDto getMesaById(String number);

  void createMesa(String number);

  void closeMesa(String number);

  void assignMesa(MesaInput mesaInput);

  void changeClientMesa(MesaInput mesaInput);
}

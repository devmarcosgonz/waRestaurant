package waRestaurant.mesa.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import waRestaurant.mesa.domain.MesaDto;
import waRestaurant.mesa.domain.MesasDto;
import waRestaurant.mesa.service.MesaService;

@RestController
@RequestMapping("/mesas")
public class MesaController {

  @Autowired
  private MesaService mesaService;

  @GetMapping
  public ResponseEntity<List<MesasDto>> getAllMesas() {
    return ResponseEntity.ok(mesaService.getAllMesas());
  }

  @GetMapping("/{number}")
  public ResponseEntity<Object> getMesaByNumber(@PathVariable String number) {
    MesaDto mesa = mesaService.getMesaById(number);
    if (mesa == null) {
      return ResponseEntity.ok("La mesa se encuentra disponible, puede ser asignada a un nuevo cliente");
    }
    return ResponseEntity.of(Optional.of(mesa));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createMesa(@RequestParam String number) {
    mesaService.createMesa(number);
  }

  @PostMapping("/assign")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMesa(@RequestBody MesaInput mesaInput) {
    mesaService.assignMesa(mesaInput);
  }

  @PutMapping("/change-client")
  @ResponseStatus(HttpStatus.OK)
  public void closeMesa(@RequestBody MesaInput mesaInput) {
    mesaService.changeClientMesa(mesaInput);
  }

  @PutMapping("/{number}/close")
  @ResponseStatus(HttpStatus.OK)
  public void closeMesa(@PathVariable String number) {
    mesaService.closeMesa(number);
  }

}

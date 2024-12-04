package waRestaurant.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waRestaurant.reservation.domain.ReservationDto;
import waRestaurant.reservation.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getReservations(
            @RequestParam String mesaNumber,
            @RequestParam(value = "start", required = false) LocalDateTime start,
            @RequestParam(value = "end", required = false) LocalDateTime end) {
        return ResponseEntity.ok(reservationService.getReservations(mesaNumber, start, end));
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationInput reservation) {
        return ResponseEntity.ok(reservationService.createReservation(reservation));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ReservationDto> confirmReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.confirmReservation(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}

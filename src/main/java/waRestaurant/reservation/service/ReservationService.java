package waRestaurant.reservation.service;

import waRestaurant.reservation.controller.ReservationInput;
import waRestaurant.reservation.domain.ReservationDto;
import waRestaurant.reservation.repository.ReservationEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<ReservationDto> getReservations(String mesaNumber, LocalDateTime start, LocalDateTime end);
    ReservationDto createReservation(ReservationInput reservation);
    ReservationDto confirmReservation(Long id);
    void cancelReservation(Long id);
    ReservationDto getReservation(String mesaNumber);

}

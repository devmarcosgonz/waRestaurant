package waRestaurant.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waRestaurant.client.repository.ClientRepository;
import waRestaurant.mesa.repository.MesaEntity;
import waRestaurant.mesa.repository.MesaRepository;
import waRestaurant.reservation.controller.ReservationInput;
import waRestaurant.reservation.domain.ReservationDto;
import waRestaurant.reservation.repository.ReservationEntity;
import waRestaurant.reservation.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.RESERVATION_NOT_FOUND;
import static waRestaurant.reservation.mapper.ReservationMapper.mapReservationEntityToReservationDto;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public List<ReservationDto> getReservations(String mesaNumber, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findAllByMesaAndReservationDateBetween(mesaNumber, start, end)
                .stream()
                .map(mapReservationEntityToReservationDto())
                .toList();
    }

    @Override
    @Transactional
    public ReservationDto createReservation(ReservationInput reservation) {

        return buildReservationEntity(reservation)
                .map(reservationRepository::save)
                .map(mapReservationEntityToReservationDto())
                .orElseThrow();
    }

    @Override
    @Transactional
    public ReservationDto confirmReservation(Long id) {
        return reservationRepository.findById(id)
                .map(updateStatusReservationEntity("CONFIRMADO"))
                .map(reservationRepository::save)
                .map(mapReservationEntityToReservationDto())
                .orElseThrow(errorSupplier(RESERVATION_NOT_FOUND));
    }

    @Override
    @Transactional
    public void cancelReservation(Long id) {
        try {
            reservationRepository.findById(id)
                    .map(updateStatusReservationEntity("CANCELADO"))
                    .map(reservationRepository::save);
        } catch (Exception e) {
            throw throwError(RESERVATION_NOT_FOUND, "Error while cancel reservation", e);
        }
    }

    @Override
    public ReservationDto getReservation(String mesaNumber) {
        /*Metodo para consultar desde mesas*/
        return null;
    }

    private Optional<ReservationEntity> buildReservationEntity(ReservationInput reservation) {
        return Optional.of(ReservationEntity.builder()
        .client(clientRepository.findById(reservation.getClientId()).get())
        .mesa(mesaRepository.findByNumber(reservation.getMesaNumber()).get())
        .reservationDate(reservation.getReservationDate())
        .status("PENDIENTE")
        .build());
    }

    private Function<ReservationEntity, ReservationEntity> updateStatusReservationEntity(String status) {
        return reservationEntity -> reservationEntity.toBuilder()
                .status(status)
                .build();
    }
}


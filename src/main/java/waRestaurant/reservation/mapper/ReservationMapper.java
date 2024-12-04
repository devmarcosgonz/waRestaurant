package waRestaurant.reservation.mapper;

import waRestaurant.reservation.domain.ReservationDto;
import waRestaurant.reservation.repository.ReservationEntity;

import java.util.function.Function;

public class ReservationMapper {
    public static Function<ReservationEntity, ReservationDto> mapReservationEntityToReservationDto() {
        return reservationEntity -> ReservationDto.builder()
                .id(reservationEntity.getId())
                .clientName(reservationEntity.getClient().getName())
                .clientPhone(reservationEntity.getClient().getPhone())
                .reservationDate(reservationEntity.getReservationDate())
                .mesaNumber(reservationEntity.getMesa().getNumber())
                .status(reservationEntity.getStatus())
                .build();
    }
}

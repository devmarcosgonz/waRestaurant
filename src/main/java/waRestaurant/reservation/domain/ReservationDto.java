package waRestaurant.reservation.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationDto {
    private Long id;
    private String clientName;
    private String clientPhone;
    private LocalDateTime reservationDate;
    private String mesaNumber;
    private String status;
}

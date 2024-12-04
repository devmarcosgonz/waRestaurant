package waRestaurant.reservation.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationInput {
    @NotBlank
    private String mesaNumber;
    @NotNull
    private Long clientId;
    @NotNull
    private LocalDateTime reservationDate;
    @NotNull
    private Long quantity;
}

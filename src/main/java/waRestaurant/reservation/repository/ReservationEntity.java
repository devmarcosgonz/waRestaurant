package waRestaurant.reservation.repository;

import jakarta.persistence.*;
import lombok.*;
import waRestaurant.client.repository.ClientEntity;
import waRestaurant.mesa.repository.MesaEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_reservation")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private ClientEntity client;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name = "quantity_client")
    private int quantityClient;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private MesaEntity mesa;

    private String status;
}


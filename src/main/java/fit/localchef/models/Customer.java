package fit.localchef.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer loyaltyPoints;

    @Column
    private String defaultDeliveryInstructions;
}

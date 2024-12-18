package fit.localchef.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role; // CUSTOMER or CHEF

    @Column
    private Integer userId; // Can be either a Customer or Chef ID

    @Column
    private String streetAddress1;

    @Column
    private String streetAddress2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String postalCode;

    @Column
    private String country;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;
}


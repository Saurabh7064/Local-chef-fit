package fit.localchef.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Meal meal;

    @ManyToOne
    private Chef chef;
    @Column
    private Integer quantity;
    @Column
    private Double price;
}

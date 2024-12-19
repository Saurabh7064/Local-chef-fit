package fit.localchef.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders") // Rename the table to 'orders'
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    @Column
    private Double totalAmount;
    @Column
    private String paymentStatus; // PENDING, COMPLETED, FAILED
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
}

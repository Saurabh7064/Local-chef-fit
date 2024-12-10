package fit.localchef.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chef_id", nullable = false)
    @JsonIgnore
    private Chef chef;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(nullable = false)
    private String cuisineType;

    @Column
    private Double pricePerServing;

    @Column
    private Boolean glutenFree;
    @Column
    private Boolean vegetarian;
    @Column
    private Boolean vegan;
    @Column
    private String spiceLevel;
    @Column
    private String imageUrl;
    @Column
    private Instant createdAt;
    @Column
    private Instant updatedAt;
}

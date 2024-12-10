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
@Table
public class Chef {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn
    private User user;

    @Column
    private String profileImageUrl;

    @Column
    private String bio;

    @Column
    private Double rating;

    @Column
    private String cuisineSpecialties;

    @Column
    private String contactEmail;

    @Column
    private String phoneNumber;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Meal> meals;
}

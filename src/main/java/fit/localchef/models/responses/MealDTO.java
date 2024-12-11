package fit.localchef.models.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private Integer id;
    private String name;
    private String description;
    private String cuisineType;
    private Double pricePerServing;
    private Boolean glutenFree;
    private Boolean vegetarian;
    private Boolean vegan;
    private String spiceLevel;
    private String imageUrl;
    private Instant createdAt;
    private Instant updatedAt;

}

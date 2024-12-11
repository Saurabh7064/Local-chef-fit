package fit.localchef.models.responses;

import fit.localchef.models.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChefDTO {

    private Integer id;
    private String name;
    private String profileImageUrl;
    private String bio;
    private Double rating;
    private String cuisineSpecialties;
    private String contactEmail;
    private String phoneNumber;
    private List<MealDTO> meals;  // List of meal DTOs



}


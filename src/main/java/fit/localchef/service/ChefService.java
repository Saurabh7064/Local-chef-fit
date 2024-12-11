package fit.localchef.service;

import fit.localchef.models.Chef;
import fit.localchef.models.responses.ChefDTO;
import fit.localchef.models.responses.MealDTO;
import fit.localchef.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    public List<ChefDTO> getAllChefsWithMeals() {
        List<Chef> chefs = chefRepository.findAll();
        return chefs.stream()
                .map(chef -> new ChefDTO(
                        chef.getId(),
                        chef.getUser().getUsername(),  // Name from User
                        chef.getProfileImageUrl(),
                        chef.getBio(),
                        chef.getRating(),
                        chef.getCuisineSpecialties(),
                        chef.getContactEmail(),
                        chef.getPhoneNumber(),
                        chef.getMeals().stream()
                                .map(meal -> new MealDTO(
                                        meal.getId(),
                                        meal.getName(),
                                        meal.getDescription(),
                                        meal.getCuisineType(),
                                        meal.getPricePerServing(),
                                        meal.getGlutenFree(),
                                        meal.getVegetarian(),
                                        meal.getVegan(),
                                        meal.getSpiceLevel(),
                                        meal.getImageUrl(),
                                        meal.getCreatedAt(),
                                        meal.getUpdatedAt()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }


}

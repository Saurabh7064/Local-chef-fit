package fit.localchef.service;

import fit.localchef.models.Chef;
import fit.localchef.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    // Method to fetch all chefs with their meals
    public List<Chef> getAllChefsWithMeals() {
        // Using the fetch strategy to load meals along with chefs
        return chefRepository.findAll(); // This assumes you have set up a lazy or eager loading for meals.
    }
}

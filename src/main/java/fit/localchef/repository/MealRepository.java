package fit.localchef.repository;

import fit.localchef.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    // Find all meals by a specific chef ID
    List<Meal> findByChefId(Integer chefId);

    // Find meals by name (case insensitive search)
    List<Meal> findByNameContainingIgnoreCase(String name);

    // Find all meals below a certain price
    List<Meal> findByPricePerServingLessThanEqual(Double price);

    // Find all vegetarian meals
    List<Meal> findByVegetarianTrue();

    // Find all vegan meals
    List<Meal> findByVeganTrue();

    // Find all gluten-free meals
    List<Meal> findByGlutenFreeTrue();
}


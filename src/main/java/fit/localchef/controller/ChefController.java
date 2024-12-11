package fit.localchef.controller;

import fit.localchef.models.responses.ChefDTO;
import fit.localchef.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChefController {

    @Autowired
    private ChefService chefService;

    // Endpoint to list all chefs and their meals
    @GetMapping("/api/v1/auth/chefs")
    public List<ChefDTO> getAllChefs() {
        return chefService.getAllChefsWithMeals();
    }
}

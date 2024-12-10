package fit.localchef.repository;

import fit.localchef.models.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Integer> {
    // JpaRepository provides methods like findAll(), save(), etc.
}

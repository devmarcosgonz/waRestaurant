package waRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import waRestaurant.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}


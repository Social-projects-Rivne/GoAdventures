package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
    Category findByEventsId(int id);
    Long countByCategoryName(String name);
}


package io.softserve.goadventures.event.repository;

import io.softserve.goadventures.event.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);
}


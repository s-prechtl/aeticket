package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT COALESCE(sum(ce.amount), 0) FROM CartEntry ce WHERE ce.category = :category")
    Integer unavailableTickets(@Param("category") Category category);
}

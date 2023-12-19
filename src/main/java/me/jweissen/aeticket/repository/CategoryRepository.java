package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.stock - sum(ce.amount) FROM Category c INNER JOIN CartEntry ce ON c = ce.category WHERE c = :category GROUP BY c")
    Integer availableTickets(@Param("category") Category category);
}

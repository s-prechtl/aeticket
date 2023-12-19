package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT sum(ce.amount * c.price) FROM CartEntry ce INNER JOIN Category c ON ce.category = c WHERE ce.cart = :cart")
    Integer getCheckoutPrice(@Param("cart") Cart cart);
}

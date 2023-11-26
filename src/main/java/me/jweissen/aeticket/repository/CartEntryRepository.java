package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartEntryRepository extends JpaRepository<CartEntry, Integer> {
    @Query("SELECT ce FROM CartEntry ce WHERE ce.cart.id = :cartId AND ce.category.event.id = :eventId")
    List<CartEntry> getByCartAndEvent(@Param("cartId") Long cartId, @Param("eventId") Long eventId);
}

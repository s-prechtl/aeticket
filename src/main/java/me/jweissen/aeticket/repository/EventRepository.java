package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.start > CURRENT_TIMESTAMP")
    List<Event> findAllFuture();
}

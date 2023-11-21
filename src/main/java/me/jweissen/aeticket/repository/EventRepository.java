package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

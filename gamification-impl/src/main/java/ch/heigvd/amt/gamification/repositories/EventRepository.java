package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
}

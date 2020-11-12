package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.ScoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<ScoreEntity, Long> {
}

package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.ScoreScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface ScoreScaleRepository extends CrudRepository<ScoreScaleEntity, Long> {
}

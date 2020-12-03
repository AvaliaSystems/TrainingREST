package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {
}

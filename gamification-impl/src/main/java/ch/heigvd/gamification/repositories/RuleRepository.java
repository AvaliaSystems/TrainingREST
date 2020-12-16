package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.BadgeEntity;
import ch.heigvd.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<RuleEntity,Long> {
    Iterable<RuleEntity> findByApplicationEntity_ApiKey(String applicationEntity_apiKey);
    Iterable<RuleEntity> findByEventType(String eventType);
}

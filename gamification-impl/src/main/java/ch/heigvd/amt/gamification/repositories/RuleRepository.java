package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.BadgeEntity;
import ch.heigvd.amt.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {
    List<RuleEntity> findAllByApplicationEntity(ApplicationEntity applicationEntity);
    List<RuleEntity> findAllByApplicationEntityAndType(ApplicationEntity applicationEntity, String type);
}

package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.api.model.Badge;
import ch.heigvd.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    Iterable<BadgeEntity> findByApplicationEntity_ApiKey(String applicationEntity_apiKey);
}

package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    Iterable<BadgeEntity> findByApplicationEntity_ApiKey(String applicationEntity_apiKey);
    BadgeEntity findByApplicationEntity_ApiKeyAndName(String applicationEntity_apiKey,String badgeName);
}

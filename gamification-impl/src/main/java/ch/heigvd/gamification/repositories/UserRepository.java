package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.BadgeEntity;
import ch.heigvd.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Iterable<UserEntity> findByApplicationEntity_ApiKey(String applicationEntity_apiKey);
    Optional<UserEntity> findByApplicationEntity_ApiKeyAndUsername(String applicationEntity_apiKey, String username);
}

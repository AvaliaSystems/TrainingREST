package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, UUID> {
    ApplicationEntity findByApiKey(UUID apiKey);
}

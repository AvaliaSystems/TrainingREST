package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, UUID> {
    ApplicationEntity findByApiKey(String apiKey);
}

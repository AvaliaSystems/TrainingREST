package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.ApplicationRegistrationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRegistrationRepository extends CrudRepository<ApplicationRegistrationEntity, Long> {
}

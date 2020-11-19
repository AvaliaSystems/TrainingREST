package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity,Long> {
}

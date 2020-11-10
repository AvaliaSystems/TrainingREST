package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}

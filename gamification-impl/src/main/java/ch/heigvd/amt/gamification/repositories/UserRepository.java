package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.api.model.User;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.BadgeEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAllByApplication(ApplicationEntity applicationEntity);
    UserEntity findByUserId(String userId);
    // TODO : Optimiser en ne passant que l'applicationId et pas toute l'application?
    UserEntity findByUserIdAndApplication(String userId, ApplicationEntity applicationEntity);
}

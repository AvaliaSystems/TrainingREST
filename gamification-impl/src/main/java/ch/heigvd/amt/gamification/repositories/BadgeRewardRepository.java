package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.BadgeRewardEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRewardRepository extends CrudRepository<BadgeRewardEntity, Long> {
    List<BadgeRewardEntity> findAllByUserEntity(UserEntity userEntity);
}
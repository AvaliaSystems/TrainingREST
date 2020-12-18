package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.PointRewardEntity;
import ch.heigvd.amt.gamification.entities.PointscaleEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRewardRepository extends CrudRepository<PointRewardEntity, Long> {
    PointRewardEntity findByUserEntityAndPointscaleEntity(UserEntity userEntity, PointscaleEntity pointscaleEntity);
}
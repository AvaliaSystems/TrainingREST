package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.PointRewardEntity;
import ch.heigvd.amt.gamification.repositories.projections.LeaderboardProjection;
import ch.heigvd.amt.gamification.repositories.projections.PointScaleScoreProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PointRewardRepository extends CrudRepository<PointRewardEntity, Long> {
    //List<PointRewardEntity> findAllByUserEntityAndPointScaleEntity(UserEntity userEntity, PointScaleEntity pointScaleEntity);

    @Query(value = "SELECT Users.app_user_id AS `appUserId`, SUM(PointsReward.points) AS `pointsSum` " +
        "FROM point_reward_entity AS PointsReward " +
        "INNER JOIN user_entity AS Users ON PointsReward.user_entity_id = Users.id " +
        "INNER JOIN point_scale_entity AS PointScales ON PointsReward.point_scale_entity_id = PointScales.id " +
        "WHERE PointScales.name = :pointScale AND PointScales.application_entity_id = :appId " +
        "GROUP BY Users.app_user_id " +
        "ORDER BY `pointsSum` DESC " +
        "LIMIT :limit", nativeQuery = true)
    List<LeaderboardProjection<String, BigDecimal>> findLeaders(@Param("appId") long appId,
                                                                @Param("pointScale") String pointScale,
                                                                @Param("limit") int limit);

    @Query (value ="SELECT PointScale.name AS `pointScale`, SUM(PointsReward.points) AS `score` " +
        "FROM point_reward_entity AS PointsReward " +
        "INNER JOIN point_scale_entity AS PointScale ON PointsReward.point_scale_entity_id = PointScale.id " +
        "WHERE PointsReward.user_entity_id=:userId " +
        "GROUP BY PointScale.name", nativeQuery = true)
    List<PointScaleScoreProjection<String, BigDecimal>> findScores(@Param("userId") long userId);
}
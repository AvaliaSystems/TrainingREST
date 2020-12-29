package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRepository extends CrudRepository<PointScaleEntity, Long> {
    List<PointScaleEntity> findAllByApplicationEntity(ApplicationEntity applicationEntity);

    PointScaleEntity findByApplicationEntityAndName(ApplicationEntity applicationEntity, String name);
}

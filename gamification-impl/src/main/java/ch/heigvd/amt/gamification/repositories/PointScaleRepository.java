package ch.heigvd.amt.gamification.repositories;

import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.BadgeEntity;
import ch.heigvd.amt.gamification.entities.PointscaleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRepository extends CrudRepository<PointscaleEntity, Long> {

    PointscaleEntity findAllByApplicationEntityAndName(ApplicationEntity applicationEntity, String name);
}

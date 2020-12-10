package ch.heigvd.gamification.repositories;

import ch.heigvd.gamification.entities.PointscaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PointscaleRepository extends CrudRepository<PointscaleEntity, Long> {
    Iterable<PointscaleEntity> findByApplicationEntity_ApiKey(String applicationEntity_apiKey);

}

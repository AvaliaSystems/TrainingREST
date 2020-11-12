package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.BadgeEntity;
import io.avalia.fruits.entities.FruitEntity;
import org.springframework.data.repository.CrudRepository;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
}

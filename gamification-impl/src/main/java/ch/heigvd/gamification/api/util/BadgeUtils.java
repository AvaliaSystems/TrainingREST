package ch.heigvd.gamification.api.util;

import ch.heigvd.gamification.api.model.Badge;
import ch.heigvd.gamification.entities.BadgeEntity;

public abstract class BadgeUtils {

    public static BadgeEntity toBadgeEntity(Badge badge){
        BadgeEntity badgeEntity = new BadgeEntity();

        badgeEntity.setName(badge.getName());
        badgeEntity.setColor(badge.getColor());
        badgeEntity.setDescription(badge.getDescription());
        return badgeEntity;
    }

    public static Badge toBadge(BadgeEntity badgeEntity){
        Badge badge = new Badge();
        badge.setId(badgeEntity.getId().intValue());
        badge.setColor(badgeEntity.getColor());
        badge.setName(badgeEntity.getName());
        badge.setDescription(badgeEntity.getDescription());
        return badge;
    }
}

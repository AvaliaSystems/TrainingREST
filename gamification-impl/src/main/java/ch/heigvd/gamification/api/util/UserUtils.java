package ch.heigvd.gamification.api.util;

import ch.heigvd.gamification.api.model.User;
import ch.heigvd.gamification.entities.UserEntity;

public abstract class UserUtils {

    public static UserEntity toUserEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        return entity;
    }

    public static User toUser(UserEntity entity) {
        User user = new User();
        user.setUsername(entity.getUsername());
        user.setId(entity.getId().intValue());
        return user;
    }
}

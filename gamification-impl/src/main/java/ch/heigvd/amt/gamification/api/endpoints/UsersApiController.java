package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.UsersApi;
import ch.heigvd.amt.gamification.api.model.Badge;
import ch.heigvd.amt.gamification.api.model.User;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.BadgeRewardEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
import ch.heigvd.amt.gamification.repositories.BadgeRewardRepository;
import ch.heigvd.amt.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRewardRepository badgeRewardRepository;

    @Autowired
    ServletRequest request;

    @Override
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        for(UserEntity userEntity : userRepository.findAllByApplicationEntity((ApplicationEntity) request.getAttribute("applicationEntity"))) {
            users.add(toUser(userEntity));
        }
        return ResponseEntity.ok(users);
    }

    // TODO : GET par appUserId plutôt que par gamification engine ID ?
    @Override
    public ResponseEntity<User> getUser(@ApiParam(value = "", required = true) @PathVariable("id") Integer id) {
        UserEntity existingUserEntity = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(existingUserEntity));
    }

    @Override
    public ResponseEntity<List<Badge>> getUserBadges(@ApiParam(value = "", required = true) @PathVariable("appUserId") String appUserId) {
        // Récupère l'application associée à partir de l'API Key
        ApplicationEntity applicationEntity = (ApplicationEntity) request.getAttribute("applicationEntity");

        // Vérifie qu'un utilisateur existant soit spécifié dans l'url
        UserEntity userEntity = userRepository.findByAppUserIdAndApplicationEntity(appUserId, applicationEntity);
        if(userEntity == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Parcourt tous les badges attribués et les stocke dans une liste qui sera retournée
        List<Badge> badges = new ArrayList<>();
        for(BadgeRewardEntity badgeRewardEntity : badgeRewardRepository.findAllByUserEntity(userEntity)) {
            badges.add(new Badge()
                .name(badgeRewardEntity.getBadgeEntity().getName())
                .description(badgeRewardEntity.getBadgeEntity().getDescription())
            );
        }

        return ResponseEntity.ok(badges);
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setAppUserId(entity.getAppUserId());
        user.setNbBadges(entity.getNbBadges());
        return user;
    }

}

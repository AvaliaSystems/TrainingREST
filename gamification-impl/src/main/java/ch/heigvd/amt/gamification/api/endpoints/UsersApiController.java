package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.UsersApi;
import ch.heigvd.amt.gamification.api.model.User;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
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
    ServletRequest request;

    @Override
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        for(UserEntity userEntity : userRepository.findAllByApplicationEntity((ApplicationEntity) request.getAttribute("applicationEntity"))) {
            users.add(toUser(userEntity));
        }
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUser(@ApiParam(value = "", required = true) @PathVariable("id") Integer id) {
        UserEntity existingUserEntity = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(existingUserEntity));
    }

/*
    private BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setDescription(badge.getDescription());
        return entity;
    }*/

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setNbBadges(entity.getNbBadges());
        return user;
    }

}

package ch.heigvd.gamification.api.endpoints;


import ch.heigvd.gamification.api.UsersApi;
import ch.heigvd.gamification.api.model.Application;
import ch.heigvd.gamification.api.model.Badge;
import ch.heigvd.gamification.api.model.User;
import ch.heigvd.gamification.entities.ApplicationEntity;
import ch.heigvd.gamification.entities.UserEntity;

import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.heigvd.gamification.api.util.UserUtils.*;
import static ch.heigvd.gamification.api.util.BadgeUtils.*;


@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(UUID X_API_KEY, @Valid User user) {
        UserEntity newUserEntity = toUserEntity(user);
        ApplicationEntity applicationEntity = applicationRepository.findByApiKey(X_API_KEY.toString());
        newUserEntity.setApplicationEntity(applicationEntity);

        userRepository.save(newUserEntity);

        Long id = newUserEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUserEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<User>> getUsers(UUID X_API_KEY) {
        List<User> users = new ArrayList<>();

        userRepository.findByApplicationEntity_ApiKey(X_API_KEY.toString())
                .forEach(userEntity -> users.add(toUser(userEntity)));

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userRepository.findAll()) {
            users.add(toUser(userEntity));
        }
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUser(Integer id, UUID X_API_KEY) {
        UserEntity existingUserEntity = userRepository
                .findByApplicationEntity_ApiKeyAndId(X_API_KEY.toString(),Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(toUser(existingUserEntity));
    }

    @Override
    public ResponseEntity<List<Badge>> getUsersBadges(Integer id, UUID X_API_KEY) {
        List<Badge> badges = new ArrayList<>();
        UserEntity existingUserEntity = userRepository
                .findByApplicationEntity_ApiKeyAndId(X_API_KEY.toString(),Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existingUserEntity.getBadgeEntitys().forEach(badgeEntity -> badges.add(toBadge(badgeEntity)));
        return ResponseEntity.ok(badges);
    }

}

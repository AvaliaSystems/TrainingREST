package ch.heigvd.gamification.api.endpoints;


import ch.heigvd.gamification.api.UsersApi;
import ch.heigvd.gamification.api.model.Badge;
import ch.heigvd.gamification.api.model.Pointscale;
import ch.heigvd.gamification.api.model.Reputation;
import ch.heigvd.gamification.api.model.User;
import ch.heigvd.gamification.entities.ApplicationEntity;
import ch.heigvd.gamification.entities.PointscaleEntity;
import ch.heigvd.gamification.entities.UserEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.PointscaleRepository;
import ch.heigvd.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.heigvd.gamification.api.util.BadgeUtils.toBadge;
import static ch.heigvd.gamification.api.util.UserUtils.toUser;
import static ch.heigvd.gamification.api.util.UserUtils.toUserEntity;


@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    PointscaleRepository pointscaleRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(UUID X_API_KEY, @Valid User user) {
        UserEntity newUserEntity = toUserEntity(user);
        ApplicationEntity applicationEntity = applicationRepository.findByApiKey(X_API_KEY.toString());
        newUserEntity.setApplicationEntity(applicationEntity);

        List<PointscaleEntity> pointscaleEntities = new ArrayList<>();
        pointscaleRepository.findByApplicationEntity_ApiKey(X_API_KEY.toString()).forEach(pointscaleEntities::add);
        newUserEntity.setPointscaleEntitys(pointscaleEntities);

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
    public ResponseEntity<Reputation> getUserReputation(String username, UUID X_API_KEY) {

        UserEntity ue = userRepository
                .findByApplicationEntity_ApiKeyAndUsername(X_API_KEY.toString(),username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        Reputation rep = toReputation(ue);

        return ResponseEntity.ok(rep);
    }

    private Pointscale toPointscale(PointscaleEntity pointscaleEntity) {
        Pointscale ps = new Pointscale();

        ps.setLabel(pointscaleEntity.getLabel());
        ps.setPointCounter(pointscaleEntity.getCounter());

        return ps;
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

    @Override
    public ResponseEntity<List<Pointscale>> getUsersPointScales(Integer id, UUID X_API_KEY) {
        UserEntity existingUserEntity = userRepository
                .findByApplicationEntity_ApiKeyAndId(X_API_KEY.toString(),Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Pointscale> pointscales =  new ArrayList<>();

        existingUserEntity.getPointscaleEntitys().forEach(pointscaleEntity -> {
            Pointscale pointscale = new Pointscale();
            pointscale.setPointCounter(pointscaleEntity.getCounter());
            pointscale.setLabel(pointscaleEntity.getLabel());
            pointscales.add(pointscale);
        });

        return ResponseEntity.ok(pointscales);
    }

    @Override
    public ResponseEntity<List<Reputation>> getReputations(UUID X_API_KEY) {
        List<Reputation> reputations = new ArrayList<>();
        for (UserEntity ue : userRepository.findByApplicationEntity_ApiKey(X_API_KEY.toString())){
            Reputation rep = toReputation(ue);
            reputations.add(rep);
        }

        return ResponseEntity.ok(reputations);
    }

    private Reputation toReputation(UserEntity ue) {
        Reputation rep = new Reputation();
        List<Badge> badges = new ArrayList<>();
        ue.getBadgeEntitys().forEach(badgeEntity -> badges.add(toBadge(badgeEntity)));

        List<Pointscale> pointscales = new ArrayList<>();
        ue.getPointscaleEntitys().forEach(pointscaleEntity -> pointscales.add(toPointscale(pointscaleEntity)));

        rep.setId(ue.getId().intValue());
        rep.setUsername(ue.getUsername());
        rep.setBadges(badges);
        rep.setPointscales(pointscales);

        return rep;
    }
}

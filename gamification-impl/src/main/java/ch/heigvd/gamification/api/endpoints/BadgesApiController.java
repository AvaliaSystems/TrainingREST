package ch.heigvd.gamification.api.endpoints;

import ch.heigvd.gamification.api.BadgesApi;
import ch.heigvd.gamification.entities.ApplicationEntity;
import ch.heigvd.gamification.entities.BadgeEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;
import ch.heigvd.gamification.api.model.Badge;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBadge(UUID X_API_KEY, @Valid Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);

        ApplicationEntity applicationEntity = applicationRepository.findByApiKey(X_API_KEY.toString());

        newBadgeEntity.setApplicationEntity(applicationEntity);
        badgeRepository.save(newBadgeEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBadge(Badge badge){
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        badgeRepository.save(newBadgeEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Badge>> getBadges(UUID X_API_KEY) {

        List<Badge> badges = new ArrayList<>();

        badgeRepository.findByApplicationEntity_ApiKey(X_API_KEY.toString())
                .forEach(badgeEntity -> badges.add(toBadge(badgeEntity)));

        return ResponseEntity.ok(badges);
    }


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

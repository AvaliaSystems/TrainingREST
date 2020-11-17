package ch.heigvd.gamification.api.endpoints;

import ch.heigvd.gamification.api.BadgesApi;
import ch.heigvd.gamification.entities.BadgeEntity;
import ch.heigvd.gamification.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;
import ch.heigvd.gamification.api.model.Badge;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBadge(@ApiParam(value = "", required = true) Badge badge){
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        badgeRepository.save(newBadgeEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Badge>> getBadges() {

        List<Badge> badges = new ArrayList<Badge>();

        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            badges.add(toBadge(badgeEntity));
        }

        return ResponseEntity.ok(badges);
    }

    private BadgeEntity toBadgeEntity(Badge badge){
            BadgeEntity badgeEntity = new BadgeEntity();

            badgeEntity.setName(badge.getName());
            badgeEntity.setColor(badge.getColor());
            badgeEntity.setDescription(badge.getDescription());
            return badgeEntity;
    }

    private Badge toBadge(BadgeEntity badgeEntity){
        Badge badge = new Badge();
        badge.setId(badgeEntity.getId().intValue());
        badge.setColor(badgeEntity.getColor());
        badge.setDescription(badgeEntity.getDescription());
        return badge;
    }

}

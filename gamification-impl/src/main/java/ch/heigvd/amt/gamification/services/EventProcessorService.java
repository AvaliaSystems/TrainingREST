package ch.heigvd.amt.gamification.services;

import ch.heigvd.amt.gamification.api.model.Badge;
import ch.heigvd.amt.gamification.entities.*;
import ch.heigvd.amt.gamification.repositories.BadgeRepository;
import ch.heigvd.amt.gamification.repositories.EventRepository;
import ch.heigvd.amt.gamification.repositories.RuleRepository;
import ch.heigvd.amt.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventProcessorService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RuleRepository ruleRepository;

    public void processEvent(EventEntity eventEntity) {
        String eventUserId = eventEntity.getUserId();
        ApplicationEntity applicationEntity = eventEntity.getApplicationEntity();

        UserEntity user = userRepository.findByUserIdAndApplicationEntity(eventUserId, applicationEntity);
        if(user == null) {
            user = new UserEntity();
            user.setUserId(eventUserId);
            user.setApplicationEntity(eventEntity.getApplicationEntity());
            user.setNbBadges(0);
        }

        // TODO : Récupérer et parcourir la liste des Rules du type de l'Event
        List<RuleEntity> eventRulesOfType = ruleRepository.findAllByApplicationEntityAndType(applicationEntity, eventEntity.getEventType());

        for(RuleEntity ruleOfType : eventRulesOfType) {
            // TODO : Attribuer un badge si la Rule l'indique
            if(ruleOfType.getAwardBadge() != null) {
                BadgeEntity badgeEntityOfApp = badgeRepository.findByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardBadge());

                List<BadgeEntity> currentBadges = user.getBadges();
                currentBadges.add(badgeEntityOfApp);
                System.out.println(currentBadges);

                user.setBadges(currentBadges);
            }

            // TODO : Attribuer des points si la Rule l'indique
            if(ruleOfType.getAwardPoints() != null) {
                // TODO
            }
        }

        //List<BadgeEntity> badges = new ArrayList<>();
        // Attribue seulement le premier badge (temporaire FIXME)
        //badges.add(badgeRepository.findAllByApplicationEntity(eventEntity.getApplicationEntity()).get(0));
        //user.setBadges(badges);

        int nbBadges = user.getNbBadges();
        user.setNbBadges(++nbBadges);

        userRepository.save(user);

        // TODO : Issue #37 - Vérifier le nombre de points de l'user et lui attribuer un badge s'il a atteint un palier de points

        // TODO : Besoin de sauvegarder tous les events ou juste les traiter? (la plupart seront "vides")
        //eventRepository.save(eventEntity);
    }

}

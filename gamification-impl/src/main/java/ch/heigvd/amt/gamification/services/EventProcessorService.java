package ch.heigvd.amt.gamification.services;

import ch.heigvd.amt.gamification.entities.*;
import ch.heigvd.amt.gamification.repositories.BadgeRepository;
import ch.heigvd.amt.gamification.repositories.EventRepository;
import ch.heigvd.amt.gamification.repositories.RuleRepository;
import ch.heigvd.amt.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public long processEvent(EventEntity eventEntity) {
        String eventUserId = eventEntity.getUserId();
        ApplicationEntity applicationEntity = eventEntity.getApplicationEntity();

        // Récupère l'utilisateur à partir de l'Event
        UserEntity user = userRepository.findByUserIdAndApplicationEntity(eventUserId, applicationEntity);
        // S'il n'existe pas encore on le créé
        if(user == null) {
            user = new UserEntity();
            user.setUserId(eventUserId);
            user.setApplicationEntity(eventEntity.getApplicationEntity());
            user.setNbBadges(0);
        }

        // Récupère et parcourt la liste des Rules du type de l'Event
        List<RuleEntity> eventRulesOfType = ruleRepository.findAllByApplicationEntityAndType(applicationEntity, eventEntity.getEventType());

        for(RuleEntity ruleOfType : eventRulesOfType) {
            // Attribue un badge si la Rule l'indique
            if(ruleOfType.getAwardBadge() != null) {
                BadgeEntity badgeEntityOfApp = badgeRepository.findByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardBadge());

                // Récupère les badges existants et ajoute le nouveau si nécessaire
                List<BadgeEntity> currentBadges = user.getBadges();
                if(currentBadges == null) {
                    currentBadges = new ArrayList<>();
                }
                currentBadges.add(badgeEntityOfApp);

                user.setBadges(currentBadges);

                // Incrémente le compteur de badges
                int nbBadges = user.getNbBadges();
                user.setNbBadges(++nbBadges);
            }

            // TODO : Attribuer des points si la Rule l'indique
            if(ruleOfType.getAwardPoints() != null) {
                // TODO
            }
        }

        userRepository.save(user);

        // TODO : Issue #37 - Vérifier le nombre de points de l'user et lui attribuer un badge s'il a atteint un palier de points

        // TODO : Besoin de sauvegarder tous les events ou juste les traiter? (la plupart seront "vides")
        //eventRepository.save(eventEntity);

        return user.getId();
    }

}

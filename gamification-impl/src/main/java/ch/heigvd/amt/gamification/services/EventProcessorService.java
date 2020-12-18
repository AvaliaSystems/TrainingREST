package ch.heigvd.amt.gamification.services;

import ch.heigvd.amt.gamification.entities.*;
import ch.heigvd.amt.gamification.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventProcessorService {
    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private PointScaleRepository pointscaleRepository;

    @Autowired
    private BadgeRewardRepository badgeRewardRepository;
    @Autowired
    private PointRewardRepository pointRewardRepository;

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
            userRepository.save(user);
        }

        // Récupère et parcourt la liste des Rules du type de l'Event
        List<RuleEntity> eventRulesOfType = ruleRepository.findAllByApplicationEntityAndEventType(applicationEntity, eventEntity.getEventType());

        for(RuleEntity ruleOfType : eventRulesOfType) {
            // Attribue un badge si la Rule l'indique
            if(ruleOfType.getAwardBadge() != null) {
                BadgeEntity badgeEntityOfApp = badgeRepository.findByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardBadge());

                // La règle attribue un badge à l'utilisateur
                BadgeRewardEntity badgeRewardEntity = new BadgeRewardEntity();
                badgeRewardEntity.setBadgeEntity(badgeEntityOfApp);
                badgeRewardEntity.setUserEntity(user);
                badgeRewardEntity.setTimestamp(LocalDateTime.now());
                badgeRewardRepository.save(badgeRewardEntity);

                /*
                // FIXME: Incrémente le compteur de badges
                int nbBadges = user.getNbBadges();
                user.setNbBadges(++nbBadges);
                 */
            }

            PointscaleEntity pointscaleEntityOfApp;
            // TODO : Attribuer des points si la Rule l'indique
            if(ruleOfType.getAwardPoints() != null) {
                pointscaleEntityOfApp = pointscaleRepository.findAllByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardPoints());

                PointRewardEntity pointRewardEntity = new PointRewardEntity();
                pointRewardEntity.setPointscaleEntity(pointscaleEntityOfApp);
                pointRewardEntity.setUserEntity(user);
                pointRewardEntity.setTimestamp(LocalDateTime.now());
                pointRewardEntity.setPoints(ruleOfType.getAmount());
                pointRewardRepository.save(pointRewardEntity);

                // TODO : Issue #37 - Vérifier le nombre de points de l'user et lui attribuer un badge s'il a atteint un palier de points
                PointRewardEntity userPointRewardEntity = pointRewardRepository.findByUserEntityAndPointscaleEntity(user, pointscaleEntityOfApp);
                int userPoints = userPointRewardEntity.getPoints();

                // TODO : Besoin de sauvegarder tous les events ou juste les traiter? (la plupart seront "vides")
                //eventRepository.save(eventEntity);

                //if(userPoints == ruleOfType.get)
            }
        }
        return user.getId();

        //userRepository.save(user);


    }
}

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
    private PointScaleRepository pointScaleRepository;

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
        UserEntity user = userRepository.findByAppUserIdAndApplicationEntity(eventUserId, applicationEntity);
        // S'il n'existe pas encore on le créé
        if(user == null) {
            user = new UserEntity();
            user.setAppUserId(eventUserId);
            user.setApplicationEntity(eventEntity.getApplicationEntity());
            user.setNbBadges(0);
            userRepository.save(user);
        }

        // Récupère et parcourt la liste des Rules du type de l'Event
        List<RuleEntity> eventRulesOfType = ruleRepository.findAllByApplicationEntityAndEventType(applicationEntity, eventEntity.getEventType());
        for(RuleEntity ruleOfType : eventRulesOfType) {

            BadgeEntity badgeEntityOfApp = badgeRepository.findByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardBadge());

            // Attribue un badge si la Rule l'indique
            if(badgeEntityOfApp != null) {
                // La règle attribue un badge à l'utilisateur
                BadgeRewardEntity badgeRewardEntity = new BadgeRewardEntity();
                badgeRewardEntity.setBadgeEntity(badgeEntityOfApp);
                badgeRewardEntity.setUserEntity(user);
                badgeRewardEntity.setTimestamp(LocalDateTime.now());
                badgeRewardRepository.save(badgeRewardEntity);

                // Incrémente le compteur de badges de l'utilisateur
                int nbBadges = user.getNbBadges();
                user.setNbBadges(++nbBadges);
                userRepository.save(user);
            }

            PointScaleEntity pointScaleEntityOfApp = pointScaleRepository.findByApplicationEntityAndName(applicationEntity, ruleOfType.getAwardPoints());

            // Attribuer des points si la Rule l'indique
            if(pointScaleEntityOfApp != null) {
                // La règle attribue des points à l'utilisateur sur la pointScale définie
                PointRewardEntity pointRewardEntity = new PointRewardEntity();
                pointRewardEntity.setPointScaleEntity(pointScaleEntityOfApp);
                pointRewardEntity.setUserEntity(user);
                pointRewardEntity.setTimestamp(LocalDateTime.now());
                pointRewardEntity.setPoints(ruleOfType.getAmount());
                pointRewardRepository.save(pointRewardEntity);

                /*
                // TODO : Issue #37 - Vérifier le nombre de points de l'user et lui attribuer un badge s'il a atteint un palier de points
                List<PointRewardEntity> userPointRewardEntityList = pointRewardRepository.findAllByUserEntityAndPointScaleEntity(user, pointScaleEntityOfApp);
                int userPoints = 0;
                for(PointRewardEntity userPointRewardEntity : userPointRewardEntityList) {
                    userPoints += userPointRewardEntity.getPoints();
                }
                */

                //if(userPoints == ruleOfType.get)
            }
        }
        return user.getId();
    }
}

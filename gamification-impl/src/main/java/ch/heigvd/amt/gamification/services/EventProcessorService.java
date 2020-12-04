package ch.heigvd.amt.gamification.services;

import ch.heigvd.amt.gamification.entities.BadgeEntity;
import ch.heigvd.amt.gamification.entities.EventEntity;
import ch.heigvd.amt.gamification.entities.UserEntity;
import ch.heigvd.amt.gamification.repositories.BadgeRepository;
import ch.heigvd.amt.gamification.repositories.EventRepository;
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

    public void processEvent(EventEntity eventEntity) {
        String eventUserId = eventEntity.getUserId();

        UserEntity user = userRepository.findByUserIdAndApplicationEntity(eventUserId, eventEntity.getApplicationEntity());
        if(user == null) {
            user = new UserEntity();
            user.setUserId(eventUserId);
            user.setApplicationEntity(eventEntity.getApplicationEntity());
            user.setNbBadges(0);
        }

        List<BadgeEntity> badges = new ArrayList<>();
        // Attribue seulement le premier badge (temporaire FIXME)
        badges.add(badgeRepository.findAllByApplicationEntity(eventEntity.getApplicationEntity()).get(0));
        user.setBadges(badges);

        int nbBadges = user.getNbBadges();
        user.setNbBadges(++nbBadges);

        userRepository.save(user);

        // TODO : Besoin de sauvegarder tous les events ou juste les traiter? (la plupart seront "vides")
        //eventRepository.save(eventEntity);
    }

}

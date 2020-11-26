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

        // TODO : Find users of application
        UserEntity user = userRepository.findByUserId(eventUserId);
        if(user == null) {
            user = new UserEntity();
            user.setUserId(eventUserId);
            user.setApplication(eventEntity.getApplication());
        }

        List<BadgeEntity> badges = new ArrayList<>();
        // Attribue seulement le premier badge (temporaire FIXME)
        badges.add(badgeRepository.findAllByApplication(eventEntity.getApplication()).get(0));
        user.setBadges(badges);

        userRepository.save(user);

        // Besoin de sauvegarder tous les events (la plupart
        //eventRepository.save(eventEntity);
    }

}

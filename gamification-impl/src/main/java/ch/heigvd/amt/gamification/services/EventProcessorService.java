package ch.heigvd.amt.gamification.services;

import ch.heigvd.amt.gamification.entities.BadgeEntity;
import ch.heigvd.amt.gamification.entities.EventEntity;
import ch.heigvd.amt.gamification.repositories.BadgeRepository;
import ch.heigvd.amt.gamification.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProcessorService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    public void processEvent(EventEntity eventEntity){
        eventEntity.setBadge(badgeRepository.findAllByApplication(eventEntity.getApplication()).get(0));
        //eventRepository.save(eventEntity);
    }

}

package ch.heigvd.gamification.api.services;

import ch.heigvd.gamification.entities.BadgeEntity;
import ch.heigvd.gamification.entities.UserEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.BadgeRepository;
import ch.heigvd.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventProcessorServiceImpl implements EventProcessorService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public void addBadgetoUser(String apikey, String username) {
        List<BadgeEntity> badges = new ArrayList<>();
        List<BadgeEntity> userBadges = null;

        badgeRepository.findByApplicationEntity_ApiKey(apikey)
                .forEach(badge -> badges.add(badge));

        UserEntity currentUser = userRepository
                .findByApplicationEntity_ApiKeyAndUsername(apikey,username)
                .orElse(null);


        if(currentUser == null){
            currentUser = new UserEntity();
            currentUser.setBadgeEntitys(new ArrayList<>());
            currentUser.setUsername(username);
            currentUser.setApplicationEntity(applicationRepository.findByApiKey(apikey));
        }

        userBadges = currentUser.getBadgeEntitys();

        userBadges.add(badges.get(0)); // get the first element

        currentUser.setBadgeEntitys(userBadges);

        userRepository.save(currentUser);
    }
}

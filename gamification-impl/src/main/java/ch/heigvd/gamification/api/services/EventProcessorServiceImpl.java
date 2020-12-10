package ch.heigvd.gamification.api.services;

import ch.heigvd.gamification.api.model.Event;
import ch.heigvd.gamification.api.model.Rule;
import ch.heigvd.gamification.api.model.RuleThenAction;
import ch.heigvd.gamification.entities.*;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.BadgeRepository;
import ch.heigvd.gamification.repositories.RuleRepository;
import ch.heigvd.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventProcessorServiceImpl implements EventProcessorService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public void applyRules(String apikey,Event event) {

        UserEntity concernedUser = userRepository.findByApplicationEntity_ApiKeyAndUsername(apikey,event.getEventparams().getUsername()).orElse(null);;

        if(concernedUser == null){
            concernedUser = this.createUser(event.getEventparams().getUsername(),applicationRepository.findByApiKey(apikey));
        }

        for (RuleEntity ruleEntity : ruleRepository.findByEventType(event.getEventType())){
            doAction(ruleEntity,concernedUser,apikey);
        }
    }

    private void doAction(RuleEntity ruleEntity, UserEntity userEntity,String apikey){
        switch (ruleEntity.getActionName()){
            case "addBadge":
                BadgeEntity badgeEntity = badgeRepository.findByApplicationEntity_ApiKeyAndName(apikey,ruleEntity.getActionTarget());
                userEntity.getBadgeEntitys().add(badgeEntity);
                userRepository.save(userEntity);
                break;
            case "addPoint":
                userEntity.getPointscaleEntitys().forEach(pointscaleEntity -> {
                    if (pointscaleEntity.getLabel().equals(ruleEntity.getActionTarget())){
                        pointscaleEntity.setCounter(pointscaleEntity.getCounter() + 1);
                    }
                    userRepository.save(userEntity);
                });
                break;
            default:
                break;

        }
    }

    private UserEntity createUser(String username, ApplicationEntity applicationEntity){

        UserEntity userEntity = new UserEntity();
        userEntity.setBadgeEntitys(new ArrayList<>());
        userEntity.setUsername(username);
        userEntity.setApplicationEntity(applicationEntity);

        return userEntity;
    }

}

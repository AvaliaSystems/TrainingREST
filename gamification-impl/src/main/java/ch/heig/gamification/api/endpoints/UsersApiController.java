package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.api.model.User;
import ch.heig.gamification.api.model.Badge;
import ch.heig.gamification.api.UsersApi;
import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.entities.UserEntity;
import ch.heig.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    ServletRequest req;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<List<User>> getUsers() {

        ApplicationEntity applicationEntity = (ApplicationEntity) req.getAttribute("appEntity");

        List<User> users = new ArrayList<>();
        for(UserEntity userEntity : userRepository.findAllByApiKey(applicationEntity.getApiKey())){
            users.add(toUser(userEntity)); // transforme userEntity -> User
        }
        return ResponseEntity.ok(users);
    }


    public ResponseEntity<User> getUser(@ApiParam(value = "", required = true) @PathVariable("inAppId") String inAppId){
        ApplicationEntity applicationEntity = (ApplicationEntity) req.getAttribute("appEntity");
        UserEntity userEntity = userRepository.findByInAppIdAndApiKey(inAppId, applicationEntity.getApiKey());
        if(userEntity != null){
            return ResponseEntity.ok(toUser(userEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

package ch.heigvd.gamification.api.endpoints;


import ch.heigvd.gamification.RFC3339DateFormat;
import ch.heigvd.gamification.api.EventsApi;
import ch.heigvd.gamification.api.model.User;
import ch.heigvd.gamification.api.model.Event;
import ch.heigvd.gamification.api.services.EventProcessorService;
import ch.heigvd.gamification.api.services.EventProcessorServiceImpl;
import ch.heigvd.gamification.entities.ApplicationEntity;
import ch.heigvd.gamification.entities.EventEntity;
import ch.heigvd.gamification.entities.UserEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EventProcessorService eventProcessorService;


    @Override
    public ResponseEntity<Void> processEvent(UUID X_API_KEY, @Valid Event event) {

        // check for event type
        String username = event.getEventparams().getUsername();
        Date date = new Date();
        String dateFormated = RFC3339DateFormat.getDateTimeInstance().format(date);
        event.setTimestamp(dateFormated);

        System.out.println("receive event at " + event.getTimestamp());

        eventProcessorService.addBadgetoUser(X_API_KEY.toString(),username);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

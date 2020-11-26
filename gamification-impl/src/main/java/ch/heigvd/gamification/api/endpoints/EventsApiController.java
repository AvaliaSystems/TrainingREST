package ch.heigvd.gamification.api.endpoints;


import ch.heigvd.gamification.api.EventsApi;
import ch.heigvd.gamification.api.model.User;
import ch.heigvd.gamification.api.model.Event;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    private ApplicationRepository applicationRepository;

    public ResponseEntity<Void> createEvent(@ApiParam(value = "", required = true) @Valid @RequestBody Event event) {
        EventEntity newEventEntity = toEventEntity(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEventEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEvent(UUID X_API_KEY, @Valid Event event) {
        EventEntity newEventEntity = toEventEntity(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEventEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private EventEntity toEventEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setId(event.getId());
        entity.setName(event.getName());

        return entity;
    }

    private Event toEvent(EventEntity entity) {
        Event event = new Event();
        //need to controle type into spec yaml
        //user.setId(entity.getId());
        event.setName(entity.getName());

        return event;
    }

}

package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.EventsApi;
import io.avalia.fruits.api.model.Event;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EventApiController implements EventsApi {
    // TODO : Créer et ajouter EventRepository et EventEntity?

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEvent(@ApiParam(value = "", required = true) @Valid @RequestBody Event event) {
        return null;
    }
}

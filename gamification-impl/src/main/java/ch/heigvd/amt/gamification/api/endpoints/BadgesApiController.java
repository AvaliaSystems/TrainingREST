package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.BadgesApi;
import ch.heigvd.amt.gamification.api.model.Badge;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Controller
public class BadgesApiController implements BadgesApi {
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        return null;
    }
}

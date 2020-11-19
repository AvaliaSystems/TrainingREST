package ch.heigvd.gamification.api.endpoints;

import ch.heigvd.gamification.api.ApplicationsApi;
import ch.heigvd.gamification.api.model.Application;
import ch.heigvd.gamification.entities.ApplicationEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Controller
public class ApplicationApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<Void> createApplication(@ApiParam(value = "",required=true) @Valid Application application) {
        ApplicationEntity newApplicationEntity = new ApplicationEntity();

        newApplicationEntity.setApiKey(UUID.randomUUID().toString());
        newApplicationEntity.setName(application.getName());

        applicationRepository.save(newApplicationEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newApplicationEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Application> getApplication(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        ApplicationEntity applicationEntity = applicationRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Application app = new Application();

        app.setId(applicationEntity.getId().intValue());
        app.setName(applicationEntity.getName());

        return ResponseEntity.ok(app);
    }
}

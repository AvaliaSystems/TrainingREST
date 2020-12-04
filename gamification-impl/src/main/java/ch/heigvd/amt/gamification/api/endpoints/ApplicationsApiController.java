package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.ApplicationsApi;
import ch.heigvd.amt.gamification.api.model.Application;
import ch.heigvd.amt.gamification.api.model.ApplicationRegistration;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ApplicationsApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> registerApplication(@ApiParam(value = "", required = true) @Valid @RequestBody ApplicationRegistration applicationRegistration) {
        ApplicationEntity newApplicationEntity = toApplicationEntity(applicationRegistration);
        applicationRepository.save(newApplicationEntity);

        return ResponseEntity.ok().header("X-API-KEY", newApplicationEntity.getKey()).build();
    }

    // FIXME : Enlever l'endpoint GET sur /applications (on le laisse pour le debug)
    /*public ResponseEntity<List<Application>> getApplications() {
        List<Application> applications = new ArrayList<>();
        for(ApplicationEntity applicationEntity : applicationRepository.findAll()) {
            applications.add(toApplication(applicationEntity));
        }
        return ResponseEntity.ok(applications);
    }

    @Override
    public ResponseEntity<Application> getApplication(@ApiParam(value = "", required = true) @PathVariable("id") Integer id) {
        ApplicationEntity existingApplicationEntity = applicationRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toApplication(existingApplicationEntity));
    }*/

    private ApplicationEntity toApplicationEntity(ApplicationRegistration applicationRegistration) {
        ApplicationEntity entity = new ApplicationEntity();
        String key = UUID.randomUUID().toString();
        entity.setKey(key);
        entity.setName(applicationRegistration.getName());
        return entity;
    }

    private Application toApplication(ApplicationEntity entity) {
        Application application = new Application();
        application.setName(entity.getName());
        application.setKey(entity.getKey());
        return application;
    }

}

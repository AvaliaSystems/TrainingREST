package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.api.model.Application;
import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.api.ApplicationsApi;
import ch.heig.gamification.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class ApplicationsApiController implements ApplicationsApi {

    @Autowired
    ApplicationsRepository applicationsRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> registerApplication(@ApiParam(value = "", required = true) @Valid @RequestBody Application application) {
        ApplicationEntity newApplicationEntity = toApplicationEntity(application);
        applicationsRepository.save(newApplicationEntity);
        Long id = newApplicationEntity.getId();

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newApplicationEntity.getId()).toUri();

        return ResponseEntity.ok(id);
    }
    private ApplicationEntity toApplicationEntity(Application application){
        ApplicationEntity entity = new ApplicationEntity();
        entity.setName(application.getName());
        return entity;
    }

    private Application toApplication(ApplicationEntity applicationEntity){
        Application application = new Application();
        application.setName(applicationEntity.getName());
        return application;
    }

}

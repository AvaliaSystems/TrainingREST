package ch.heigvd.amt.gamification.api.endpoints;

//import ch.heigvd.amt.gamification.api.PointScaleApi;
import ch.heigvd.amt.gamification.api.model.PointScale;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.PointScaleEntity;
import ch.heigvd.amt.gamification.repositories.PointScaleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Controller
public class PointScaleApiController implements PointScaleApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    ServletRequest request;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPointScale(@ApiParam(value = "", required = true) @Valid @RequestBody PointScale pointScale) {
        PointScaleEntity newPointScaleEntity = toPointScaleEntity(pointScale);
        newPointScaleEntity.setApplicationEntity((ApplicationEntity) request.getAttribute("applicationEntity"));
        pointScaleRepository.save(newPointScaleEntity);
        Long id = newPointScaleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private PointScaleEntity toPointScaleEntity(PointScale pointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setName(pointScale.getName());
        entity.setDescription(pointScale.getDescription());
        return entity;
    }
}

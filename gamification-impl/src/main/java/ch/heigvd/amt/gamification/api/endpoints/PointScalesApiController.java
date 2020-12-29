package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.PointScalesApi;
import ch.heigvd.amt.gamification.api.model.PointScale;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.PointScaleEntity;
import ch.heigvd.amt.gamification.repositories.PointScaleRepository;
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

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    ServletRequest request;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPointscale(@ApiParam(value = "", required = true) @Valid @RequestBody PointScale pointScale) {
        // Récupère l'application associée à partir de l'API Key
        ApplicationEntity applicationEntity = (ApplicationEntity) request.getAttribute("applicationEntity");

        // Vérifie que le badge n'existe pas déjà pour l'application donnée
        // Retourne un 409 - Conflict si c'est le cas
        if(pointScaleRepository.findByApplicationEntityAndName(applicationEntity, pointScale.getName()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        PointScaleEntity newPointScaleEntity = toPointScaleEntity(pointScale);
        newPointScaleEntity.setApplicationEntity(applicationEntity);
        pointScaleRepository.save(newPointScaleEntity);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(newPointScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<PointScale>> getPointScales() {
        List<PointScale> pointScales = new ArrayList<>();
        for(PointScaleEntity newPointScaleEntity : pointScaleRepository.findAllByApplicationEntity((ApplicationEntity)
            request.getAttribute("applicationEntity"))) {
            pointScales.add(toPointscale(newPointScaleEntity));
        }
        return ResponseEntity.ok(pointScales);
    }

    public ResponseEntity<PointScale> getPointScale(@ApiParam(value = "", required = true) @PathVariable("id") Integer id) {
        PointScaleEntity existingPointscaleEntity = pointScaleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toPointscale(existingPointscaleEntity));
    }

    private PointScale toPointscale(PointScaleEntity pointScaleEntity) {
        PointScale pointScale = new PointScale();
        pointScale.setName(pointScaleEntity.getName());
        pointScale.setDescription(pointScaleEntity.getDescription());
        return pointScale;
    }

    private PointScaleEntity toPointScaleEntity(PointScale pointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setName(pointScale.getName());
        entity.setDescription(pointScale.getDescription());
        return entity;
    }
}

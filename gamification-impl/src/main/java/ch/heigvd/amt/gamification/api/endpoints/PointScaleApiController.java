package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.PointscaleApi;
import ch.heigvd.amt.gamification.api.model.PointScale;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.PointscaleEntity;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class PointScaleApiController implements PointscaleApi {

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    ServletRequest request;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPointscale(@ApiParam(value = "", required = true) @Valid @RequestBody PointScale pointscale) {
        // Récupère l'application associée à partir de l'API Key
        ApplicationEntity applicationEntity = (ApplicationEntity) request.getAttribute("applicationEntity");

        // Vérifie que le badge n'existe pas déjà pour l'application donnée
        // Retourne un 409 - Conflict si c'est le cas
        if(pointScaleRepository.findByApplicationEntityAndName(applicationEntity, pointscale.getName()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        PointscaleEntity newPointscaleEntity = toPointscaleEntity(pointscale);
        newPointscaleEntity.setApplicationEntity(applicationEntity);
        pointScaleRepository.save(newPointscaleEntity);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(newPointscaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<PointScale>> getPointScales() {
        List<PointScale> pointscales = new ArrayList<>();
        for(PointscaleEntity newPointscaleEntity : pointScaleRepository.findAllByApplicationEntity((ApplicationEntity)
            request.getAttribute("applicationEntity"))) {
            pointscales.add(toPointscale(newPointscaleEntity));
        }
        return ResponseEntity.ok(pointscales);
    }

    private PointScale toPointscale(PointscaleEntity pointscaleEntity) {
        PointScale pointScale = new PointScale();
        pointScale.setName(pointscaleEntity.getName());
        pointScale.setDescription(pointscaleEntity.getDescription());
        return pointScale;
    }

    private PointscaleEntity toPointscaleEntity(PointScale pointScale) {
        PointscaleEntity entity = new PointscaleEntity();
        entity.setName(pointScale.getName());
        entity.setDescription(pointScale.getDescription());
        return entity;
    }
}

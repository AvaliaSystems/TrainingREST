package ch.heigvd.gamification.api.endpoints;

import ch.heigvd.gamification.api.PointscaleApi;
import ch.heigvd.gamification.api.model.Application;
import ch.heigvd.gamification.api.model.Pointscale;
import ch.heigvd.gamification.entities.PointscaleEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.PointscaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class PointscaleApiController implements PointscaleApi {

    @Autowired
    PointscaleRepository pointscaleRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public ResponseEntity<Void> createPointScales(UUID X_API_KEY, @Valid Pointscale pointscale) {
        PointscaleEntity pointscaleEntity = new PointscaleEntity();
        pointscaleEntity.setLabel(pointscale.getLabel());
        pointscaleEntity.setApplicationEntity(applicationRepository.findByApiKey(X_API_KEY.toString()));
        pointscaleRepository.save(pointscaleEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

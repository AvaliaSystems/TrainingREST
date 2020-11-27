package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.api.ScoreScalesApi;
import ch.heig.gamification.api.model.ScoreScale;
import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.entities.ScoreScaleEntity;
import ch.heig.gamification.repositories.ScoreScaleRepository;
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

@Controller
public class ScoreScalesApiController implements ScoreScalesApi {

    @Autowired
    ScoreScaleRepository scoreScaleRepository;

    @Autowired
    ServletRequest servletRequest;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createScoreScale(@ApiParam(name = "", required = true) @Valid @RequestBody ScoreScale scoreScale) {
        ScoreScaleEntity newScoreScaleEntity = toScoreScaleEntity(scoreScale);
        scoreScaleRepository.save(newScoreScaleEntity);

        servletRequest.getAttribute("scoreScaleEntitiy");

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newScoreScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    /*
    public ResponseEntity<ScoreScale> getScoreScale(@ApiParam(name = "", required=true) @PathVariable("id") Integer id) {
        ScoreScaleEntity existingScoreScaleEntity = scoreScaleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toScoreScale(existingScoreScaleEntity));
    }*/

    private ScoreScaleEntity toScoreScaleEntity(ScoreScale scoreScale) {
        ScoreScaleEntity entity = new ScoreScaleEntity();
        entity.setName(scoreScale.getName());
        entity.setApplicationEntity((ApplicationEntity)servletRequest.getAttribute("appEntity"));
        return entity;
    }
    /*
    private ScoreScale toScoreScale(ScoreScaleEntity entity) {
        ScoreScale scoreScale = new ScoreScale();
        scoreScale.setName(entity.getName());
        //scoreScale.setApplicationEntity(entity.getApplicationEntity());
        return scoreScale;
    }*/
}

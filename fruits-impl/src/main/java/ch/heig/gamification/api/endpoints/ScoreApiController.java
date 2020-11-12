package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.api.ScoreApi;
import ch.heig.gamification.api.model.Score;
import ch.heig.gamification.entities.ScoreEntity;
import ch.heig.gamification.repositories.ScoreRepository;
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

import javax.validation.Valid;
import java.net.URI;

@Controller
public class ScoreApiController implements ScoreApi {

    @Autowired
    ScoreRepository scoreRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createScore(@ApiParam(value = "", required = true) @Valid @RequestBody Score score) {
        ScoreEntity newScoreEntity = toScoreEntity(score);
        scoreRepository.save(newScoreEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newScoreEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Score> getScore(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        ScoreEntity existingScoreEntity = scoreRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toScore(existingScoreEntity));
    }

    private ScoreEntity toScoreEntity(Score score) {
        ScoreEntity entity = new ScoreEntity();
        entity.setValue(score.getValue());
        return entity;
    }

    private Score toScore(ScoreEntity entity) {
        Score score = new Score();
        score.setValue(entity.getValue());
        return score;
    }
}

package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.EventsApi;
import ch.heigvd.amt.gamification.api.RulesApi;
import ch.heigvd.amt.gamification.api.model.Event;
import ch.heigvd.amt.gamification.api.model.Rule;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.entities.EventEntity;
import ch.heigvd.amt.gamification.entities.RuleEntity;
import ch.heigvd.amt.gamification.repositories.RuleRepository;
import ch.heigvd.amt.gamification.services.EventProcessorService;
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
public class RulesApiController implements RulesApi {
    @Autowired
    ServletRequest request;

    @Autowired
    RuleRepository ruleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule) {
        RuleEntity newRuleEntity = toRuleEntity(rule);
        //newRuleEntity.setApplication((ApplicationEntity) request.getAttribute("applicationEntity"));
        ruleRepository.save(newRuleEntity);

        Long id = newRuleEntity.getId();

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private RuleEntity toRuleEntity(Rule rule) {
        RuleEntity entity = new RuleEntity();
        entity.setType(rule.getType());
        entity.setQuantity(rule.getQuantity());
        entity.setAwardBadge(rule.getAwardBadge());
        entity.setAwardPoints(rule.getAwardPoints());
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule rule = new Rule();
        rule.setType(entity.getType());
        rule.setQuantity(entity.getQuantity());
        rule.setAwardBadge(entity.getAwardBadge());
        rule.setAwardPoints(entity.getAwardPoints());
        return rule;
    }
}

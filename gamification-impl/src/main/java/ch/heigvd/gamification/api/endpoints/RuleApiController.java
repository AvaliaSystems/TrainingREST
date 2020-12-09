package ch.heigvd.gamification.api.endpoints;

import ch.heigvd.gamification.api.RulesApi;
import ch.heigvd.gamification.api.model.Rule;
import ch.heigvd.gamification.api.model.RuleIf;
import ch.heigvd.gamification.api.model.RuleThen;
import ch.heigvd.gamification.api.model.RuleThenAction;
import ch.heigvd.gamification.entities.RuleEntity;
import ch.heigvd.gamification.repositories.ApplicationRepository;
import ch.heigvd.gamification.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RuleApiController implements RulesApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public ResponseEntity<Void> createRule(UUID X_API_KEY, @Valid Rule rule) {
        RuleEntity newRuleEntity = new RuleEntity();
        newRuleEntity.setApplicationEntity(applicationRepository.findByApiKey(X_API_KEY.toString()));

        newRuleEntity.setEventType(rule.getIf().getEventType());
        newRuleEntity.setActionName(rule.getThen().getAction().getName());
        newRuleEntity.setActionTarget(rule.getThen().getAction().getTarget());
        newRuleEntity.setActionAmount(rule.getThen().getAction().getAmount());

        ruleRepository.save(newRuleEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(UUID X_API_KEY) {
        List<Rule> rules = new ArrayList<>();

        for (RuleEntity ruleEntity : ruleRepository.findByApplicationEntity_ApiKey(X_API_KEY.toString())){
            Rule rule = new Rule();

            rule.setIf(new RuleIf());
            rule.setThen(new RuleThen());
            rule.getThen().setAction(new RuleThenAction());
            rule.getIf().setEventType(ruleEntity.getEventType());
            rule.getThen().getAction().setName(ruleEntity.getActionName());
            rule.getThen().getAction().setTarget(ruleEntity.getActionTarget());
            rule.getThen().getAction().setAmount(ruleEntity.getActionAmount());

            rules.add(rule);
        }

        return ResponseEntity.ok(rules);
    }
}

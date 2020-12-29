package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Event;
import ch.heigvd.gamification.api.dto.EventEventparams;
import ch.heigvd.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class EventSteps {

    private Environment environment;
    private DefaultApi api;
    private BasicSteps basicSteps;
    private ApplicationSteps applicationSteps;
    private BadgeSteps badgeSteps;
    private UserSteps userSteps;

    Event event;
    EventEventparams eventparams;

    public EventSteps(Environment environment, BasicSteps basicSteps, ApplicationSteps applicationSteps, UserSteps userSteps, BadgeSteps badgeSteps) {
        this.environment = environment;
        this.api = environment.getApi();
        this.basicSteps = basicSteps;
        this.applicationSteps = applicationSteps;
        this.userSteps = userSteps;
        this.badgeSteps = badgeSteps;
    }

    @Given("I have an event payload")
    public void i_have_an_event_payload() {
        eventparams = new ch.heigvd.gamification.api.dto.EventEventparams()
                .username("Jean");
        event = new ch.heigvd.gamification.api.dto.Event()
                .eventType("generic_descritpion")
                .eventparams(eventparams)
                .timestamp((long)0);
    }

    @When("^I POST the event payload to the /events endpoint$")
    public void i_POST_the_event_payload_to_the_events_endpoint() {
        try {
            basicSteps.processApiResponse(api.processEventWithHttpInfo(applicationSteps.getApiKey(), event));
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }
}

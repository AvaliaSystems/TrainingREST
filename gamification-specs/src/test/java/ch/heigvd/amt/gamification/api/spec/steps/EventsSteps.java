package ch.heigvd.amt.gamification.api.spec.steps;

import ch.heigvd.amt.gamification.ApiException;
import ch.heigvd.amt.gamification.ApiResponse;
import ch.heigvd.amt.gamification.api.DefaultApi;
import ch.heigvd.amt.gamification.api.dto.Event;
import ch.heigvd.amt.gamification.api.dto.EventProperties;
import ch.heigvd.amt.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class EventsSteps {
    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private Event event;

    public EventsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is an Events server")
    public void there_is_an_events_server() {
        assertNotNull(api);
    }

    @Given("I have an event payload")
    public void i_have_an_event_payload() {
        EventProperties eventProperties = new EventProperties()
                .quantity(0)
                .subType("mockPropertyType");

        event = new Event().properties(eventProperties)
                //.timestamp();
                .eventType("mockType")
                .userId("mockUserIs");
    }

    @When("I POST the event payload to the \\/events endpoint")
    public void i_post_the_event_payload_to_the_events_endpoint() {
        try {
            lastApiResponse = api.createEventWithHttpInfo(event);
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }
}

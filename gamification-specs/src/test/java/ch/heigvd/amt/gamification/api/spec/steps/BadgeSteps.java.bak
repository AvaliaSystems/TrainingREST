package ch.heigvd.amt.gamification.api.spec.steps;

import ch.heigvd.amt.gamification.api.spec.helpers.Environment;
import ch.heigvd.amt.gamification.api.DefaultApi;
import ch.heigvd.amt.gamification.ApiException;
import ch.heigvd.amt.gamification.ApiResponse;
import ch.heigvd.amt.gamification.api.dto.Badge;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BadgeSteps {
    private Environment environment;
    private DefaultApi api;

    Badge badge;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Badge   lastReceivedBadge;

    public BadgeSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Badges server")
    public void there_is_a_badges_server() {
        assertNotNull(api);
    }

    @Given("I have a badge payload")
    public void i_have_a_badge_payload() {
        badge = new ch.heigvd.amt.gamification.api.dto.Badge()
                .badgeId("mockId")
                .name("mock name")
                .description("mock description");
    }

    @When("I POST the badge payload to the \\/badges endpoint")
    public void i_post_the_badge_payload_to_the_badges_endpoint() {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(Integer expectedStatusCode) {
        assertEquals((long) expectedStatusCode, lastStatusCode);
    }

    @When("I send a GET to the \\/badges endpoint")
    public void i_send_a_get_to_the_badges_endpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }
}

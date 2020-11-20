package ch.heigvd.amt.gamification.api.spec.steps;

import ch.heigvd.amt.gamification.ApiClient;
import ch.heigvd.amt.gamification.ApiException;
import ch.heigvd.amt.gamification.ApiResponse;
import ch.heigvd.amt.gamification.api.DefaultApi;
import ch.heigvd.amt.gamification.api.dto.Application;
import ch.heigvd.amt.gamification.api.dto.Badge;
import ch.heigvd.amt.gamification.api.dto.Event;
import ch.heigvd.amt.gamification.api.dto.EventProperties;
import ch.heigvd.amt.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Badge lastReceivedBadge;

    public BasicSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Badges server")
    public void there_is_a_Badges_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("I have a badge payload")
    public void i_have_a_badge_payload() throws Throwable {
        badge = new ch.heigvd.amt.gamification.api.dto.Badge()
                .name("mockName")
                .description("mockdesc");
    }

    @When("^I POST the badge payload to the /badges endpoint$")
    public void i_POST_the_badge_payload_to_the_badges_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @When("^I send a GET to the /badges endpoint$")
    public void iSendAGETToTheBadgesEndpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
    }

    @When("I send a GET to the URL in the location header")
    public void iSendAGETToTheURLInTheLocationHeader() {
        Integer id = Integer.parseInt(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getBadgeWithHttpInfo(id);
            processApiResponse(lastApiResponse);
            lastReceivedBadge = (Badge) lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the badge payload")
    public void iReceiveAPayloadThatIsTheSameAsTheBadgePayload() {
        assertEquals(badge, lastReceivedBadge);
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;


        //======== EVENTS ============
        List<String> apiKeyHeaderValues = (List<String>)lastApiResponse.getHeaders().get("x-api-key");
        lastReceivedApiKeyHeader = apiKeyHeaderValues != null ? apiKeyHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }

    // ================= APPLICATIONS ====================================
    Application application;

    @Given("there is an Applications server")
    public void there_is_an_applications_server() {
        assertNotNull(api);
    }

    @When("I send a GET to the \\/applications endpoint")
    public void i_send_a_get_to_the_applications_endpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Given("I have a application payload")
    public void i_have_a_application_payload() {
        application = new ch.heigvd.amt.gamification.api.dto.Application()
                .key("mockAppKey")
                .name("mockAppnName");
    }

    @When("I POST the application payload to the \\/applications endpoint")
    public void i_post_the_application_payload_to_the_applications_endpoint() {
        try {
            lastApiResponse = api.registerApplicationWithHttpInfo(application);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    // ============================= EVENTS ==================================
    Event event;
    private String lastReceivedApiKeyHeader;

    @Given("there is an Events server")
    public void there_is_an_events_server() {
        assertNotNull(api);
    }

    @Given("I have an event payload")
    public void i_have_an_event_payload() {
        EventProperties eventProperties = new EventProperties()
                .quantity(0)
                .type("mockPropertyType");

        event = new Event().properties(eventProperties)
                //.timestamp();
                .type("mockType")
                .userId("mockUserIs");
    }

    @When("I POST the event payload to the \\/events endpoint")
    public void i_post_the_event_payload_to_the_events_endpoint() {
        try {
            lastApiResponse = api.createEventWithHttpInfo(event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code with an x-api-key header")
    public void i_receive_a_status_code_with_an_x_api_key_header(Integer expectedStatusCode) {
        assertEquals((long)expectedStatusCode, lastStatusCode);
    }

    @When("I POST the event payload to the \\/events endpoint with app-key in the x-api-key header")
    public void i_post_the_event_payload_to_the_events_endpoint_with_app_key_in_the_x_api_key_header() {
        api.getApiClient().addDefaultHeader("x-api-key", lastReceivedApiKeyHeader);

        try {
            lastApiResponse = api.createEventWithHttpInfo(event);
            processApiResponse(lastApiResponse);
            //lastReceivedBadge = (Badge) lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }

}

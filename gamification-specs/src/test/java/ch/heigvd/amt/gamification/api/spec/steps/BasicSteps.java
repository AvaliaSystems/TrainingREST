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
  //  private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Badge lastReceivedBadge;

    private final String API_KEY_HEADER = "X-API-KEY";
    private String myApiKey;

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
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, environment.getLastStatusCode());
    }

    @When("^I send a GET to the /badges endpoint$")
    public void iSendAGETToTheBadgesEndpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
    }

    @When("I send a GET to the URL in the location header")
    public void iSendAGETToTheURLInTheLocationHeader() {
        lastReceivedLocationHeader = environment.getLastReceivedLocationHeader();
        Integer id = Integer.parseInt(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getBadgeWithHttpInfo(id);
            environment.processApiResponse(lastApiResponse);
            lastReceivedBadge = (Badge) lastApiResponse.getData();
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the badge payload")
    public void iReceiveAPayloadThatIsTheSameAsTheBadgePayload() {
        assertEquals(badge, lastReceivedBadge);
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
            lastApiResponse = api.getApplicationsWithHttpInfo();
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Given("I have a application payload")
    public void i_have_a_application_payload() {
        application = new ch.heigvd.amt.gamification.api.dto.Application()
                .apiKey("mockAppKey")
                .name("mockAppnName");
    }

    @When("I POST the application payload to the \\/applications endpoint")
    public void i_post_the_application_payload_to_the_applications_endpoint() {
        try {
            lastApiResponse = api.registerApplicationWithHttpInfo(application);
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Then("I receive a {int} status code with an x-api-key header")
    public void i_receive_a_status_code_with_an_x_api_key_header(Integer expectedStatusCode) {
        List<String> apiKeyHeaderValues = (List<String>)lastApiResponse.getHeaders().get(API_KEY_HEADER);
        myApiKey = apiKeyHeaderValues != null ? apiKeyHeaderValues.get(0) : null;
        api.getApiClient().setApiKey(myApiKey);
        assertEquals((long)expectedStatusCode, environment.getLastStatusCode());
    }

    @Given("I have successfully registered my app")
    public void i_have_successfully_registered_my_app() {
        i_have_a_application_payload();
        i_post_the_application_payload_to_the_applications_endpoint();
        i_receive_a_status_code_with_an_x_api_key_header(200);
    }


    // ============================= EVENTS ==================================

}

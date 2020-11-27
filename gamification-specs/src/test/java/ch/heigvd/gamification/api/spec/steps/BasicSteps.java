package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.ApiKey;
import ch.heigvd.gamification.api.dto.Application;
import ch.heigvd.gamification.api.dto.Badge;
import ch.heigvd.gamification.api.dto.User;
import ch.heigvd.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicSteps {

    private Environment environment;
    private DefaultApi api;

    User user;
    Application application;
    UUID apiKey;
    Badge badge;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private User lastReceivedUser;

    public BasicSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Gamification server")
    public void there_is_a_Gamification_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("I have a user payload")
    public void i_have_a_user_payload() throws Throwable {
        user = new ch.heigvd.gamification.api.dto.User()
          .id(37)
          .username("Jean");

    }

    @When("^I POST the user payload to the /users endpoint$")
    public void i_POST_the_user_payload_to_the_users_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createUserWithHttpInfo(apiKey, user);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @When("^I send a GET to the /users endpoint$")
    public void iSendAGETToTheUsersEndpoint() {
        try {
            lastApiResponse = api.getUsersWithHttpInfo(apiKey);
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
            lastApiResponse = api.getUserWithHttpInfo(id);
            processApiResponse(lastApiResponse);
            lastReceivedUser = (User)lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the user payload")
    public void iReceiveAPayloadThatIsTheSameAsTheUserPayload() {
        assertEquals(user, lastReceivedUser);
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

    @Given("I have an application payload")
    public void iHaveAnApplicationPayload() {
        application = new ch.heigvd.gamification.api.dto.Application()
                .name("testApp");
    }

    @When("^I POST the application payload to the /applications endpoint$")
    public void i_POST_the_application_payload_to_the_applications_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createApplicationWithHttpInfo(application);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a GET to the /applications endpoint with an API Key")
    public void iSendAGETToTheApplicationsEndpointWithAnAPIKey() {
        try {
            lastApiResponse = api.getApplicationWithHttpInfo(apiKey);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Given("I have a random API Key")
    public void iHaveARandomAPIKey() {
        apiKey = UUID.randomUUID();
    }

    @Given("I have a correct API key")
    public void iHaveACorrectAPIKey(){
        apiKey = ((ApiKey) lastApiResponse.getData()).getKey();
    }

    @Given("I have a badge payload")
    public void i_have_a_badge_payload() throws Throwable {
        badge = new ch.heigvd.gamification.api.dto.Badge()
                .id(24)
                .color("red")
                .description("for good bois only")
                .name("good boi badge");
    }

    @When("^I POST the badge payload to the /badges endpoint$")
    public void i_POST_the_badge_payload_to_the_badges_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(apiKey, badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a GET to the /badges endpoint$")
    public void iSendAGETToTheBadgesEndpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo(apiKey);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }
}

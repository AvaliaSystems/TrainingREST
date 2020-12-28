package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Application;
import ch.heigvd.gamification.api.dto.Badge;
import ch.heigvd.gamification.api.dto.Event;
import ch.heigvd.gamification.api.dto.EventEventparams;
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
    UUID apiKey;
    Badge badge;
    Event event;
    EventEventparams eventparams;

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


    public void setLastApiResponse(ApiResponse apiResponse){

        lastApiResponse = apiResponse;
        processApiResponse(lastApiResponse);
    }

    @Given("there is a Gamification server")
    public void there_is_a_Gamification_server() throws Throwable {
        assertNotNull(api);
    }





    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }



    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
    }

    /*@When("I send a GET to the URL in the location header")
    public void iSendAGETToTheURLInTheLocationHeader() {
        Integer id = Integer.parseInt(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getUserWithHttpInfo(id);
            processApiResponse(lastApiResponse);
            lastReceivedUser = (User)lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }*/

    @And("I receive a payload that is the same as the user payload")
    public void iReceiveAPayloadThatIsTheSameAsTheUserPayload() {
        assertEquals(user, lastReceivedUser);
    }

    public void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    public void processApiException(ApiException apiException) {

        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }


    public ApiResponse getlastApiResponse(){
        return lastApiResponse;
    }


    @Given("I have a badge payload")
    public void i_have_a_badge_payload() throws Throwable {
        badge = new ch.heigvd.gamification.api.dto.Badge()
                .id(0)
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

    @Given("I have an event payload")
    public void i_have_an_event_payload() throws Throwable {
        eventparams = new ch.heigvd.gamification.api.dto.EventEventparams()
                .username("Jean");
        event = new ch.heigvd.gamification.api.dto.Event()
                .eventType("generic_descritpion")
                .eventparams(eventparams)
                .timestamp((long)0);
    }

    @When("^I POST the event payload to the /events endpoint$")
    public void i_POST_the_event_payload_to_the_events_endpoint() throws Throwable {
        try {
            lastApiResponse = api.processEventWithHttpInfo(apiKey, event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

}

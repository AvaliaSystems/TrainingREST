package ch.heigvd.amt.gamification.api.spec.steps;

import ch.heigvd.amt.gamification.ApiException;
import ch.heigvd.amt.gamification.ApiResponse;
import ch.heigvd.amt.gamification.api.DefaultApi;
import ch.heigvd.amt.gamification.api.dto.Event;
import ch.heigvd.amt.gamification.api.dto.EventProperties;
import ch.heigvd.amt.gamification.api.dto.User;
import ch.heigvd.amt.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UsersSteps {
    private Environment environment;
    private DefaultApi api;


    private ApiResponse lastApiResponse;
    private Event event;
    private User user;

    private String lastReceivedUserLocationHeader;
    private User lastReceivedUser;


    public UsersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Users server")
    public void there_is_a_Users_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("I have an event payload for user {string}")
    public void i_have_an_event_payload_for_user(String userId) {
        EventProperties eventProperties = new EventProperties()
                .quantity(0)
                .subType("mockPropertyType");

        event = new Event().properties(eventProperties)
                //.timestamp();
                .eventType("mockType")
                .userId(userId);

        user = new User().appUserId(userId);
    }

    //TODO refactor w method from events
    @When("I POST the event payload to the \\/events endpoint for user")
    public void i_post_the_event_payload_to_the_events_endpoint_for_user() {
        try {
            lastApiResponse = api.createEventWithHttpInfo(event);
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }


    @When("I send a GET to \\/users endpoint")
    public void i_send_a_get_to_users_endpoint() {
        try {
            lastApiResponse = api.getUsersWithHttpInfo();
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Then("I receive a list of {int} users")
    public void i_receive_a_list_of_users(Integer nbUsers) {
        List<User> userList = (ArrayList) lastApiResponse.getData();
        assertEquals((long) nbUsers, userList.size());
    }

    @When("I send a GET to the URL in the userLocation header")
    public void i_send_a_get_to_the_url_in_the_user_location_header() {
        lastReceivedUserLocationHeader = environment.getLastReceivedUserLocationHeader();
        Integer id = Integer.parseInt(lastReceivedUserLocationHeader.substring(lastReceivedUserLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getUserWithHttpInfo(id);
            environment.processApiResponse(lastApiResponse);
            lastReceivedUser = (User) lastApiResponse.getData();
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }

    @Then("I receive a payload for user with userId")
    public void i_receive_a_payload_for_user_with_userId() {
        assertEquals(user.getAppUserId(), lastReceivedUser.getAppUserId());
    }

}

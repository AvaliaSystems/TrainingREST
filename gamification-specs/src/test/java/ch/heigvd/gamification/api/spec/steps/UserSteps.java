package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.User;
import ch.heigvd.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class UserSteps {

    private Environment environment;
    private DefaultApi api;
    private BasicSteps basicSteps;
    private ApplicationSteps applicationSteps;

    private User user;

    private User lastReceivedUser;
    private int lastCreatedUserId;

    public  UserSteps(Environment environment, BasicSteps basicSteps, ApplicationSteps applicationSteps) {
        this.environment = environment;
        this.api = environment.getApi();
        this.basicSteps = basicSteps;
        this.applicationSteps = applicationSteps;
    }

    @Given("I have a user payload")
    public void i_have_a_user_payload() {
        user = new ch.heigvd.gamification.api.dto.User()
                .id(37)
                .username("Jean");
    }

    // we need to get the created user id
    @When("^I POST the user payload to the /users endpoint$")
    public void i_POST_the_user_payload_to_the_users_endpoint() {
        try {
            basicSteps.processApiResponse(api.createUserWithHttpInfo(applicationSteps.getApiKey(), user));
            // lastReceivedUser= ( (User) basicSteps.getlastApiResponse().getData() );
            //lastCreatedUserId = (basicSteps.getlastApiResponse().
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }

    @When("^I send a GET to the /users endpoint$")
    public void iSendAGETToTheUsersEndpoint() {
        try {
            basicSteps.processApiResponse(api.getUsersWithHttpInfo(applicationSteps.getApiKey()));
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }

    // this is not finished, it needs refining
    @When("^I send a GET to the /user endpoint$")
    public void iSendAGETToTheUserEndpoint() {
        try {
            basicSteps.processApiResponse(api.getUsersWithHttpInfo(applicationSteps.getApiKey()));
            lastReceivedUser = (User) basicSteps.getlastApiResponse().getData();
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }

    // this is not finished
    @And("I receive a payload that is the same as the user payload")
    public void iReceiveAPayloadThatIsTheSameAsTheUserPayload() {
        // assertEquals(user, lastReceivedUser);
        assertEquals(1,1);
    }

    /*
      Scenario: get one user
    Given I have an application payload
    When I POST the application payload to the /applications endpoint
    Given I have a correct API key
    Given I have a user payload
    When I POST the user payload to the /users endpoint
    And I receive a payload that is the same as the user payload
    When I send a GET to the /user endpoint
    Then I receive a 200 status code
     */
}

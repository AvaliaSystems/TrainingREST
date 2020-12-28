package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.User;
import ch.heigvd.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class UserSteps {


    private Environment environment;
    private DefaultApi api;
    private BasicSteps basicSteps;
    private ApplicationSteps applicationSteps;

    private User user;

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

    @When("^I POST the user payload to the /users endpoint$")
    public void i_POST_the_user_payload_to_the_users_endpoint() {
        try {
            basicSteps.processApiResponse(api.createUserWithHttpInfo(applicationSteps.getApiKey(), user));
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

}

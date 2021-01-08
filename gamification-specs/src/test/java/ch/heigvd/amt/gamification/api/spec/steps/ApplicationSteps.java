package ch.heigvd.amt.gamification.api.spec.steps;

import ch.heigvd.amt.gamification.ApiException;
import ch.heigvd.amt.gamification.ApiResponse;
import ch.heigvd.amt.gamification.api.DefaultApi;
import ch.heigvd.amt.gamification.api.dto.ApplicationRegistration;
import ch.heigvd.amt.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class ApplicationSteps {
    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApplicationRegistration applicationRegistration;


    private final String API_KEY_HEADER = "X-API-KEY";
    private String myApiKey;

    public ApplicationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is an Applications server")
    public void there_is_an_applications_server() {
        assertNotNull(api);
    }

    // TODO : L'endpoint GET n'existe plus
   /* @When("I send a GET to the \\/applications endpoint")
    public void i_send_a_get_to_the_applications_endpoint() {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo();
            environment.processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            environment.processApiException(e);
        }
    }*/

    @Given("I have a application payload")
    public void i_have_a_application_payload() {
        applicationRegistration = new ApplicationRegistration()
                .name("mockAppnName");
    }

    @When("I POST the application payload to the \\/applications endpoint")
    public void i_post_the_application_payload_to_the_applications_endpoint() {
        try {
            lastApiResponse = api.registerApplicationWithHttpInfo(applicationRegistration);
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

    @Then("I receive a {int} status code with another x-api-key header")
    public void i_receive_a_status_code_with_another_x_api_key_header(Integer expectedStatusCode) {
        List<String> apiKeyHeaderValues = (List<String>)lastApiResponse.getHeaders().get(API_KEY_HEADER);
        String newApiKey = apiKeyHeaderValues != null ? apiKeyHeaderValues.get(0) : null;
        assertNotEquals(myApiKey, newApiKey);
        assertEquals((long)expectedStatusCode, environment.getLastStatusCode());
    }
}

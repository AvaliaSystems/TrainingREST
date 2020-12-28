package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.ApiKey;
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

public class ApplicationSteps {

    private Environment environment;
    private DefaultApi api;
    private BasicSteps basicSteps;

    Application application;
    UUID apiKey;


    public ApplicationSteps(Environment environment, BasicSteps basicSteps) {
        this.environment = environment;
        this.api = environment.getApi();
        this.basicSteps=basicSteps;
    }

    @Given("I have an application payload")
    public void iHaveAnApplicationPayload() {
        application = new ch.heigvd.gamification.api.dto.Application()
                .name("testApp");
    }


    @When("^I POST the application payload to the /applications endpoint$")
    public void i_POST_the_application_payload_to_the_applications_endpoint() throws Throwable {
        try {
            basicSteps.processApiResponse(api.createApplicationWithHttpInfo(application));
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }

    @Given("I have a correct API key")
    public void iHaveACorrectAPIKey(){
        apiKey = ((ApiKey) basicSteps.getlastApiResponse().getData()).getKey();
    }

    @When("^I send a GET to the /applications endpoint with an API Key")
    public void iSendAGETToTheApplicationsEndpointWithAnAPIKey() {
        try {
            basicSteps.processApiResponse(api.getApplicationWithHttpInfo(apiKey));
        } catch (ApiException e) {
            basicSteps.processApiException(e);
        }
    }

    @Given("I have a random API Key")
    public void iHaveARandomAPIKey() {
        apiKey = UUID.randomUUID();
    }
}

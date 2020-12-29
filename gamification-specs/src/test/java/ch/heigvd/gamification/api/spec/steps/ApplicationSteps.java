package ch.heigvd.gamification.api.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.ApiKey;
import ch.heigvd.gamification.api.dto.Application;
import ch.heigvd.gamification.api.spec.helpers.Environment;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.UUID;

public class ApplicationSteps {

    private Environment environment;
    private DefaultApi api;
    private BasicSteps basicSteps;

    private Application application;
    private UUID apiKey;

    public ApplicationSteps(Environment environment, BasicSteps basicSteps) {
        this.environment = environment;
        this.api = environment.getApi();
        this.basicSteps=basicSteps;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    @Given("I have an application payload")
    public void iHaveAnApplicationPayload() {
        application = new ch.heigvd.gamification.api.dto.Application()
                .name("testApp");
    }

    @When("^I POST the application payload to the /applications endpoint$")
    public void i_POST_the_application_payload_to_the_applications_endpoint() {
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

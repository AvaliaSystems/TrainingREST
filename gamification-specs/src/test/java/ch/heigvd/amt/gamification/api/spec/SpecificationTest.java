package ch.heigvd.amt.gamification.api.spec;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import  org.junit.Test;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/scenarios/", plugin = {"pretty", "html:target/cucumber"})
public class SpecificationTest {
    public SpecificationTest(){
    }
    @Test
    public void testMain(){

    }
};


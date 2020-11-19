Feature: Application registration

  Scenario: Register another application
    Given I have an application payload
    When I POST it to the /applications endpoint
    Then I receive a 201 status code

  Scenario: Check if the application is correctly registered
    Given I have an application payload
    When I POST it to the /applications endpoint
    And I ask for a list of registered applications with a GET on the /registrations endpoint
    Then I see my application in the list
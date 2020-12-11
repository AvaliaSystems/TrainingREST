Feature: Basic operations on users

  Background:
    Given there is a Users server



  Scenario: create 2 users and retrieve
    Given I have successfully registered my app
    Given I have an event payload for user "usersFeature1"
    When I POST the event payload to the /events endpoint for user
    #TODO 201
    Then I receive a 200 status code
    Given I have an event payload for user "usersFeature2"
    When I POST the event payload to the /events endpoint for user
    #TODO 201
    Then I receive a 200 status code

    #Retrieval
    When I send a GET to /users endpoint
    Then I receive a 200 status code
    And I receive a list of 2 users
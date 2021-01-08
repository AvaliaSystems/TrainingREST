Feature: Basic operations on users

  Background:
    Given there is a Users server

  Scenario: create 1 user and retrieve
    Given I have successfully registered my app
    Given I have an event payload for user "usersFeature1"
    When I POST the event payload to the /events endpoint for user
    Then I receive a 201 status code
    When I send a GET to the URL in the userLocation header
    Then I receive a 200 status code
    And I receive a payload for user with userId

  Scenario: create 2 users and retrieve
    Given I have successfully registered my app
    Given I have an event payload for user "usersFeature1"
    When I POST the event payload to the /events endpoint for user
    Then I receive a 201 status code
    Given I have an event payload for user "usersFeature2"
    When I POST the event payload to the /events endpoint for user
    Then I receive a 201 status code

    #Retrieval
    When I send a GET to /users endpoint
    Then I receive a 200 status code
    And I receive a list of 2 users

  Scenario: check app can't see users from other apps
    #Post 1
    Given I have successfully registered my app
    Given I have an event payload for user "usersFeature1"
    When I POST the event payload to the /events endpoint for user
    Then I receive a 201 status code

    #Post 2
    Given I have successfully registered my app
    Given I have an event payload for user "usersFeature2"
    When I POST the event payload to the /events endpoint for user
    Then I receive a 201 status code

    #retrieval
    When I send a GET to /users endpoint
    Then I receive a 200 status code
    And I receive a list of 1 users
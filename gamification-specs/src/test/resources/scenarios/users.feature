Feature: User operations on applications

  Background:
    Given there is a Gamification server

  Scenario: create a user
    Given I have an application payload
    When I POST the application payload to the /applications endpoint
    Given I have a correct API key
    Given I have a user payload
    When I POST the user payload to the /users endpoint
    Then I receive a 201 status code

  Scenario: get one user
    Given I have an application payload
    When I POST the application payload to the /applications endpoint
    Given I have a correct API key
    Given I have a user payload
    When I POST the user payload to the /users endpoint
    When I send a GET to the /user endpoint
    Then I receive a 200 status code
    And I receive a payload that is the same as the user payload

  Scenario: get the list of users
    Given I have an application payload
    When I POST the application payload to the /applications endpoint
    Given I have a correct API key
    When I send a GET to the /users endpoint
    Then I receive a 200 status code



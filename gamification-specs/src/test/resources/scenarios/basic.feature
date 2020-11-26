Feature: Basic operations on users

  Background:
    Given there is a Gamification server

  Scenario: create a user
    Given I have a user payload
    When I POST the user payload to the /users endpoint
    Then I receive a 201 status code

  Scenario: get the list of users
    When I send a GET to the /users endpoint
    Then I receive a 200 status code
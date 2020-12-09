Feature: Basic operations on events

  Background:
    Given there is an Events server

  Scenario: create an event anonymous
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 401 status code

     #TODO 201
  Scenario: create an event authenticated
    Given I have successfully registered my app
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 200 status code

  Scenario: fail
    Given i do shit


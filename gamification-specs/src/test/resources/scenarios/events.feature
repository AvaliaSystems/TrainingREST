Feature: Basic operations on events

  Background:
    Given there is an Applications server
    Given there is an Events server

  Scenario: create an event anonymous
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 401 status code

  Scenario: create an event authenticated
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 200 status code with an x-api-key header
    Given I have an event payload
    When I POST the event payload to the /events endpoint with app-key in the x-api-key header
    Then I receive a 200 status code
Feature: Basic operations on events

  Background:
    Given there is an Applications server
    Given there is an Events server

  Scenario: create an event anonymous
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 401 status code



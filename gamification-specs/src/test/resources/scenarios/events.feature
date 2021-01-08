Feature: Basic operations on events

  Background:
    Given there is an Events server

  Scenario: create an event anonymous
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 401 status code
    
  Scenario: create an event authenticated
    Given I have successfully registered my app
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 201 status code

    #TODO
#  Scenario: can create 2 events with same payload
#    Given I have an event payload
#    When I POST the event payload to the /events endpoint
#    Then I receive a 201 status code
#    When I POST the event payload to the /events endpoint
#    Then I receive a 201 status code
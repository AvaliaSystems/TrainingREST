Feature: Basic operations on applications

  Background:
    Given there is an Applications server

  Scenario: create an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

  Scenario: get the list of applications
    When I send a GET to the /applications endpoint
    Then I receive a 200 status code
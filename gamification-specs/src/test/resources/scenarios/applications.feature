Feature: Basic operations on applications

  Background:
    Given there is an Applications server

  Scenario: create an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

  Scenario: create an application and retrieve api key
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code with an x-api-key header

  Scenario: can create 2 apps with same payload
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code with an x-api-key header
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code with another x-api-key header
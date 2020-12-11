Feature: Basic operations on applications

  Background:
    Given there is an Applications server

        #TODO 201
  Scenario: create an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 200 status code

    #TODO 201 on POST
  Scenario: create an application and retrieve api key
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 200 status code with an x-api-key header
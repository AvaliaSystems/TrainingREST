Feature: Basic operations on badges

  Background:
    Given there is a Badges server

  Scenario: create a badge unauthenticated
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 401 status code

  Scenario: create a badge
    Given I have successfully registered my app
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  Scenario: get the list of badges
    Given I have successfully registered my app
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code

    Scenario: get single badge from id
      Given I have successfully registered my app
      Given I have a badge payload
      When I POST the badge payload to the /badges endpoint
      Then I receive a 201 status code
      When I send a GET to the URL in the location header
      Then I receive a 200 status code
      And I receive a payload that is the same as the badge payload

  Scenario: multiple badges
    Given I have successfully registered my app
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    When I send a GET to the URL in the location header
    Then I receive a 200 status code
    And I receive a payload that is the same as the badge payload
    Given I have a badge payload named "fifi"
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code
    And I receive a list of 2 badges

  Scenario: can't create 2 badges with same payload
    Given I have successfully registered my app
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    When I POST the badge payload to the /badges endpoint
    Then I receive a 409 status code


  Scenario: check app can't see badges from other apps
    #Post 1
    Given I have successfully registered my app
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    #Post 2
    Given I have successfully registered my app
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

    #retrieval
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code
    And I receive a list of 1 badges
Feature: Basic operations on point scales

  Background:
    Given there is a PointScales server

  Scenario: create a point scale unauthenticated
    Given I have a pointScale payload
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 401 status code

  Scenario: create a point scale
    Given I have successfully registered my app
    Given I have a pointScale payload
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code

  Scenario: get the list of multiple point scales
    Given I have successfully registered my app
    Given I have a pointScale payload "pointScale1"
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code
    Given I have a pointScale payload "pointScale2"
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code
    When I send a GET to the /pointScales endpoint
    Then I receive a 200 status code
    And I receive a list of 2 pointScales

  Scenario: get single point scale from id
    Given I have successfully registered my app
    Given I have a pointScale payload
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code
    When I send a GET to the URL in the location header for a pointScale
    Then I receive a 200 status code
    And I receive a payload that is the same as the pointScale payload

  Scenario: check app can't see pointScales from other apps
    #Post 1
    Given I have successfully registered my app
    Given I have a pointScale payload
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code
    #Post 2
    Given I have successfully registered my app
    Given I have a pointScale payload
    When I POST the pointScale payload to the /pointScales endpoint
    Then I receive a 201 status code

    #retrieval
    When I send a GET to the /pointScales endpoint
    Then I receive a 200 status code
    And I receive a list of 1 pointScales
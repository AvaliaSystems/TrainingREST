Feature: Badge operations on applications

  Background:
    Given there is a Gamification server

Scenario: create a badge
Given I have an application payload
When I POST the application payload to the /applications endpoint
Given I have a correct API key
Given I have a badge payload
When I POST the badge payload to the /badges endpoint
Then I receive a 201 status code

Scenario: get the list of badges
Given I have an application payload
When I POST the application payload to the /applications endpoint
Given I have a correct API key
When I send a GET to the /badges endpoint
Then I receive a 200 status code
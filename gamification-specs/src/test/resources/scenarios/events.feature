Feature: Events operations on applications

  Background:
    Given there is a Gamification server

Scenario: send an event to the API
Given I have an application payload
When I POST the application payload to the /applications endpoint
Given I have a correct API key
Given I have a user payload
When I POST the user payload to the /users endpoint
Given I have a badge payload
When I POST the badge payload to the /badges endpoint
Given I have an event payload
When I POST the event payload to the /events endpoint
Then I receive a 202 status code
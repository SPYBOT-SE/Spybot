Feature: Select Level
  As a PLAYER
  I want to select a level

  Background:
    Given the level hasn't been chosen

  Scenario: Button is clicked
    When I click on a button in the level menu
    Then the new level is being loaded

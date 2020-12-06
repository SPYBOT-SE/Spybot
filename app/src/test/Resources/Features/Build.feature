Feature: Build
  As a PLAYER
  I want to expand the board

  Background:
    Given the level has been chosen

  Scenario: Activate field
    Given it is my turn
    And a pawn is chosen which can build
    And the pawn can still build/attack
    When I click on a field outside of the board
    Then this field gets activated

  Scenario: Field is out of range
    Given it is my turn
    And a pawn is chosen which can build
    And the pawn can still build/attack
    When I click on a field out of range
    Then the pawn should not activate the field

  Scenario: Field is already active
    Given it is my turn
    And a pawn is chosen which can build
    And the pawn can still build/attack
    When I click on a field which is active
    Then the pawn should not activate the field
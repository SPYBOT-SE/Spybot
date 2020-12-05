Feature: Move Pawn
  As a PLAYER
  I want to move with one Pawn from one field to another

  Background:
    Given the level has been chosen
    And the pawn can still move

  Scenario: Correct move
    Given it is my turn
    And a pawn is chosen
    And the pawn can move
    When I click on a field on the board
    Then the icon of the pawn should be on the new field

  Scenario: Field is out of range
    Given it is my turn
    And a pawn is chosen
    And the pawn can move
    When I click on a field on the board out of range
    Then the pawn should not move

  Scenario: Field is occupied by another pawn
    Given it is my turn
    And a pawn is chosen
    And the pawn can move
    When I click on a field which is occupied by another pawn
    Then the pawn should not move
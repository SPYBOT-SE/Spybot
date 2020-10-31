Feature: Attack
  As a PLAYER
  I want to attack an enemy pawn with on of my pawns

  Background:
    Given the level has been chosen
    And the pawn can still attack

  Scenario: Attack enemy
    Given it is my turn
    And a pawn is chosen
    And the pawn can attack
    When I click on a field on the board with an enemy pawn
    Then the health and body should shrink
    And get deleted if the pawn dies

  Scenario: Field is out of range
    Given it is my turn
    And a pawn is chosen
    And the pawn can attack
    When I click on a field on the board out of range
    Then the pawn should not attack

  Scenario: Field is not occupied by an enemy pawn
    Given it is my turn
    And a pawn is chosen
    And the pawn can attack
    When I click on a field which is not occupied by an enemy pawn
    Then the pawn should not attack
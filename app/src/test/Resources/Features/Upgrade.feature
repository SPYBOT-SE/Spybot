Feature:Upgrade
  As a User
  I want to upgrade one of my pawns

  Background:
    Given the user is in catalogue view
    And the pawn can be upgraded

  Scenario: User upgrades pawn
    Given at least one pawn can be upgraded
    And a pawn is chosen
    And an upgrade variant is chosen
    And user has enough money
    When I click upgrade
    Then the pawn shoud be upgraded to that version
    And old version should be deleted
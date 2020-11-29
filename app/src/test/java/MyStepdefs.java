import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {
    @Given("the level has been chosen")
    public void theLevelHasBeenChosen() {
    }

    @And("the pawn can still attack")
    public void thePawnCanStillAttack() {
    }

    @Given("it is my turn")
    public void itIsMyTurn() {

    }

    @And("a pawn is chosen")
    public void aPawnIsChosen() {
    }

    @And("the pawn can attack")
    public void thePawnCanAttack() {
    }

    @When("I click on a field on the board with an enemy pawn")
    public void iClickOnAFieldOnTheBoardWithAnEnemyPawn() {
    }

    @Then("the health and body shrinks")
    public void theHealthAndBodyShrinks() {
        throw new io.cucumber.java.PendingException();
    }

    @And("get deleted if the pawn dies")
    public void getDeletedIfThePawnDies() {
    }

    @When("I click on a field on the board out of range")
    public void iClickOnAFieldOnTheBoardOutOfRange() {
    }

    @Then("the pawn should not attack")
    public void thePawnShouldNotAttack() {
    }

    @When("I click on a field which is not occupied by an enemy pawn")
    public void iClickOnAFieldWhichIsNotOccupiedByAnEnemyPawn() {
    }
}

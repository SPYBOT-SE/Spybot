import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BuildStepDef {
    @And("a pawn is chosen which can build")
    public void aPawnIsChosenWhichCanBuild() {
    }

    @And("the pawn can still build\\/attack")
    public void thePawnCanStillBuildAttack() {
    }

    @When("I click on a field outside of the board")
    public void iClickOnAFieldOutsideOfTheBoard() {
    }

    @Then("this field gets activated")
    public void thisFieldGetsActivated() {
    }

    @When("I click on a field out of range")
    public void iClickOnAFieldOutOfRange() {
    }

    @Then("the pawn should not activate the field")
    public void thePawnShouldNotActivateTheField() {
    }

    @When("I click on a field which is active")
    public void iClickOnAFieldWhichIsActive() {
    }
}

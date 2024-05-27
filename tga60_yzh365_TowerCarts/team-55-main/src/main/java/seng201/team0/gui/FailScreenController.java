package seng201.team0.gui;
/**
 * Controller for the fail screen.
 * @author tga60.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

public class FailScreenController {

    @FXML
    private Label roundsCompletedLabel;
    private GameManager gameManager;

    /**
     * Quits game
     */
    @FXML
    private void onQuitPressed() {
        System.exit(0);
    }


    /**
     * Constructs a FailScreenController.
     * @param gameManager The game manger for setting and getting variables.
     */
    public FailScreenController(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * Sets variables.
     */
    public void initialize()
    {
        roundsCompletedLabel.setText((int) gameManager.rounds.getCurrentRound() -1+" Rounds Completed");
    }

}

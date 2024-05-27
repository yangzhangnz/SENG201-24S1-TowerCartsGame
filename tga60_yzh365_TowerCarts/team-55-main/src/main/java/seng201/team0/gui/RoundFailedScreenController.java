package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

/**
 * Controller for the round failed screen.
 * @author tga60.
 */
public class RoundFailedScreenController {



    @FXML
    private Label roundsCompletedLabel;
    private GameManager gameManager;

    /**
     * Quits game.
     */
    @FXML
    private void onQuitPressed() {
        System.exit(0);
    }

    /**
     * Closes current screen.
     * Launch the game screen.
     */
    @FXML
    private void onRestartPressed() {
            gameManager.restartRound();
            gameManager.launchGameScreen();
        }



    /**
     * Constructs a v.
     * @param gameManager The game manger for setting and getting variables.
     */
    public RoundFailedScreenController(GameManager gameManager)
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

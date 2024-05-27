package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

/**
 * Controller for the win screen.
 * @author tga60.
 */
public class WinScreenController {

    @FXML
    private Label roundsCompletedLabel;
    @FXML
    private Label wellDoneLabel;
    private GameManager gameManager;

    /**
     * Quits game.
     */
    @FXML
    private void onQuitPressed() {
        System.exit(0);
    }

    /**
     * Constructs a WinScreenController.
     * @param gameManager The game manger for setting and getting variables.
     */
    public WinScreenController(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * Sets variables.
     */
    public void initialize()
    {
        roundsCompletedLabel.setText("You Completed " + (int) (gameManager.rounds.getCurrentRound()) + " Rounds");
        wellDoneLabel.setText("Well done "+ gameManager.getPlayer().getName());
    }

}

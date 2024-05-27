package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

/**
 * Controller for the round complete screen.
 * @author tga60.
 */
public class RoundCompleteScreenController {

    @FXML
    private Label moneyLabel;

    @FXML
    private Label roundCompletedLabel;
    private GameManager gameManager;

    /**
     * Closes current screen.
     * Launch the game screen.
     */
    @FXML
    public void onNextRound(){
        gameManager.nextRound();
        gameManager.launchGameScreen();
    }




    /**
     * Constructs a RoundCompleteScreenController.
     * @param gameManager The game manger for setting and getting variables.
     */
    public RoundCompleteScreenController(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * Sets variables.
     */
    public void initialize()
    {
        roundCompletedLabel.setText("Round "+ (int) gameManager.rounds.getCurrentRound() +" Completed");
        moneyLabel.setText("Balance: $" + gameManager.getPlayer().getMoney());
    }

}

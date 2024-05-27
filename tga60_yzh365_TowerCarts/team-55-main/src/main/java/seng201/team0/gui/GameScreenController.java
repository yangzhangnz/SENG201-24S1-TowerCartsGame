package seng201.team0.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import seng201.team0.factors.Tower;
import seng201.team0.interfaces.TimerListener;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

import java.util.List;

/**
 * Controller for the game screen.
 * @author tga60 & yzh365.
 */
public class GameScreenController implements TimerListener {
    @FXML
    private Label roundCounter, livesCounter;
    @FXML
    private Label totalMoneyCounter;
    @FXML
    private Label tower1MoneyCounter, tower2MoneyCounter, tower3MoneyCounter, tower4MoneyCounter, tower5MoneyCounter;
    @FXML
    private Label  tower1MaterialCounter, tower2MaterialCounter, tower3MaterialCounter, tower4MaterialCounter, tower5MaterialCounter;
    @FXML
    private Label tower1Material, tower2Material, tower3Material, tower4Material, tower5Material;
    @FXML
    private Button selectedTower1, selectedTower2, selectedTower3,selectedTower4, selectedTower5;
    @FXML
    private ProgressBar tower1CartDistance, tower2CartDistance, tower3CartDistance, tower4CartDistance, tower5CartDistance;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button btnStart;
    List<Label> towerMaterials;
    public List<Label> materialCounters;
    List<Label> moneyCounters;
    public List<ProgressBar> cartDistances;
    List<Button> selectedTowers;

    private ItemManager itemManager;
    private GameManager gameManager;

    /**
     * Constructs a GameScreenController.
     * @param gameManager The game manger for setting and getting variables.
     */
    public GameScreenController(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }


    /**
     * On pressed if the round isn't playing it will start he round, if it is it will pause the round.
     */
    @FXML
    private void onStartPressed(){
        if(!gameManager.counterService.getIsRunning()) {
            this.gameManager.getCounterService().setTimerListener(this);
            gameManager.getCounterService().startTimer();
            btnStart.setText("Pause Round");
        }
        else
        {
            gameManager.getCounterService().pauseTimer();
            btnStart.setText("Play Round");
        }
    }

    /**
     * Set the up list with their corresponding buttons and labels.
     * Sets variables.
     */
    public void initialize()
    {
        onTimeUpdated(gameManager.counterService.getCurrentTime());

        itemManager = gameManager.getItemManager();
        inventoryButton.setText(gameManager.getPlayer().getName()+"'s Items");

        updateRoundCounter();
        updateLivesCounter();
        updateTotalMoneyCounter();

        towerMaterials = List.of(tower1Material, tower2Material, tower3Material, tower4Material, tower5Material);
        materialCounters = List.of( tower1MaterialCounter, tower2MaterialCounter, tower3MaterialCounter, tower4MaterialCounter, tower5MaterialCounter);
        moneyCounters = List.of( tower1MoneyCounter, tower2MoneyCounter, tower3MoneyCounter, tower4MoneyCounter, tower5MoneyCounter);
        cartDistances = List.of( tower1CartDistance, tower2CartDistance, tower3CartDistance, tower4CartDistance, tower5CartDistance);
        selectedTowers = List.of(selectedTower1, selectedTower2, selectedTower3, selectedTower4, selectedTower5);


        for (int i = 0; i < itemManager.getTowerList().size(); i++) {
            towerMaterials.get(i).setText(itemManager.getTowerList().get(i).getItemName());
            selectedTowers.get(i).setText(itemManager.getTowerList().get(i).getItemName());
        }
    }

    /**
     * Updates the round counter label to display the current round and max round in Round.
     */
    private void updateRoundCounter(){
        roundCounter.setText((int)gameManager.getRounds().getCurrentRound() + "/" +
                (int)gameManager.getRounds().getMaxRound());
    }

    /**
     * Updates the lives counter label to display the value of lives in Player.
     */
    private void updateLivesCounter(){
        livesCounter.setText("Lives " + gameManager.getPlayer().getLives());
    }

    /**
     * Updates the total money counter label to display the value of money in Player.
     */
    private void updateTotalMoneyCounter(){
        totalMoneyCounter.setText("Money: $" + gameManager.getPlayer().getMoney());
    }

    /**
     * Closes current screen.
     * Launch the player screen.
     */
    @FXML
    private void handleOpenShop() {
        gameManager.getCounterService().pauseTimer();
        gameManager.launchShopScreen();
    }


    /**
     * Closes current screen.
     * Launch the player screen.
     */
    @FXML
    private void handleInventoryButton() {
        gameManager.getCounterService().pauseTimer();
        gameManager.launchPlayerScreen();

    }


    /**
     * Observes the current time in the counter service.
     * Updates the gui values every time current time is updated.
     * Changes colour of carts progress bar depending on how many broken tower there are for that corresponding cart.
     * @param currentTime The number of seconds into the round.
     */
    @Override
    public void onTimeUpdated(int currentTime) {
        Platform.runLater(() ->
        {
            totalMoneyCounter.setText("Balance: $" + gameManager.getPlayer().getMoney());
            for (int i = 0; i < moneyCounters.size(); i++)
            {
                moneyCounters.get(i).setText("$" + itemManager.getTowerList().get(i).getMoneyMade());
            }
            for (int i = 0; i < cartDistances.size(); i++)
            {
                cartDistances.get(i).setProgress(gameManager.cartsList.get(i).getCurrentDistance() / 100);
            }
            for (int i = 0; i < materialCounters.size(); i++)
            {
                materialCounters.get(i).setText((int)gameManager.cartsList.get(i).getCurrentSize() + " / " +
                        gameManager.cartsList.get(i).getMaxSize());
            }
            for (int i = 0; i < itemManager.getTowerList().size(); i++)
            {
                Tower tempTower = itemManager.getTowerList().get(i);
                Tower tempReserve = itemManager.getReserveTowers()[i];
                boolean tempReserveIsBroken;
                if(tempReserve == null)
                {
                    tempReserveIsBroken = true;
                }
                else
                {
                    tempReserveIsBroken = tempReserve.getIsBroken();
                }
                if(!tempTower.getIsBroken())
                {
                    selectedTowers.get(i).setText(tempTower.getItemName());
                    selectedTowers.get(i).setDisable(false);
                    cartDistances.get(i).setStyle("-fx-accent: #00AA00;");
                }
                else if(!tempReserveIsBroken)
                {
                    selectedTowers.get(i).setText("Reserve\n" + tempReserve.getItemName());
                    cartDistances.get(i).setStyle("-fx-accent: #DDAA00;");
                    selectedTowers.get(i).setDisable(false);
                }
                else {
                    selectedTowers.get(i).setText("Broken");
                    cartDistances.get(i).setStyle("-fx-accent: #AA0000;");
                    selectedTowers.get(i).setDisable(true);
                }
            }

        });
    }
}

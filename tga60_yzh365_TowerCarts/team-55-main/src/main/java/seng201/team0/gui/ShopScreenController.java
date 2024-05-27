package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.enums.Type;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;
import seng201.team0.services.MarketService;

import java.util.List;


/**
 * Controller for the shop screen.
 * @author tga60 & yzh365.
 */
public class ShopScreenController {

    @FXML
    private Label lblTower1, lblTower2, lblTower3, lblTower4, lblTower5;
    List<Label> lblTowerTypes;
    @FXML
    private Button btnBuyTower1, btnBuyTower2, btnBuyTower3, btnBuyTower4, btnBuyTower5;
    List<Button> btnBuyTowers;
    @FXML
    private Button btnSellTower1, btnSellTower2, btnSellTower3, btnSellTower4, btnSellTower5;
    List<Button> btnSellTowers;
    @FXML
    private Label lblBuyPriceTower1, lblBuyPriceTower2, lblBuyPriceTower3, lblBuyPriceTower4, lblBuyPriceTower5;
    List<Label> lblBuyPrices;
    @FXML
    private Label lblSellPriceTower1, lblSellPriceTower2, lblSellPriceTower3, lblSellPriceTower4, lblSellPriceTower5;
    List<Label> lblSellPrices;
    @FXML
    private Label lblTower1Owned, lblTower2Owned, lblTower3Owned, lblTower4Owned, lblTower5Owned;
    List<Label> lblTowersOwned;
    @FXML
    private Label lblBuyPriceUpgrade, lblBuyPriceRepair, lblBuyPriceChangeType;
    @FXML
    private Label lblSellPriceUpgrade, lblSellPriceRepair, lblSellPriceChangeType;
    @FXML
    private Label lblUpgradeOwned, lblRepairOwned, lblChangeTypeOwned;
    @FXML
    private Label lblMoney;


    @FXML
    private Button btnSellUpgrade, btnSellRepair, btnSellChangeType;
    private final MarketService marketService;
    private GameManager gameManager;
    private ItemManager itemManager;



    /**
     * Constructs a ShopScreenController.
     * @param marketService The for managing transactions between shop and player.
     */
    public ShopScreenController(MarketService marketService) {
        this.marketService = marketService;
        gameManager = marketService.getGameManager();
        itemManager = gameManager.getItemManager();
    }

    /**
     * Set the up list with their corresponding buttons and labels.
     * Sets variables.
     */
    public void initialize() {


        lblTowerTypes = List.of(lblTower1, lblTower2, lblTower3, lblTower4, lblTower5);
        btnBuyTowers = List.of(btnBuyTower1, btnBuyTower2, btnBuyTower3, btnBuyTower4, btnBuyTower5);
        btnSellTowers = List.of(btnSellTower1, btnSellTower2, btnSellTower3, btnSellTower4, btnSellTower5);
        lblBuyPrices = List.of(lblBuyPriceTower1, lblBuyPriceTower2, lblBuyPriceTower3, lblBuyPriceTower4, lblBuyPriceTower5);
        lblSellPrices = List.of(lblSellPriceTower1, lblSellPriceTower2, lblSellPriceTower3, lblSellPriceTower4, lblSellPriceTower5);
        lblTowersOwned = List.of(lblTower1Owned, lblTower2Owned, lblTower3Owned, lblTower4Owned, lblTower5Owned);
        updateUI();
        buyTowers();
        sellTowers();
    }

    /**
     * Gets the index of the button pressed and passes it to market service to buy a reserve.
     * Updates gui variables.
     */
    private void buyTowers(){
        for (int i = 0; i < btnBuyTowers.size(); i++) {
            final int index = i;
            btnBuyTowers.get(i).setOnAction(event -> {
                marketService.buyTower(index);
                updateUI();
            });
        }
    }

    /**
     * Gets the index of the button pressed and passes it to market service to sell a reserve.
     * Updates gui variables.
     */
    private void sellTowers(){
        for (int i = 0; i < btnSellTowers.size(); i++) {
            final int index = i;
            btnSellTowers.get(i).setOnAction(event -> {
                marketService.sellTower(index);
                updateUI();
            });
        }
    }

    /**
     * Calls market service to buy repair amount.
     * Updates gui variables.
     */
    @FXML
    private void onBuyRepair(){
        marketService.increaseRepairAmount();
        updateUI();
    }

    /**
     * Calls market service to sell repair amount.
     * Updates gui variables.
     */
    @FXML
    private void onSellRepair(){
        marketService.decreaseRepairAmount();
        updateUI();
    }

    /**
     * Calls market service to buy upgrade amount.
     * Updates gui variables.
     */
    @FXML
    private void onBuyUpgrade(){
        marketService.increaseUpgradeAmount();
        updateUI();
    }

    /**
     * Calls market service to sell upgrade amount.
     * Updates gui variables.
     */
    @FXML
    private void onSellUpgrade(){
        marketService.decreaseUpgradeAmount();
        updateUI();
    }

    /**
     * Calls market service to buy change type amount.
     * Updates gui variables.
     */
    @FXML
    private void onBuyChangeType(){
        marketService.increaseChangeTypeAmount();
        updateUI();
    }

    /**
     * Calls market service to sell change type amount.
     * Updates gui variables.
     */
    @FXML
    private void onSellChangeType(){
        marketService.decreaseChangeTypeAmount();
        updateUI();
    }

    /**
     * Updates each list of labels to the corresponding variable in Tower. For the corresponding Tower in tower list.
     */
    private void updateUI(){
        for (int i = 0; i < lblTowerTypes.size(); i++) {
            lblTowerTypes.get(i).setText(itemManager.getTowerList().get(i).getItemName());
        }
        for (int i = 0; i < lblBuyPrices.size(); i++) {
            lblBuyPrices.get(i).setText("$" + itemManager.getTowerList().get(i).getItemCost());
        }
        for (int i = 0; i < lblSellPrices.size(); i++) {
            lblSellPrices.get(i).setText("$" + (int) (itemManager.getTowerList().get(i).getItemCost() * 0.8));
        }
        for (int i = 0; i < lblTowersOwned.size(); i++) {
            int numberOfTower = 1;
            if(itemManager.getReserveTowers()[i] != null)
            {
                btnBuyTowers.get(i).setDisable(true);
                btnSellTowers.get(i).setDisable(false);
                numberOfTower++;
            }
            else {
                btnBuyTowers.get(i).setDisable(false);
                btnSellTowers.get(i).setDisable(true);
            }
            lblTowersOwned.get(i).setText("" + (numberOfTower));

        }
        lblMoney.setText("$" + gameManager.getPlayer().getMoney());
        lblBuyPriceUpgrade.setText("$" + itemManager.getUpgrades().get(Type.SPEED).getItemCost());
        lblBuyPriceRepair.setText("$" + itemManager.getUpgrades().get(Type.REPAIR).getItemCost());
        lblBuyPriceChangeType.setText("$" + itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost());
        lblSellPriceUpgrade.setText("$" + (int) (itemManager.getUpgrades().get(Type.SPEED).getItemCost() * 0.8));
        lblSellPriceRepair.setText("$" + (int) (itemManager.getUpgrades().get(Type.REPAIR).getItemCost() *0.8));
        lblSellPriceChangeType.setText("$" + (int) (itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost() *0.8));
        lblUpgradeOwned.setText("" + itemManager.getUpgrades().get(Type.SPEED).getAmountOfType());
        if(itemManager.getUpgrades().get(Type.SPEED).getAmountOfType() > 0)
        {
            btnSellUpgrade.setDisable(false);
        }
        else
        {
            btnSellUpgrade.setDisable(true);
        }
        lblRepairOwned.setText("" + itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType());
        if(itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType() > 0)
        {
            btnSellRepair.setDisable(false);
        }
        else
        {
            btnSellRepair.setDisable(true);
        }
        lblChangeTypeOwned.setText("" + itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType());
        if(itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType() > 0)
        {
            btnSellChangeType.setDisable(false);
        }
        else
        {
            btnSellChangeType.setDisable(true);
        }

    }

    /**
     * Closes current screen.
     * Launch the game screen.
     */
    @FXML
    private void onReturnPressed() {
        gameManager.launchGameScreen();
    }

    /**
     * Closes current screen.
     * Launch the shop screen.
     */
    @FXML
    private void onItemsPressed() {
        gameManager.launchPlayerScreen();
    }
}

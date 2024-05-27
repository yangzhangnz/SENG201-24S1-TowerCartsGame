package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.enums.Material;
import seng201.team0.enums.Type;
import seng201.team0.factors.Tower;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;
import seng201.team0.services.MarketService;

import java.util.List;
import java.util.Random;

/**
 * Controller for the shop screen.
 * @author tga60 & yzh365.
 */

public class PlayerScreenController {

    @FXML
    private Label lblTower1, lblTower2, lblTower3, lblTower4, lblTower5;
    List<Label> lblCurrentTypes;
    @FXML
    private Label lblTower1Attribute, lblTower2Attribute, lblTower3Attribute, lblTower4Attribute, lblTower5Attribute;
    List<Label> lblTowerAttributes;
    @FXML
    private Label lblReserveTower1, lblReserveTower2, lblReserveTower3, lblReserveTower4, lblReserveTower5;
    List<Label> lblReserveTypes;
    @FXML
    private Label lblReserveTower1Attribute, lblReserveTower2Attribute, lblReserveTower3Attribute, lblReserveTower4Attribute, lblReserveTower5Attribute;
    List<Label> lblReserveTowerAttributes;

    @FXML
    private Label lblUnlock1, lblUnlock2, lblUnlock3, lblUnlock4, lblUnlock5;
    List<Label> lblUnlocks;

    @FXML
    private Button btnUpgradeTower1, btnUpgradeTower2, btnUpgradeTower3, btnUpgradeTower4, btnUpgradeTower5;
    List<Button> btnUpgradeTowers;
    @FXML
    private Button btnRepairTower1, btnRepairTower2, btnRepairTower3, btnRepairTower4, btnRepairTower5;
    List<Button> btnRepairTowers;
    @FXML
    private Button btnChangeTower1, btnChangeTower2, btnChangeTower3, btnChangeTower4, btnChangeTower5;
    List<Button> btnChangeTowers;


    @FXML
    private Label lblProductionAmount, lblRepairAmount, lblChangeTypeAmount;

    private MarketService marketService;
    private GameManager gameManager;
    private ItemManager itemManager;


    /**
     * Constructs a PlayerScreenController.
     * @param marketService The for managing transactions between shop and player.
     */
    public PlayerScreenController(MarketService marketService) {
        this.marketService = marketService;
        this.gameManager = marketService.getGameManager();
        this.itemManager = gameManager.getItemManager();
    }

    /**
     * Set the up list with their corresponding buttons and labels.
     * Sets variables.
     */
    public void initialize() {
        lblCurrentTypes = List.of(lblTower1, lblTower2, lblTower3, lblTower4, lblTower5);
        lblTowerAttributes = List.of(lblTower1Attribute, lblTower2Attribute, lblTower3Attribute, lblTower4Attribute, lblTower5Attribute);
        lblReserveTypes = List.of(lblReserveTower1, lblReserveTower2, lblReserveTower3, lblReserveTower4, lblReserveTower5);
        lblReserveTowerAttributes = List.of(lblReserveTower1Attribute, lblReserveTower2Attribute, lblReserveTower3Attribute, lblReserveTower4Attribute, lblReserveTower5Attribute);
        lblUnlocks = List.of(lblUnlock1, lblUnlock2, lblUnlock3, lblUnlock4, lblUnlock5);
        btnUpgradeTowers = List.of(btnUpgradeTower1, btnUpgradeTower2, btnUpgradeTower3, btnUpgradeTower4, btnUpgradeTower5);
        btnRepairTowers = List.of(btnRepairTower1, btnRepairTower2, btnRepairTower3, btnRepairTower4, btnRepairTower5);
        btnChangeTowers = List.of(btnChangeTower1, btnChangeTower2, btnChangeTower3, btnChangeTower4, btnChangeTower5);
        updateStats();
        upgradeTowerButtons();
        repairTowerButtons();
        changeTowerButtons();
    }

    /**
     * Gets the index of the button pressed and passes it to upgrade tower.
     */
    private void upgradeTowerButtons(){
        for (int i = 0; i < btnUpgradeTowers.size(); i++) {
            final int index = i;
            btnUpgradeTowers.get(i).setOnAction(event -> {
                upgradeTower(index);
                updateStats();
            });
        }
    }

    /**
     * Gets the index of the button pressed and passes it to repair tower.
     */
    private void repairTowerButtons(){
        for (int i = 0; i < btnRepairTowers.size(); i++) {
            final int index = i;
            btnRepairTowers.get(i).setOnAction(event -> {
                repairTower(index);
                updateStats();
            });
        }
    }

    /**
     * Gets the index of the button pressed and passes it to change tower.
     */
    private void changeTowerButtons(){
        for (int i = 0; i < btnChangeTowers.size(); i++) {
            final int index = i;
            btnChangeTowers.get(i).setOnAction(event -> {
                changeTowerType(index);
                updateStats();
            });
        }
    }

    /**
     * Updates each list of labels to the corresponding variable in Tower. For the corresponding Tower in tower list.
     */
    private void updateStats() {
        int productionAmount = itemManager.getUpgrades().get(Type.SPEED).getAmountOfType();
        int repairAmount = itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType();
        int changeTypeAmount = itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType();
        lblProductionAmount.setText(productionAmount + "\nProduction\nItems");
        lblRepairAmount.setText(repairAmount + "\nRepair\nItems");
        lblChangeTypeAmount.setText(changeTypeAmount + "\nChange Type\nItems");
        List<Tower> towers = itemManager.getTowerList();
        for (int i = 0; i < towers.size(); i++) {
            boolean tempReserveIsBroken = false;
            Tower tower = towers.get(i);
            lblCurrentTypes.get(i).setText(tower.getItemName());
            lblReserveTypes.get(i).setText(tower.getItemName());
            lblTowerAttributes.get(i).setText("Level:" + tower.getLevel() +
                    "\nProduction: " + tower.getProductionRate() +
                    "\nPrice: " + tower.getMoneyMadePerMaterial() +
                    "\nBroken: " + tower.getIsBroken());
            if (i < itemManager.getReserveTowers().length) {
                Tower reserve = itemManager.getReserveTowers()[i];
                if (reserve != null) {
                    tempReserveIsBroken = reserve.getIsBroken();
                    lblReserveTowerAttributes.get(i).setText("Level:" + reserve.getLevel() +
                            "\nProduction: " + reserve.getProductionRate() +
                            "\nPrice: " + reserve.getMoneyMadePerMaterial() +
                            "\nBroken: " + reserve.getIsBroken());
                }
                else
                {
                    lblReserveTowerAttributes.get(i).setText("No Reserve");
                }
            }

            btnRepairTowers.get(i).setDisable(!(repairAmount > 0 && (towers.get(i).getIsBroken() || tempReserveIsBroken)));
            btnChangeTowers.get(i).setDisable(changeTypeAmount <= 0);
            if(tower.getLevel() <= tower.getUpgradeLevel()) {
                btnUpgradeTowers.get(i).setDisable(true);
                lblUnlocks.get(i).setText("Unlock At Lvl:" + (tower.getLevel() + 1));
            }
            else
            {
                lblUnlocks.get(i).setText("");
                btnUpgradeTowers.get(i).setDisable(productionAmount <= 0);
            }
        }
    }

    /**
     * Calls increaseProductionRate to increase the production rate of the tower with the giving index
     * @param index the position in the list of the tower and it's reserve.
     */
    private void upgradeTower(int index) {
        Tower tower = itemManager.getTowerList().get(index);
        Tower reserve = itemManager.getReserveTowers()[index];

        if (tower != null && itemManager.getUpgrades().get(Type.SPEED).getAmountOfType() > 0) {
            tower.increaseProductionRate(2);
            if (reserve != null) {
                reserve.increaseProductionRate(2);
            }
            itemManager.getUpgrades().get(Type.SPEED).decreaseAmount();
        }

    }

    /**
     * Calls setIsBroken to fix the tower and reserve with the given index.
     * @param index the position in the list of the tower and it's reserve.
     */
    private void repairTower(int index) {
        Tower tower = itemManager.getTowerList().get(index);
        Tower reserve = itemManager.getReserveTowers()[index];
        if (tower != null && itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType() > 0) {
            tower.setIsBroken(false);
            if(reserve != null)
            {
                reserve.setIsBroken(false);
            }

            itemManager.getUpgrades().get(Type.REPAIR).decreaseAmount();
        }
    }

    /**
     * Calls setMaterial to change the tower and reserve material to a new random one.
     * @param index the position in the list of the tower and it's reserve.
     */
    private void changeTowerType(int index) {
        Tower tower = itemManager.getTowerList().get(index);
        if (tower != null && itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType() > 0) {
            Material newType = Material.values()[new Random().nextInt(Material.values().length - 1)];
            tower.setMaterial(newType);  // Assuming a method to change material
            itemManager.getUpgrades().get(Type.CHANGETYPE).decreaseAmount();
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
     * Launch the player screen.
     */
    @FXML
    private void onShopPressed() {
        gameManager.launchShopScreen();
    }
}
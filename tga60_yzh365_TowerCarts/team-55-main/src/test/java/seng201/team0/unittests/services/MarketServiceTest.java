package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.enums.Type;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;
import seng201.team0.services.MarketService;

import static org.junit.jupiter.api.Assertions.*;

public class MarketServiceTest {
    private MarketService marketService;
    private GameManager gameManager;
    private ItemManager itemManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(
                gm -> {},  // setupScreenLauncher
                gm -> {},  // gameScreenLauncher
                () -> {},  // clearScreen
                gm -> {},  // shopScreenLaunch
                gm -> {},  // roundCompleteLauncher
                gm -> {},  // roundFailedLauncher
                gm -> {},  // playerScreenLaunch
                gm -> {},  // failScreenLauncher
                gm -> {}   // winScreenLauncher
        );
        marketService = new MarketService(gameManager);
        itemManager = gameManager.getItemManager();
    }

    @Test
    void testBuyTower() {
        if (!gameManager.getItemManager().getTowerList().isEmpty()) {
            gameManager.getPlayer().addMoney(2000);
            int initialMoney = gameManager.getPlayer().getMoney();
            int towerCost = gameManager.getItemManager().getTowerList().get(0).getItemCost();

            marketService.buyTower(0);
            assertEquals(initialMoney - towerCost, gameManager.getPlayer().getMoney());
            assertNotNull(gameManager.getItemManager().getReserveTowers()[0]);
        }
    }

    @Test
    void testSellTower() {
        marketService.buyTower(0);
        int initialMoney = gameManager.getPlayer().getMoney();
        int towerCost = itemManager.getTowerList().get(0).getItemCost();
        marketService.sellTower(0);
        assertEquals(initialMoney + (int) (towerCost * 0.8), gameManager.getPlayer().getMoney());
        assertNull(itemManager.getReserveTowers()[0]);
    }

    @Test
    void testIncreaseChangeTypeAmount() {
        int initialMoney = gameManager.getPlayer().getMoney();
        int changeTypeCost = itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost();
        marketService.increaseChangeTypeAmount();
        assertEquals(initialMoney - changeTypeCost, gameManager.getPlayer().getMoney());
        assertEquals(1, itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType());
    }

    @Test
    void testIncreaseUpgradeAmount() {
        int initialMoney = gameManager.getPlayer().getMoney();
        int speedCost = itemManager.getUpgrades().get(Type.SPEED).getItemCost();
        marketService.increaseUpgradeAmount();
        assertEquals(initialMoney - speedCost, gameManager.getPlayer().getMoney());
        assertEquals(1, itemManager.getUpgrades().get(Type.SPEED).getAmountOfType());
    }

    @Test
    void testDecreaseRepairAmount() {
        marketService.increaseRepairAmount();
        int initialMoney = gameManager.getPlayer().getMoney();
        int repairCost = itemManager.getUpgrades().get(Type.REPAIR).getItemCost();
        marketService.decreaseRepairAmount();
        assertEquals(initialMoney + (int) (repairCost * 0.8), gameManager.getPlayer().getMoney());
        assertEquals(0, itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType());
    }

    @Test
    void testIncreaseRepairAmount() {
        int initialMoney = gameManager.getPlayer().getMoney();
        int repairCost = itemManager.getUpgrades().get(Type.REPAIR).getItemCost();
        marketService.increaseRepairAmount();
        assertEquals(initialMoney - repairCost, gameManager.getPlayer().getMoney());
        assertEquals(1, itemManager.getUpgrades().get(Type.REPAIR).getAmountOfType());
    }

    @Test
    void testDecreaseChangeTypeAmount() {
        marketService.increaseChangeTypeAmount();  // Ensure there is an item to decrease
        int initialMoney = gameManager.getPlayer().getMoney();
        int changeTypeCost = itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost();
        marketService.decreaseChangeTypeAmount();
        assertEquals(initialMoney + (int) (changeTypeCost * 0.8), gameManager.getPlayer().getMoney());
        assertEquals(0, itemManager.getUpgrades().get(Type.CHANGETYPE).getAmountOfType());
    }

    @Test
    void testDecreaseUpgradeAmount() {
        marketService.increaseUpgradeAmount();  // Ensure there is an item to decrease
        int initialMoney = gameManager.getPlayer().getMoney();
        int speedCost = itemManager.getUpgrades().get(Type.SPEED).getItemCost();
        marketService.decreaseUpgradeAmount();
        assertEquals(initialMoney + (int) (speedCost * 0.8), gameManager.getPlayer().getMoney());
        assertEquals(0, itemManager.getUpgrades().get(Type.SPEED).getAmountOfType());
    }

    @Test
    void testGetGameManager() {
        assertEquals(gameManager, marketService.getGameManager());
    }
}

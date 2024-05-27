package seng201.team0.services;

import seng201.team0.enums.Material;
import seng201.team0.enums.Type;
import seng201.team0.factors.Tower;
import seng201.team0.factors.Upgrade;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Market services, keeps track of the shop items, player items and purchases.
 * @author tga60.
 */

public class MarketService {
    private final ItemManager itemManager;
    private final GameManager gameManager;

    /**
     * Constructor, sets the game manager.
     * @param gameManager Manages class interaction between each other.
     */
    public MarketService(GameManager gameManager) {
        this.gameManager = gameManager;
        this.itemManager = gameManager.getItemManager();
    }

    /**
     * Checks to see if player has enough money for purchase.
     * Creates a new reserve tower for the tower with the give index, and removes money from player.
     * @param index the index of the tower that is getting a reserve tower.
     */
    public void buyTower(int index)
    {
        if(gameManager.getPlayer().getMoney() >= itemManager.getTowerList().get(index).getItemCost()) {
            Tower[] tempTowerReserves = itemManager.getReserveTowers();
            tempTowerReserves[index] = new Tower(itemManager.getTowerList().get(index).getMaterial());
            itemManager.setReserveTowers(tempTowerReserves);
            gameManager.getPlayer().addMoney(-itemManager.getTowerList().get(index).getItemCost());
        }
    }

    /**
     * Checks to see if player has a reserve tower to sell.
     * Removes reserve tower and give the player money.
     * @param index the index of the reserve tower.
     */
    public void sellTower(int index)
    {
        Tower[] tempTowerReserves = itemManager.getReserveTowers();
        tempTowerReserves[index] = null;
        itemManager.setReserveTowers(tempTowerReserves);
        gameManager.getPlayer().addMoney((int) (itemManager.getTowerList().get(index).getItemCost() *0.8));
    }

    /**
     * Checks to see if player has enough money for purchase.
     * Add another repair item to player, and removes money from player.
     */
    public void increaseRepairAmount()
    {
        if(gameManager.getPlayer().getMoney() >= itemManager.getUpgrades().get(Type.REPAIR).getItemCost()) {
            itemManager.getUpgrades().get(Type.REPAIR).increaseAmount();
            gameManager.getPlayer().addMoney(-itemManager.getUpgrades().get(Type.REPAIR).getItemCost());
        }
    }

    /**
     * Checks to see if player has enough money for purchase.
     * Add another change type item to player, and removes money from player.
     */
    public void increaseChangeTypeAmount()
    {
        if(gameManager.getPlayer().getMoney() >= itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost()) {
            itemManager.getUpgrades().get(Type.CHANGETYPE).increaseAmount();
            gameManager.getPlayer().addMoney(-itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost());
        }
    }

    /**
     * Checks to see if player has enough money for purchase.
     * Add another upgrade item to player, and removes money from player.
     */
    public void increaseUpgradeAmount()
    {
        if(gameManager.getPlayer().getMoney() >= itemManager.getUpgrades().get(Type.SPEED).getItemCost()) {
            itemManager.getUpgrades().get(Type.SPEED).increaseAmount();
            gameManager.getPlayer().addMoney(-itemManager.getUpgrades().get(Type.SPEED).getItemCost());
        }
    }

    /**
     * Checks to see if player has a repair item.
     * Removes a repair item from player, adds money to player.
     */
    public void decreaseRepairAmount()
    {
        itemManager.getUpgrades().get(Type.REPAIR).decreaseAmount();
        gameManager.getPlayer().addMoney((int) (itemManager.getUpgrades().get(Type.REPAIR).getItemCost() * 0.8));

    }

    /**
     * Checks to see if player has a change Type item.
     * Removes a change type item from player, adds money to player.
     */
    public void decreaseChangeTypeAmount()
    {
        itemManager.getUpgrades().get(Type.CHANGETYPE).decreaseAmount();
        gameManager.getPlayer().addMoney((int) (itemManager.getUpgrades().get(Type.CHANGETYPE).getItemCost() * 0.8));

    }
    public void decreaseUpgradeAmount()
    {
        itemManager.getUpgrades().get(Type.SPEED).decreaseAmount();
        gameManager.getPlayer().addMoney((int) (itemManager.getUpgrades().get(Type.SPEED).getItemCost() * 0.8));
    }


    public GameManager getGameManager()
    {
        return this.gameManager;
    }

}

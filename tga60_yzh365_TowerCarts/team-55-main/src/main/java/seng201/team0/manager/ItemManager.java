package seng201.team0.manager;

import seng201.team0.enums.Material;
import seng201.team0.enums.Type;
import seng201.team0.factors.Tower;
import seng201.team0.factors.Upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manages the tower and upgrade items.
 * @author tga60 & yzh365.
 */
public class ItemManager {
    private final Map<Material, Tower> towers;
    private final Map<Type, Upgrade> upgrades;


    private List<Tower> playerTowers = new ArrayList<>();   // Main tower list of player, player can choose 3 out of 5 from this list
    private Tower[] reserveTowers = new Tower[5];  // Reserve tower list of player, same as main tower list

    private List<Tower> towerOptions = new ArrayList<>();  // Default tower list to offer up 5 types of towers as default towers

    /**
     * Constructs an ItemManager and initializes tower lists and player's balance.
     */
    public ItemManager() {
        this.towers = new HashMap<>();
        this.upgrades = new HashMap<>();
        this.playerTowers.addAll(List.of(new Tower(Material.DIRT),new Tower(Material.WOOD)));   //Sets default Towers

        initializeTowers();
        initializeUpgrades();
        setupDefaultTowers();
    }

    /**
     * Initializes the towers with predefined settings
     */
    private void initializeTowers() {
        for (Material mat : Material.values()) {
            Tower newTower =  new Tower(mat);
            towers.put(mat, newTower);
        }
    }

    /**
     * Initializes upgrades for various types.
     */
    private void initializeUpgrades() {
        upgrades.put(Type.SPEED, new Upgrade("Speed", 400, Material.ANY));
        upgrades.put(Type.REPAIR, new Upgrade("Repair", 200, Material.ANY));
        upgrades.put(Type.CHANGETYPE, new Upgrade("Change Type", 400, Material.ANY));
    }

    /**
     * Gets the list of player's towers.
     *
     * @return the list of towers owned by the player.
     */
    public List<Tower> getTowerList() {
        return playerTowers;
    }

    /**
     * Sets the list of towers for the player.
     *
     * @param towerList The list of towers to be set for the player.
     */
    public void setTowerList(List<Tower> towerList) {
        this.playerTowers.addAll(towerList);
    }

    /**
     * Gets the list of player's reserve towers.
     *
     * @return the list of reserve towers owned by the player.
     */
    public Tower[] getReserveTowers() {
        return reserveTowers;
    }

    /**
     * Sets the list of reserve towers for the player.
     *
     * @param reserveTowers The list of reserve towers to be set for the player.
     */
    public void setReserveTowers(Tower[] reserveTowers) {
        this.reserveTowers = reserveTowers;
    }

    /**
     * Gets the list of default towers.
     *
     * @return the list of default towers.
     */
    public List<Tower> getTowersOptions() {
        return towerOptions;
    }

    /**
     * Adds the selectable towers to tower options.
     */
    private void setupDefaultTowers() {
        towerOptions.add(new Tower(Material.COAL));
        towerOptions.add(new Tower(Material.STONE));
        towerOptions.add(new Tower(Material.IRON));
        towerOptions.add(new Tower(Material.COPPER));
        towerOptions.add(new Tower(Material.SILVER));
        towerOptions.add(new Tower(Material.GOLD));
    }


    /**
     * Gets the map of towers grouped by material.
     *
     * @return A map containing the towers categorized by their material type.
     */
    public Map<Material, Tower> getTowers() {
        return towers;
    }

    /**
     * Gets the map of upgrades grouped by type.
     * @return A map containing the upgrades categorized by their type.
     */
    public Map<Type, Upgrade> getUpgrades() {
        return upgrades;
    }
}

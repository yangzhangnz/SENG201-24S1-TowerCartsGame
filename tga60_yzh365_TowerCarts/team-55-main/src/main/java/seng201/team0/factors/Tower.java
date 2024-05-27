package seng201.team0.factors;

import seng201.team0.enums.ItemType;
import seng201.team0.enums.Material;

/**
 * Represents a tower with specific materials and properties.
 * Inherits from Item which includes name, speed, type and cost for constructor.
 * @author tga60 & yzh365
 */
public class Tower extends Item {
    private int level = 1;              //The current level of the tower
    private int upgradeLevel = 0;       //How many upgrades the tower has
    private boolean isBroken = false;   // Flag indicating if the tower is operational or not
    private int productionRate;            // Speed at which the tower operates or reloads
    private int moneyMade;              // Total money made by the tower
    private int moneyMadePerMaterial;     // Money made per material by the tower
    private Material material;          // Material from which the tower is constructed

    /**
     * Constructor to initialize the Tower with a material; setting name, speed and cost based on material properties.
     *
     * @param material The material from which the tower is constructed.
     */
    public Tower(Material material) {
        super(material.getName(), ItemType.TOWER, material.getCost(), material);
        this.productionRate = material.getProductionRate();
        this.material = material;
        this.moneyMade = 0;
        this.moneyMadePerMaterial = material.getMaterialPrice();
    }



    /**
     * Changes the material type of the tower, affecting its properties.
     *
     * @param newMaterial The new material to apply to the tower.
     */
    public void changeType(Material newMaterial) {
        this.material = newMaterial;
        this.productionRate = newMaterial.getProductionRate();  // Update speed to match new material
        setItemCost(newMaterial.getCost());   // Update cost to match new material
        setItemName(newMaterial.getName());   // Update name to match new material
    }

    /**
     * Gets the total money made by the tower.
     *
     * @return The total money made as an integer.
     */
    public int getMoneyMade()
    {
        return moneyMade;
    }

    /**
     * Sets the total money made by the tower.
     *
     * @param moneyMade The money made to set.
     */
    public void setMoneyMade(int moneyMade)
    {
        this.moneyMade = moneyMade;
    }

    /**
     * Gets the money made per second by the tower.
     *
     * @return The money made per second as an integer.
     */
    public int getMoneyMadePerMaterial() {
        return moneyMadePerMaterial;
    }


    /**
     * Increases the total money made by the tower based on its production rate and earnings per second.
     * The amount added is calculated as a product of money made per material and production rate.
     */
    public void increaseMoneyMade(){
        moneyMade += moneyMadePerMaterial * productionRate;
    }

    /**
     * Increase the tower level by a specified amount.
     *
     * @param addAmount The amount to increase level by.
     */
    public void addLevel(int addAmount) {
        this.level += addAmount;
    }

    /**
     * Gets the level of the tower.
     *
     * @return The level as an integer.
     */
    public int getLevel(){
        return level;
    }

    /**
     * Gets the upgrade level of the tower.
     *
     * @return The upgrade level as an integer.
     */
    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    /**
     * Gets the current operational status of the tower.
     *
     * @return true if the tower is broken and needs repair.
     */
    public boolean getIsBroken(){
        return isBroken;
    }

    /**
     * Sets the operational status of the tower.
     *
     * @param isBroken True if the tower is broken.
     */
    public void setIsBroken(boolean isBroken)
    {
        this.isBroken = isBroken;
    }

    /**
     * Gets the current production rate of the tower.
     *
     * @return the production rate of the tower.
     */
    public int getProductionRate(){
        return productionRate;
    }

    /**
     * Increases the operational speed of the tower.
     *
     * @param increment The amount by which to increase the speed.
     */
    public void increaseProductionRate(int increment) {
        this.productionRate += increment;
        this.upgradeLevel += 1;
    }


    /**
     * Gets the current material of the tower.
     *
     * @return the material of the tower.
     */
    public Material getMaterial() {
        return this.material;
    }


    /**
     * Changes the values of the tower to same values of the give material.
     *
     * @param material The new material to switch to.
     */
    public void setMaterial(Material material) {
        this.productionRate = this.productionRate - this.material.getProductionRate() + material.getProductionRate() ;
        this.material = material;
        this.moneyMadePerMaterial = material.getMaterialPrice();
        setItemName(material.getName());
        setItemCost(material.getCost());
        setItemResourceType(material);
    }
}

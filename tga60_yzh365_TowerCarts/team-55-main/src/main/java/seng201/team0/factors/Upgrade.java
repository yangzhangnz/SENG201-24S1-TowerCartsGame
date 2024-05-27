package seng201.team0.factors;

import seng201.team0.enums.ItemType;
import seng201.team0.enums.Material;
import seng201.team0.enums.Type;

/**
 * Represents an upgrade item in the game, capable of improving the properties of towers.
 * Each upgrade has a type determining its effect on towers.
 * @author tga60  & yzh365
 */
public class Upgrade extends Item {
    private int amount;                 // Current amount of this upgrade available

    /**
     *  Constructs an Upgrade item with specified attributes.
     *
     * @param name The name of the upgrade.
     * @param cost The cost of the upgrade.
     * @param resourceType The material associated with this upgrade, usually Material. ANY as upgrades may not be material-specific.
     */
    public Upgrade(String name, int cost, Material resourceType) {
        super(name, ItemType.UPGRADE, cost, resourceType);
        this.amount = 0;
    }

    /**
     * Increases the amount of this upgrade available for use.
     */
    public void increaseAmount() {
        this.amount++;
    }

    /**
     * Decreases the amount of this upgrade available for use.
     */
    public void decreaseAmount() {
        this.amount--;
    }

    /**
     * Returns the current amount of this upgrade type available.
     *
     * @return The amount of this upgrade type.
     */
    public int getAmountOfType() {
        return this.amount;
    }

}

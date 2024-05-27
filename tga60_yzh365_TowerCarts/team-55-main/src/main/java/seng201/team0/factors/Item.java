package seng201.team0.factors;

import seng201.team0.enums.ItemType;
import seng201.team0.enums.Material;

/**
 * Abstract class representing a general item in the game.
 * Items can be either towers or upgrades with related materials and costs.
 * @author yzh365
 */
public abstract class Item {
    private String itemName;
    private ItemType itemType;
    private Material materialType;
    public int itemCost;

    /**
     * Constructs a new Item with specified attributes.
     *
     * @param itemName The name of the item.
     * @param itemType The type of the item (ie: Tower, Upgrade).
     * @param itemCost The cost of the item.
     * @param materialType The resourceType of the item.
     */
    public Item(String itemName, ItemType itemType, int itemCost, Material materialType) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemCost = itemCost;
        this.materialType = materialType;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName The new name for the item.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the type of the item.
     *
     * @return The type of the item.
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Sets the type of the item.
     *
     * @param itemType The new type for the item.
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * Gets the cost of the item.
     *
     * @return The cost of the item.
     */
    public int getItemCost() {
        return itemCost;
    }

    /**
     * Sets the cost of the item.
     *
     * @param itemCost The new cost for the item.
     */
    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    /**
     * Gets the material type associated with the item.
     *
     * @return The material type of the item.
     */
    public Material getItemResourceType() {
        return materialType;
    }

    /**
     * Sets the material type associated with the item.
     *
     * @param materialType The new material type for the item.
     */
    public void setItemResourceType(Material materialType) {
        this.materialType = materialType;
    }

}

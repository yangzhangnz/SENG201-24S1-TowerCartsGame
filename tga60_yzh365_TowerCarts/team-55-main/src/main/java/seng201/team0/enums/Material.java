package seng201.team0.enums;

/**
 * Enum defining some materials that can be used in the game, affecting the cost and speed of towers.
 * Every material has associated attributes such as speed, cost and default name.
 * @author tga60 & yzh365
 */
public enum Material {
    DIRT(20, 1500,1, "Dirt"),
    WOOD(19, 1700, 2, "Wood"),
    COAL(18, 1900,3, "Coal"),
    STONE(17, 2100, 4, "Stone"),
    IRON(16, 2300, 5, "Iron"),
    COPPER(15, 2500,6, "Copper"),
    SILVER(14, 2700, 7, "Silver"),
    GOLD(13, 2900, 8, "Gold"),
    ANY(0, 0, 0, "Any");   // Adding a generic 'ANY' type with default properties

    private final int productionRate;
    private final int cost;
    private final int materialPrice;
    private final String name;

    /**
     * Constructor for Material enum to set the properties of each material type.
     *
     * @param productionRate The productionRate of the tower based on the material.
     * @param cost The cost of the tower based on the material.
     * @param name The default name of the tower based on the material.
     * @param materialPrice The cost earned per product made.
     */
    Material(int productionRate, int cost, int materialPrice, String name) {
        this.productionRate = productionRate;
        this.materialPrice = materialPrice;
        this.cost = cost;
        this.name = name;
    }

    /**
     * Gets the productionRate of tower from associated material.
     *
     * @return the productionRate of towers from specific material.
     */
    public int getProductionRate() {
        return productionRate;
    }

    /**
     * Gets cost of tower from associated material.
     *
     * @return the cost of tower from specific material.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets materialPrice from associated material.
     *
     * @return materialPrice the cost earned per product made.
     */
    public int getMaterialPrice() {
        return materialPrice;
    }

    /**
     * Gets the default name for towers from associated material.
     *
     * @return the default tower name.
     */
    public String getName() {
        return name;
    }
}

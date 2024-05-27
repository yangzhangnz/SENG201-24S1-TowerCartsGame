package seng201.team0.factors;

import seng201.team0.enums.Material;

/**
 * Represents a resource cart that can be used in the game to fill with resources during a round.
 * The cart has properties such as size, max size, speed, current distance, and max distance.
 * @author tga60 & yz365.
 */
public class Cart {
    private int maxSize;
    private int currentSize;
    private Material materialType;
    private float currentDistance;
    private float maxDistance;

    /**
     * Constructs a Cart with a specified resource type.
     * Initializes the cart with default values for size, speed and distance.
     *
     * @param materialType The type of resource the cart can hold.
     */
    public Cart(Material materialType) {
        this.maxSize = 100;                 // Amount of the resources the cart can hold
        this.currentSize = 0;               // Amount of the resources the cart is currently holding
        this.materialType = materialType;   // Type of resource that can fill the cart
        this.currentDistance = 0;           // Current distance traveled by the cart
        this.maxDistance = 100;             // Maximum distance the cart can travel
    }

    /**
     * Fills the cart with a specified amount of resources.
     * Ensures the cart's size does not exceed its maximum capacity.
     *
     * @param amount The amount of resources added to the cart.
     */
    public void fill(int amount) {
        this.maxSize -= amount;
        if (this.maxSize < 0) {
            this.maxSize = 0; // Reset size to 0 when it is filled
        }
    }

    /**
     * Gets the current distance traveled by the cart.
     *
     * @return The current distance as a float.
     */
    public float getCurrentDistance() {
        return currentDistance;
    }

    /**
     * Sets the current distance traveled by the cart.
     *
     * @param currentDistance The distance the cart has traveled.
     */
    public void setCurrentDistance(float currentDistance) {
        this.currentDistance = currentDistance;
    }

    /**
     * Gets the maximum distance the cart can travel.
     *
     * @return The maximum distance as a float.
     */
    public float getMaxDistance() {
        return maxDistance;
    }


    /**
     * Gets the maximum size or capacity of the cart.
     *
     * @return The maximum size as an integer.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the maximum size or capacity of the cart.
     *
     * @param maxSize The maximum size to set.
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets the current size or the amount of resources currently in the cart.
     *
     * @return The current size as an integer.
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * Sets the current size or the amount of resources currently in the cart.
     *
     * @param currentSize The current size to set.
     */
    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * Gets the type of resource the cart is designed to carry.
     *
     * @return The resource type as a Material enum.
     */
    public Material getMaterialType() {
        return materialType;
    }

    /**
     * Sets the type of resource the cart is designed to carry.
     *
     * @param materialType The resource type to set.
     */
    public void setMaterialType(Material materialType) {
        this.materialType = materialType;
    }


}

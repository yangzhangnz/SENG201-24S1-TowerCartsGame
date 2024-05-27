package seng201.team0.factors;

import seng201.team0.enums.Difficulty;
import seng201.team0.enums.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, such as name, towers (main and reserve), lives, upgrades and money.
 * @author tga60 & yzh365
 */
public class Player {
    public String name;
    private int lives = 5;
    private int money;
    private Difficulty difficulty;

    /**
     * Constructs a Player with an empty list of towers.
     */
    public Player() {
        difficulty = Difficulty.NORMAL;
        setInitialMoney();
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the player and check to see if it meets the requirements before setting the player name
     * @param name The String entered by the player.
     * @return Whether the name met the requirements or not. 
     */
    public boolean setName(String name) {
        if (name != null && name.length() >= 3 && name.length() <= 15 && name.matches("^[a-zA-Z0-9 ]*$")) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets the game difficulty based on the slider input.
     *
     * @return The value of difficulty.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level based on the slider position.
     *
     * @param sliderPosition The position of the slider decide the difficulty level.
     */
    public void chooseDifficulty(int sliderPosition) {
        if (sliderPosition == 0) {
            this.difficulty = Difficulty.EASIEST;
        } else if (sliderPosition == 5) {
            this.difficulty = Difficulty.EASY;
        } else if (sliderPosition == 10) {
            this.difficulty = Difficulty.NORMAL;
        } else if (sliderPosition == 15) {
            this.difficulty = Difficulty.HARD;
        } else if (sliderPosition >= 20) {
            this.difficulty = Difficulty.HARDEST;
        } else {
            this.difficulty = Difficulty.NORMAL;
        }
        setInitialMoney(); // Recalculate money whenever difficulty changes
    }

    /**
     * Gets the money from player.
     *
     * @return The money of player.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Updates the player's money based on the current difficulty settings.
     */
    private void setInitialMoney() {
        switch (difficulty) {
            case EASIEST:
                money = 1500;
                break;
            case EASY:
                money = 1200;
                break;
            case HARD:
                money = 500;
                break;
            case HARDEST:
                money = 0; // Less money for higher difficulty
                break;
            default:
                money = 800;
                break;
        }
    }

    /**
     * Adds a specified amount of money to the player's total.
     *
     * @param money The amount of money to add.
     */
    public void addMoney(int money)
    {
        this.money += money;
    }


    /**
     * Removes one live from the player.
     */
    public void loseLives() {
        lives--;
    }

    /**
     * Gets the lives from player
     *
     * @return the lives of player
     */
    public int getLives() {
        return lives;
    }



}

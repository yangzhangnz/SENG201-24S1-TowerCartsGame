package seng201.team0.factors;

import javafx.application.Platform;
import seng201.team0.interfaces.TimerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages the rounds in the game, such as timing for each round and managing round progression.
 * @author tga60
 */
public class Rounds {
    private int currentRound;
    private int maxRound;

    /**
     * Constructs a Rounds instance initializing it to the first round.
     */
    public Rounds()
    {
        this.currentRound = 1;
        this.maxRound = 5;
    }

    /**
     * Gets the number of the current round.
     *
     * @return The current round number as an integer.
     */
    public float getCurrentRound()
    {
        return(this.currentRound);
    }

    /**
     * Sets the number of the current round.
     *
     * @param currentRound The round number to set.
     */
    public void setCurrentRound(int currentRound)
    {
        this.currentRound = currentRound;
    }

    /**
     * Gets the maximum number of rounds in the game.
     *
     * @return The maximum number of rounds as an integer.
     */
    public float getMaxRound()
    {
        return(this.maxRound);
    }

    /**
     * Sets the maximum number of rounds in the game.
     *
     * @param maxRound The maximum number of rounds to set.
     */
    public void setMaxRound(int maxRound)
    {
        this.maxRound = maxRound;
    }

}

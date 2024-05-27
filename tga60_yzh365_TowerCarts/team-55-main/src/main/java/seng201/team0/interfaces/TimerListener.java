package seng201.team0.interfaces;

/**
 * Interface for classes that need updates depending on time changes in the game.
 * @author tga60
 */
public interface TimerListener {
    /**
     * Called when the current time is updated.
     * @param currentTime The number of seconds into the round.
     */
    void onTimeUpdated(int currentTime);
}

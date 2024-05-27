package seng201.team0.services;

import javafx.application.Platform;
import seng201.team0.interfaces.TimerListener;
import seng201.team0.manager.GameManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Counter services, keeps track of the current time of the round in seconds.
 * @author tga60
 */
public class CounterService {

    private ScheduledExecutorService timeExecutor;  // Executor for managing timing
    private TimerListener timerListener;        // Listener for time updates
    private int currentTime;                    // Current time within the
    private boolean isRunning;
    private GameManager gameManager;

    /**
     * Constructor, sets the game manager.
     * @param gameManager Manages class interaction between each other.
     */
    public CounterService(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Sets the timer listener.
     *
     * @param timerListener the gui game screen controller which needs updating every second.
     */
    public void setTimerListener(TimerListener timerListener)
    {
        this.timerListener = timerListener;
    }

    /**
     * Increases current time by 1 every second.
     * Updates the game manager and game screen controller on the JavaFX tread.
     * Stops timer one it reaches 10 seconds.
     */
    public void startTimer()
    {
        isRunning = true;
        timeExecutor = Executors.newSingleThreadScheduledExecutor();
        timeExecutor.scheduleAtFixedRate(() -> {
            if (this.currentTime <= 10) {
                this.currentTime += 1;
                Platform.runLater(() -> gameManager.onTimeUpdated(currentTime));
                Platform.runLater(() -> timerListener.onTimeUpdated(currentTime));

            } else {
                stopTimer();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }


    /**
     * Stops the timer.
     * Shuts down the time executor.
     * Resets current time to 0;
     */
    public void stopTimer() {
        if (timeExecutor != null && !timeExecutor.isShutdown()) {
            timeExecutor.shutdownNow();
        }
        currentTime = 0;
        isRunning = false;
    }

    /**
     * Stops the time executor, pausing the time.
     */
    public void pauseTimer() {
        if (timeExecutor != null && !timeExecutor.isShutdown()) {
            timeExecutor.shutdownNow();
        }
        isRunning = false;
    }

    /**
     * Gets is running.
     *
     * @return Is running, a boolean to show if the timer is running.
     */
    public boolean getIsRunning(){
        return  isRunning;
    }

    /**
     * Gets the current time elapsed in the current round.
     *
     * @return The current time as an integer.
     */
    public int getCurrentTime()
    {
        return(this.currentTime);
    }
}

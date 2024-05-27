package seng201.team0.unittests.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import seng201.team0.factors.Player;
import seng201.team0.factors.Rounds;
import seng201.team0.manager.GameManager;
import seng201.team0.services.CounterService;


public class GameManagerTest {
    private GameManager gameManagerStub;
    PlayerStub playerStub;
    RoundsStub roundsStub;
    CounterServiceStub counterServiceStub;

    @BeforeEach
    void setUp() {
        playerStub = new PlayerStub();
        roundsStub = new RoundsStub();
        gameManagerStub = new GameManager(this::launchSetupScreen, this::launchGameScreen, this::clearScreen, this::launchShopScreen,
                        this::launchRoundCompleteScreen, this::launchRoundFailedScreen, this::launchPlayerScreen,
                        this::launchFailScreen, this:: launchWinScreen);

       // gameManager.setPlayer(playerStub);
        gameManagerStub.setRounds(roundsStub);
       // gameManager.setCounterService(counterServiceStub);
    }

    private void launchSetupScreen(GameManager gm) {
        System.out.println("Set up screen launched");
    }

    @Test
    void testLaunchSetupScreen() {
        gameManagerStub.launchSetupScreen();
    }

    private void launchRoundCompleteScreen(GameManager gm) {
        System.out.println("Round complete screen launched");
    }

    @Test
    void testLaunchRoundCompleteScreen() {
        roundsStub.setCurrentRound(1);
        roundsStub.setMaxRound(5);
        gameManagerStub.launchRoundCompleteScreen();
    }

    private void launchFailScreen(GameManager gm) {
        System.out.println("Fail screen launched");
    }

    @Test
    void testLaunchFailScreen() {
        gameManagerStub.launchFailScreen();
    }

    private void launchRoundFailedScreen(GameManager gm) {
        System.out.println("Round failed screen launched");
    }

    @Test
    void testLaunchRoundFailedScreen() {
        playerStub.setLives(5);
        playerStub.loseLives();
        gameManagerStub.launchRoundFailedScreen();
        assertEquals(4, playerStub.getLives()); // Lives starts as 5
    }

    private void launchWinScreen(GameManager gm) {
        System.out.println("Win screen launched");
    }

    @Test
    void testLaunchWinScreen() {
        gameManagerStub.launchWinScreen();
    }

    private void launchGameScreen(GameManager gm) {
        System.out.println("Game screen launched");
    }

    @Test
    void testLaunchGameScreen() {
        gameManagerStub.launchGameScreen();
    }

    private void launchShopScreen(GameManager gm) {
        System.out.println("Shop screen launched");
    }

    @Test
    void testLaunchShopScreen() {
        gameManagerStub.launchShopScreen();
    }

    private void launchPlayerScreen(GameManager gm) {
        System.out.println("Player screen launched");
    }

    @Test
    void testLaunchPlayerScreen() {
        gameManagerStub.launchPlayerScreen();
    }

    @Test
    void testRestartRound() {
        playerStub.setLives(5);
        gameManagerStub.restartRound();
        assertEquals(4, playerStub.getLives());  // Once restart game, player's live - 1
    }

    @Test
    void testGameRoundProgression() {
        gameManagerStub.nextRound();
        assertEquals(2, roundsStub.getCurrentRound());  // Round++
        assertEquals(20, gameManagerStub.cartsList.get(0).getMaxSize());// Carts' maximum + 20 when into next round
    }

    private void clearScreen() {
        System.out.println("Screen Cleared");
    }

    @Test
    void testEndRound() {
        //  TODO
    }

    // Definitions of stub classes
    class PlayerStub extends Player {
        private int lives = 5;

        @Override
        public void loseLives() {
            lives--;
        }
        @Override
        public int getLives() {
            return lives;
        }

        public void setLives(int lives) {
            this.lives = lives;
        }
    }

    class RoundsStub extends Rounds {
        private int currentRound = 1;
        private int maxRound = 5;

        @Override
        public float getCurrentRound() {
            return currentRound;
        }

        @Override
        public void setCurrentRound(int currentRound) {
            this.currentRound = currentRound;
        }

        @Override
        public float getMaxRound() {
            return maxRound;
        }

        @Override
        public void setMaxRound(int maxRound) {
            this.maxRound = maxRound;
        }
    }

    class CounterServiceStub extends CounterService {
        public CounterServiceStub(GameManager gameManager) {
            super(gameManager);
        }

        @Override
        public void stopTimer() {
            // Custom behavior for testing
        }
    }
}

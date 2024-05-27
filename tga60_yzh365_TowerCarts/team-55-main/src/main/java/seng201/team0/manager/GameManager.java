package seng201.team0.manager;

import javafx.application.Platform;
import seng201.team0.factors.Cart;
import seng201.team0.factors.Player;
import seng201.team0.factors.Rounds;
import seng201.team0.factors.Tower;
import seng201.team0.interfaces.TimerListener;
import seng201.team0.services.CounterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static seng201.team0.enums.Difficulty.*;

/**
 * Manages the player, rounds and cart and screen transitions within the game.
 * @author tga60
 */

public class GameManager implements TimerListener {
    public Player player = new Player();
    public List<Cart> cartsList = new ArrayList<>();

    public Rounds rounds = new Rounds();
    private int currentTime = 0;
    public CounterService counterService = new CounterService(this);
    private ItemManager itemManager = new ItemManager();
    private final Consumer<GameManager> setupScreenLauncher;
    private final Consumer<GameManager> gameScreenLauncher;
    private final Consumer<GameManager> roundCompleteLauncher;
    private final Consumer<GameManager> roundFailedLauncher;

    private final Runnable clearScreen;
    private final Consumer<GameManager> shopScreenLaunch;
    private final Consumer<GameManager> playerScreenLaunch;
    private final Consumer<GameManager> failScreenLauncher;
    private final Consumer<GameManager> winScreenLauncher;


    /**
     * Sets up the Consumer interfaces for launching screens.
     *
     * @param setupScreenLauncher Consumer functional interface to handle setup screen launching.
     * @param gameScreenLauncher Consumer functional interface to handle game screen launching.
     * @param clearScreen Runnable functional interface to clear the screen.
     * @param shopScreenLaunch Consumer functional interface to handle shop screen launching.
     * @param roundCompleteLauncher Consumer functional interface to handle round complete screen launching.
     * @param roundFailedLauncher Consumer functional interface to handle round failed screen launching.
     * @param playerScreenLaunch Consumer functional interface to handle player screen launching.
     * @param failScreenLauncher Consumer functional interface to handle game fail screen launching.
     * @param winScreenLauncher Consumer functional interface to handle game win screen launching.
     */
    public GameManager(Consumer<GameManager> setupScreenLauncher, Consumer<GameManager> gameScreenLauncher,
                       Runnable clearScreen, Consumer<GameManager> shopScreenLaunch,
                       Consumer<GameManager> roundCompleteLauncher,Consumer<GameManager> roundFailedLauncher,
                       Consumer<GameManager> playerScreenLaunch, Consumer<GameManager> failScreenLauncher,
                       Consumer<GameManager> winScreenLauncher) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.gameScreenLauncher = gameScreenLauncher;
        this.clearScreen = clearScreen;
        this.shopScreenLaunch = shopScreenLaunch;
        this.roundCompleteLauncher = roundCompleteLauncher;
        this.roundFailedLauncher = roundFailedLauncher;
        this.playerScreenLaunch = playerScreenLaunch;
        this.failScreenLauncher = failScreenLauncher;
        this.winScreenLauncher = winScreenLauncher;
        launchSetupScreen();
    }


    /**
     * Sets the list of carts.
     *
     * @param towerList Create carts for each item in the tower list.
     */
    public void setCartsList(List<Tower> towerList) {
        for(Tower tower:towerList)
        {
            cartsList.add(new Cart(tower.getItemResourceType()));
        }
    }

    /**
     * Updates the carts distance and size.
     * Adds to the towers moneyMade.
     * Plays random event.
     * Launches round failed screen if a cart reaches the end.
     * Launches round complete screen if all cart sizes = cart max sizes.
     * Launches win screen if all cart sizes = cart max sizes on the final round.
     */
    public void updateValues(){
        boolean finished = true;
        for (int i = 0; i < cartsList.size(); i++) {
            Tower tempTower = itemManager.getTowerList().get(i);
            Tower tempReserve = itemManager.getReserveTowers()[i];
            Cart tempCart = cartsList.get(i);

            if (cartsList.get(i).getCurrentSize() < cartsList.get(i).getMaxSize()) {
                finished = false;
                addToCarts( tempTower, tempReserve, tempCart);
                if(cartsList.get(i).getCurrentDistance() < 100) {
                    cartsList.get(i).setCurrentDistance(currentTime * 10);

                }
                else
                {
                    if(player.getLives() > 1) {
                        launchRoundFailedScreen();
                    }
                    else
                    {
                        launchFailScreen();
                    }

                    counterService.stopTimer();
                }
            }
            if(tempCart.getCurrentSize() >= tempCart.getMaxSize()) {
                tempCart.setCurrentSize(tempCart.getMaxSize());

            }
        }

        if(finished)
        {
            counterService.stopTimer();
            if(rounds.getCurrentRound() >= rounds.getMaxRound())
            {
                launchWinScreen();
            }
            else {
                launchRoundCompleteScreen();
            }
        }
        else {
            randomEvent();
        }
    }

    /**
     * Gets a random chance depending on the difficulty.
     * On random chance selected break random tower, or tower reserve if tower is broken.
     */
    private void randomEvent()
    {
        if(currentTime <= 5)
        {
            int chanceOfRandomEvent = 1;
            if(player.getDifficulty() == EASIEST)
            {
                chanceOfRandomEvent = 6;
            } else   if(player.getDifficulty() == EASY)
            {
                chanceOfRandomEvent = 5;
            } else  if(player.getDifficulty() == NORMAL)
            {
                chanceOfRandomEvent = 4;
            } else  if(player.getDifficulty() == HARD)
            {
                chanceOfRandomEvent = 3;
            } else  if(player.getDifficulty() == HARDEST)
            {
                chanceOfRandomEvent = 2;
            }

            if(new Random().nextInt(chanceOfRandomEvent) == 0)
            {
                int randomTowerIndex = new Random().nextInt(5);
                Tower randomTower = itemManager.getTowerList().get(randomTowerIndex);
                Tower randomReserve = itemManager.getReserveTowers()[randomTowerIndex];
                if(randomTower.getIsBroken())
                {
                    if(randomReserve != null)
                    {
                             randomReserve.setIsBroken(true);
                    }
                }
                randomTower.setIsBroken(true);
            }
        }
    }


    /**
     * Updates the carts distance by the current time, and the size by the production rate of the corresponding tower
     * if tower isn't broken.
     * Adds to the towers moneyMade by production rate * money made per material.
     */
    private void addToCarts(Tower tower, Tower reserve, Cart cart){
        boolean tempReserveIsBroken;
        if(reserve == null)
        {
            tempReserveIsBroken = true;
        }
        else
        {
            tempReserveIsBroken = reserve.getIsBroken();
        }
        if(!tower.getIsBroken() || !tempReserveIsBroken) {
            int newMaterialAmount = tower.getProductionRate() + cart.getCurrentSize();
            cart.setCurrentSize(newMaterialAmount);
            tower.increaseMoneyMade();
            player.addMoney(tower.getMoneyMadePerMaterial() * tower.getProductionRate());
        }
    }

    /**
     * Resets the towers and carts.
     * Increase the tower levels, doubles increase amount if there is a reserve.
     * Increase the cart max size.
     * Stops timer.
     */
    public void nextRound()
    {
        resetRoundItems();
        rounds.setCurrentRound((int) rounds.getCurrentRound() + 1);
        for (int i = 0; i < itemManager.getTowerList().size(); i++)
        {
            cartsList.get(i).setMaxSize(cartsList.get(i).getMaxSize() + 20);
            int addAmount = 1;
            if(itemManager.getReserveTowers()[i] != null)
            {
                addAmount *=2;
                itemManager.getReserveTowers()[i].addLevel(addAmount);
            }
            itemManager.getTowerList().get(i).addLevel(addAmount);
        }
        counterService.stopTimer();
    }

    /**
     * Sets cart distances and size to 0.
     * Sets tower money made to 0;
     */
    private void resetRoundItems() {
        for (int i = 0; i < cartsList.size(); i++)
        {
            cartsList.get(i).setCurrentDistance(0);
            cartsList.get(i).setCurrentSize(0);
            itemManager.getTowerList().get(i).setMoneyMade(0);
        }
    }


    /**
     * Resets the towers and carts.
     * Takes one live away from the player.
     * Stops the timer.
     */
    public void restartRound()
    {
        resetRoundItems();
        player.loseLives();
        counterService.stopTimer();
    }


    /**
     * Gets the player.
     *
     * @return the player.
     */
    public Player getPlayer(){return this.player;}


    /**
     * Gets the item manager.
     *
     * @return the item manager.
     */
    public ItemManager getItemManager(){return this.itemManager;}

    /**
     * Sets the item manager.
     *
     * @param itemManager the item manger for the towers and carts.
     */
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    /**
     * Gets the rounds.
     *
     * @return the rounds.
     */
    public Rounds getRounds() {
        return this.rounds;
    }

    /**
     * Sets the rounds class.
     *
     * @param rounds used for holding the current round and max round
     */
    public void setRounds(Rounds rounds) {
        this.rounds = rounds;
    }

    /**
     * Gets the counterService.
     *
     * @return the counterService.
     */
    public CounterService getCounterService() {
        return this.counterService;
    }


    /**
     * Launches the setup screen.
     */
    public void launchSetupScreen() {
        setupScreenLauncher.accept(this);
    }


    /**
     * Closes the current screen and launches the round complete screen.
     */
    public void launchRoundCompleteScreen() {
        clearScreen.run();
        roundCompleteLauncher.accept(this);
    }

    /**
     * Closes the current screen and launches the round failed screen.
     */
    public void launchRoundFailedScreen() {
        clearScreen.run();
        roundFailedLauncher.accept(this);
    }

    /**
     * Closes the current screen and launches the fail screen.
     */
    public void launchFailScreen() {
        clearScreen.run();
        failScreenLauncher.accept(this);
    }

    /**
     * Closes the current screen and launches the win screen.
     */
    public void launchWinScreen() {
        clearScreen.run();
        winScreenLauncher.accept(this);
    }

    /**
     * Closes the current screen and launches the game screen.
     */
    public void launchGameScreen() {
        clearScreen.run();
        gameScreenLauncher.accept(this);
    }


    /**
     * Closes the current screen and launches the shop screen.
     */
    public void launchShopScreen() {
        shopScreenLaunch.accept(this);
    }

    /**
     * Closes the current screen and launches the player screen.
     */
    public void launchPlayerScreen() {
        playerScreenLaunch.accept(this);
    }

    /**
     * Observes the current time in the counter service.
     * Updates values every time current time is updated.
     * @param currentTime The number of seconds into the round.
     */
     @Override
     public void onTimeUpdated(int currentTime) {
       Platform.runLater(() ->
      {
          this.currentTime = currentTime;
          updateValues();
      });
    }


}

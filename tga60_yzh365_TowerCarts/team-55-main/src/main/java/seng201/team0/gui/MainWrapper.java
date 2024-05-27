package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team0.manager.GameManager;
import seng201.team0.services.MarketService;

import java.io.IOException;
/**
 * Used to create and launch new windows.
 * @author tga60 & yzh365.
 */
public class MainWrapper {
    @FXML
    private Pane pane;
    private Stage stage;


    /**
     * Creates a new game manager giving it all the consumer functions to be called on with in game manager.
     * @param stage sets the stage to give stage.
     */
    public void init(Stage stage) {
        this.stage = stage;
        new GameManager(this::launchSetupScreen, this::launchGameScreen, this::clearPane, this::launchShopScreen,
                this::launchRoundCompleteScreen, this::launchRoundFailedScreen, this::launchPlayerScreen,
                this::launchFailScreen, this:: launchWinScreen);
    }


    /**
     * Loads the FXML setup screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchSetupScreen(GameManager gameManager) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource("/fxml/setupScreen.fxml"));
            setupLoader.setControllerFactory(param -> new SetupScreenController(gameManager));
            Parent setupParent  = setupLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Tower Setup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML game screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchGameScreen(GameManager gameManager) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
            mainScreenLoader.setControllerFactory(param -> new GameScreenController(gameManager));
            Parent setupParent  = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML round complete screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchRoundCompleteScreen(GameManager gameManager) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/roundCompleteScreen.fxml"));
            mainScreenLoader.setControllerFactory(param -> new RoundCompleteScreenController(gameManager));
            Parent setupParent  = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Next Round");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML round fail screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchRoundFailedScreen(GameManager gameManager) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/roundFailedScreen.fxml"));
            mainScreenLoader.setControllerFactory(param -> new RoundFailedScreenController(gameManager));
            Parent setupParent  = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Round Failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML shop screen.
     * @param gameManager is giving to the controller, so it can read the variables within the market service.
     */
    public void launchShopScreen(GameManager gameManager) {
        try {
            pane.getChildren().clear();

            FXMLLoader shopScreenLoader = new FXMLLoader(getClass().getResource("/fxml/shopScreen.fxml"));
            MarketService marketService = new MarketService(gameManager);
            shopScreenLoader.setControllerFactory(param -> new ShopScreenController(marketService));
            Parent shopParent = shopScreenLoader.load();
            pane.getChildren().add(shopParent);
            stage.setTitle("Shop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML player screen.
     * @param gameManager is giving to the controller, so it can read the variables within the market service.
     */
    public void launchPlayerScreen(GameManager gameManager) {
        try {
            pane.getChildren().clear();

            FXMLLoader playerScreenLoader = new FXMLLoader(getClass().getResource("/fxml/playerScreen.fxml"));
            MarketService marketService = new MarketService(gameManager);
            playerScreenLoader.setControllerFactory(param -> new PlayerScreenController(marketService));
            Parent parent = playerScreenLoader.load();
            pane.getChildren().add(parent);
            stage.setTitle("Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML fail screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchFailScreen(GameManager gameManager) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/failScreen.fxml"));
            mainScreenLoader.setControllerFactory(param -> new FailScreenController(gameManager));
            Parent setupParent  = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the FXML win screen.
     * @param gameManager is giving to the controller, so it can read the variables within the game manager.
     */
    public void launchWinScreen(GameManager gameManager) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/winScreen.fxml"));
            mainScreenLoader.setControllerFactory(param -> new WinScreenController(gameManager));
            Parent setupParent  = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Win Screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Removes the current loaded screen.
     */

    public void clearPane() {
        pane.getChildren().removeAll(pane.getChildren());
    }

}

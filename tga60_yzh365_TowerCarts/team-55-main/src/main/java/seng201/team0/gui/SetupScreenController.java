package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import seng201.team0.factors.Tower;
import seng201.team0.manager.GameManager;
import seng201.team0.manager.ItemManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Setup screen controller, calls the managers to set the variables for the game.
 * @author tga60.
 */

public class SetupScreenController {

    @FXML
    private TextArea nameInput;

    @FXML
    private Label nameLable;
    @FXML
    private Label reloadLable;

    @FXML
    private Label priceLable;
    @FXML
    private Label errorLabel;
    @FXML
    private Button coalButton, stoneButton, ironButton, copperButton, silverButton, goldButton;

    @FXML
    private Button selected1Button, selected2Button, selected3Button;

    @FXML
    private Slider difficultySlider, roundSlider;

    private ItemManager itemManager;
    private GameManager gameManager;

    private int selectedTowerIndex = -1;
    List<Button> selectedTowerButtons;
    List<Button> towerButtons;
    private final Tower[] selectedTowers = new Tower[3];

    /**
     * Constructs a SetupScreenController .
     * @param gameManager The game manger for setting and getting variables.
     */
    public SetupScreenController(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }


    /**
     * Check to see if name and selected tower list meet requirements.
     * On accept button pressed, add selected tower list to item manager tower list.
     * Launch the game screen.
     */
    @FXML
    private void onAccept() {
        if(gameManager.getPlayer().setName(nameInput.getText()))
        {
            Tower[] tempSelectedTowers = Arrays.stream(selectedTowers)
                    .filter(Objects::nonNull)
                    .toArray(Tower[]::new);
            if(tempSelectedTowers.length == 3) {
                itemManager.setTowerList(Arrays.stream(selectedTowers).filter((Objects::nonNull)).toList());
                gameManager.setCartsList(itemManager.getTowerList());
                new GameScreenController(gameManager);
                gameManager.launchGameScreen();
            }
            else
            {
                errorLabel.setText("Ensure 3 towers are selected.");
            }
        }
        else
        {
            errorLabel.setText("Ensure the name is between 3 and 15 characters and no special characters.");
        }

    }

    /**
     * Set the up list with their corresponding buttons and labels.
     * Sets variables.
     */
    public void initialize()
    {
        itemManager = gameManager.getItemManager();
        selectedTowerButtons = List.of(selected1Button, selected2Button, selected3Button);
        towerButtons = List.of(coalButton,stoneButton,ironButton,copperButton,silverButton,goldButton);

        setSelectedTowers();
        setDifficulty();
        setRounds();
    }

    /**
     * Listens for when the round slider is updated, and sets round class max round to the value of the slider.
     */
    private void setRounds(){
        roundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameManager.getRounds().setMaxRound(newValue.intValue());
        });
    }


    /**
     * Listens for when the difficulty slider is updated, and sets player class difficulty to the value of the slider.
     */
    private void setDifficulty(){
        difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() % 5 == 0) {
                gameManager.getPlayer().chooseDifficulty(newValue.intValue());
            }
        });
    }

    /**
     * Gets the index from the tower button pressed, and adds a tower of that index to the selected to the list,
     * when the selected button is pressed.
     */
    private void setSelectedTowers(){
        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;

            towerButtons.get(i).setOnAction(event -> {
                updateStats(itemManager.getTowersOptions().get(finalI));
                selectedTowerIndex = finalI;
                towerButtons.forEach(button -> {
                    if (button == towerButtons.get(finalI)) {
                        button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
                    } else {
                        button.setStyle("");
                    }
                });
            });
        }
        for (int i = 0; i < selectedTowerButtons.size(); i++) {
            int finalI = i;
            selectedTowerButtons.get(i).setOnAction(event -> {
                if (selectedTowerIndex != -1) {
                    selectedTowerButtons.get(finalI).setText(itemManager.getTowersOptions().get(selectedTowerIndex).getItemName());
                    selectedTowers[finalI] = new Tower(itemManager.getTowersOptions().get(selectedTowerIndex).getItemResourceType());
                }
            });
        }
    }

    /**
     * Updates the stats on the screen, to the stats of the selected tower.
     * @param tower the selected tower.
     */
    private void updateStats(Tower tower) {
        nameLable.setText("Resource: "+ tower.getItemName());
        reloadLable.setText("Production Rate:" + (int) (tower.getProductionRate()));
        priceLable.setText("Resource price: $"+ (int) (tower.getMoneyMadePerMaterial()));
    }







}

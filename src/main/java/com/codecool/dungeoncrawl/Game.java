package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.IO.GameMapIO;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.PotionType;
import com.codecool.dungeoncrawl.logic.map.Tiles;
import com.codecool.dungeoncrawl.logic.map.*;
import com.codecool.dungeoncrawl.logic.util.*;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.map.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game extends Application {

    int mapCounter = 1;

    Scene scene;
    Stage saveModal = new Stage();
    Stage loadModal = new Stage();
    Stage menuModal = new Stage();
    Stage errorModal = new Stage();
    Stage manualModal = new Stage();
    FileChooser importWindow = new FileChooser();
    FileChooser exportWindow = new FileChooser();

    GameMap map;
    Canvas canvas = new Canvas(
            NumberParameters.TILE_WIDTH_MULTIPLIER_V.getValue() * Tiles.TILE_WIDTH,
            NumberParameters.TILE_WIDTH_MULTIPLIER_V1.getValue() * Tiles.TILE_WIDTH
    );
    GraphicsContext context = canvas.getGraphicsContext2D();

    GameDatabaseManager dbManager = new GameDatabaseManager();

    GameMapIO gameMapIO = new GameMapIO();
    Util util = new Util();
    GameConditions gameConditions = new GameConditions();
    boolean confirmQuit = false;

    Label healthLabel = new Label();
    Label defenseLabel = new Label();
    Label attackLabel = new Label();
    Label inventoryLabel = new Label();
    Label quitLabel = new Label();
    Label actionLabel = new Label();
    Label pickUpInfo = new Label();

    Pane lineBreak = new Pane();
    Pane lineBreak2 = new Pane();
    Pane lineBreak3 = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane ui = new GridPane();
        setUpUi(ui);

        BorderPane borderPane = new BorderPane();
        setUpBorderPane(ui, borderPane);

        saveModal.initModality(Modality.WINDOW_MODAL);
        saveModal.initOwner(primaryStage);
        saveModal.setTitle("Save Game");
        loadModal.initModality(Modality.WINDOW_MODAL);
        loadModal.initOwner(primaryStage);
        loadModal.setTitle("Load Game");
        menuModal.initModality(Modality.WINDOW_MODAL);
        menuModal.initOwner(primaryStage);
        menuModal.setTitle("Export/Import Game");
        errorModal.initModality(Modality.WINDOW_MODAL);
        errorModal.initOwner(menuModal);
        errorModal.setTitle("File format error");
        manualModal.initModality(Modality.WINDOW_MODAL);
        manualModal.initOwner(primaryStage);
        manualModal.setTitle("Game manual");
        setUpModal(saveModal, "Save");
        setUpModal(loadModal, "Load");
        setUpModal(errorModal, "Ok");
        setupMenu(menuModal);
        setupGameManual(manualModal);
        importWindow.setTitle("Select exported game");
        exportWindow.setTitle("Export game as");


        scene = new Scene(borderPane);
        setUpScene(primaryStage, scene, MapName.MAP1.getMapName(), null);
        scene.getStylesheets().add("style.css");
    }

    private void setUpModal (Stage modal, String buttonText) {
        Label saveGame = new Label();
        Label error = new Label();
        TextField saveName = new TextField();
        Button actionButton = new Button();
        actionButton.setText(buttonText);
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        EventHandler<ActionEvent> cancelEvent = actionEvent -> {
            modal.hide();
            if (buttonText.equals("Ok")) {
                menuModal.hide();
            }
        };
        cancelButton.setOnAction(cancelEvent);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30));
        vBox.setSpacing(8);
        vBox.setAlignment(Pos.CENTER);
        if (buttonText.equals("Save")) {
            EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        dbManager.setup();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    System.out.println(map); // map string format need to be implemented
                    if (dbManager.checkExistingSave(saveName.getText())) {
                        //Player update
                        PlayerModel currentPlayer = dbManager.updatePlayer(map.getPlayer());
                        dbManager.updateGameState(map.getMapName().toString(), formatter.format(date), currentPlayer, saveName.getText());
                        dbManager.updatePlayerInventory(1 , map.getPlayer().getInventory());
                    } else {
                        // Player save
                        PlayerModel currentPlayer = dbManager.savePlayer(map.getPlayer());
                        dbManager.saveGameState(map.getMapName().toString(), formatter.format(date), currentPlayer, saveName.getText());
                        dbManager.savePlayerInventory(currentPlayer.getId(), map.getPlayer().getInventory());
                    }
                    modal.hide();
                }
            };
            actionButton.setOnAction(saveEvent);
            saveGame.setText("Save game as:");
            vBox.getChildren().addAll(saveGame, saveName);
        } else if (buttonText.equals("Load")) {
            try {
                dbManager.setup();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            List<String> savedGames = dbManager.displayAllSaves();
            for (int i = 0; i < savedGames.size(); i++) {
                Button loadButton = new Button();
                loadButton.setText(savedGames.get(i));
                EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String saveName = loadButton.getText();
                        int playerId = dbManager.getPlayerId(saveName);
                        //Load game
                        PlayerModel selectedPlayer = dbManager.loadPlayerData(playerId);
                        System.out.println(dbManager.loadGameState(playerId));
                        System.out.println(selectedPlayer);
                        System.out.println(dbManager.loadPlayersInventory(playerId));
                        map.getPlayer().setHealth(selectedPlayer.getHp());
                        map.getPlayer().setDrunk(selectedPlayer.getDrunk());

                        modal.hide();
                    }
                };
                vBox.getChildren().add(loadButton);
                loadButton.setOnAction(loadEvent);
            }
        } else if (buttonText.equals("Ok")) {
            EventHandler<ActionEvent> errorEvent = actionEvent -> modal.hide();
            actionButton.setOnAction(errorEvent);
            error.setText("IMPORT ERROR! Unfortunately the given file is in wrong format. Please try another one!");
            vBox.getChildren().add(error);
        }
        if (!buttonText.equals("Load")) {
            vBox.getChildren().addAll(actionButton, cancelButton);
        } else {
            vBox.getChildren().add(cancelButton);
        }
        Scene modalScene = new Scene(vBox);
        modal.setScene(modalScene);
    }

    private void setupGameManual(Stage modal) {
        Label controls = new Label();
        controls.setText(util.getGameManual());
        controls.setTextAlignment(TextAlignment.JUSTIFY);
        Button closeButton = new Button();
        closeButton.setText("Close");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(40));
        vBox.setSpacing(8);
        vBox.setAlignment(Pos.CENTER);
        EventHandler<ActionEvent> closeEvent = actionEvent -> modal.hide();
        closeButton.setOnAction(closeEvent);
        vBox.getChildren().addAll(controls, closeButton);
        Scene modalScene = new Scene(vBox);
        modalScene.setFill(Color.TRANSPARENT);
        modal.initStyle(StageStyle.TRANSPARENT);
        vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        //vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        modal.setScene(modalScene);
        controls.getStyleClass().add("status-labels");
    }

    private void setupMenu(Stage modal) {
        Button importButton = new Button();
        Button exportButton = new Button();
        Button cancelButton = new Button();
        importButton.setText("Import game");
        exportButton.setText("Export game");
        cancelButton.setText("Cancel");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(40));
        vBox.setSpacing(8);
        vBox.setAlignment(Pos.CENTER);
        EventHandler<ActionEvent> importEvent = actionEvent -> {
            File file = importWindow.showOpenDialog(modal);
            String path;
            if (file != null) {
                path = file.getPath();
                if (!path.contains(".json")) {
                    errorModal.show();
                } else {
                    try {
                        map = gameMapIO.loadGameMap(path);
                        refresh(map.getPlayer().getX(), map.getPlayer().getY());
                        actionLabel.setText("");
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    modal.hide();
                }
            } else {
                modal.hide();
            }
        };
        importButton.setOnAction(importEvent);
        EventHandler<ActionEvent> exportEvent = actionEvent -> {
            File file = exportWindow.showSaveDialog(modal);
            String path;
            if (file != null) {
                path = file.getPath();
                try {
                    gameMapIO.saveGameMap(map, path);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            modal.hide();
        };
        exportButton.setOnAction(exportEvent);
        EventHandler<ActionEvent> cancelEvent = actionEvent -> modal.hide();
        cancelButton.setOnAction(cancelEvent);
        vBox.getChildren().addAll(importButton, exportButton, cancelButton);
        Scene modalScene = new Scene(vBox);
        modal.setScene(modalScene);
    }

    private void setUpBorderPane(GridPane ui, BorderPane borderPane) {
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
    }

    private void setUpScene(Stage primaryStage, Scene scene, String mapToLoad, GameMap previousMap) {
        primaryStage.setScene(scene);
        int[] coordinates = MapLoader.getPlayerPosition(mapToLoad);
        map = MapLoader.loadMap(coordinates[2], mapToLoad, previousMap);
        map.setMapName(MapName.MAP1);
        refresh(coordinates[1], coordinates[0]);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle(StringFactory.TITLE.message);
        primaryStage.show();
    }

    private void setUpNextScene(String mapToLoad, GameMap previousMap){
        int[] coordinates = MapLoader.getPlayerPosition(mapToLoad);
        map = MapLoader.loadMap(coordinates[2], mapToLoad, previousMap);
        refresh(coordinates[1], coordinates[0]);
    }

    private void setUpUi(GridPane ui) {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        lineBreak.minHeightProperty().bind(quitLabel.heightProperty());
        lineBreak2.minHeightProperty().bind(quitLabel.heightProperty());
        lineBreak3.minHeightProperty().bind(quitLabel.heightProperty());
        setLabels(ui);
        pickUpInfo.setText(StringFactory.MANUAL.message);
        pickUpInfo.setWrapText(true);
        quitLabel.setWrapText(true);
    }

    private void setLabels(GridPane ui) {
        ui.add(new Label(StringFactory.HEALTH_LABEL.message), 0, 0);
        ui.getChildren().get(0).getStyleClass().add("main-labels");
        ui.add(healthLabel, 1, 0);
        ui.add(new Label(StringFactory.DEFENSE_LABEL.message), 0, 1);
        ui.getChildren().get(2).getStyleClass().add("main-labels");
        ui.add(defenseLabel, 1, 1);
        ui.add(new Label(StringFactory.ATTACK_LABEL.message), 0, 2);
        ui.getChildren().get(4).getStyleClass().add("main-labels");
        ui.add(attackLabel, 1, 2);
        ui.add(new Label(StringFactory.ACTION_LABEL.message), 0, 3);
        ui.getChildren().get(6).getStyleClass().add("main-labels");
        ui.add(actionLabel, 0, 4, 2, 1);
        ui.add(lineBreak3, 0, 5);
        ui.add(new Label(StringFactory.INVENTORY_LABEL.message), 0, 6);
        ui.getChildren().get(9).getStyleClass().add("main-labels");
        ui.add(inventoryLabel, 0, 7, 2, 1);
        ui.add(lineBreak, 0, 8);
        ui.add(pickUpInfo, 0, 9, 2, 1);
        ui.add(lineBreak2, 0, 10);
        ui.add(quitLabel, 0, 11, 2, 1);
        healthLabel.getStyleClass().add("status-labels");
        defenseLabel.getStyleClass().add("status-labels");
        attackLabel.getStyleClass().add("status-labels");
        actionLabel.getStyleClass().add("status-labels");
        inventoryLabel.getStyleClass().add("status-labels");
        pickUpInfo.getStyleClass().add("status-labels");
        quitLabel.getStyleClass().add("main-labels");
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(Direction.NORTH.getX(), Direction.NORTH.getY());
                map.getPlayer().interactions(map, actionLabel);
                map.monsterInteractions();
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case DOWN:
                map.getPlayer().move(Direction.SOUTH.getX(), Direction.SOUTH.getY());
                map.getPlayer().interactions(map, actionLabel);
                map.monsterInteractions();
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case LEFT:
                map.getPlayer().move(Direction.WEST.getX(), Direction.WEST.getY());
                map.getPlayer().interactions(map, actionLabel);
                map.monsterInteractions();
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case RIGHT:
                map.getPlayer().move(Direction.EAST.getX(), Direction.EAST.getY());
                map.getPlayer().interactions(map, actionLabel);
                map.monsterInteractions();
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case Q:
                quitLabel.setText(StringFactory.WANT_TO_QUIT.message);
                confirmQuit = true;
                break;
            case ENTER:
                map.getPlayer().pickUpItem(map);
                break;
            case Y:
                if (confirmQuit) {
                    Util.exitGame();
                }
                break;
            case N:
                confirmQuit = false;
                quitLabel.setText("");
                break;
            case F:
                if (map.getPlayer().hasItem(ItemType.FOOD))
                    map.getPlayer().consumeFood();
                break;
            case H:
                Item potionItem = map.getPlayer().searchForPotion(PotionType.HEALING_POTION);
                if (potionItem != null)
                    map.getPlayer().consumePotion(StringFactory.HEALING_POTION.message);
                break;
            case G:
                Item potionItem2 = map.getPlayer().searchForPotion(PotionType.STONE_SKIN_POTION);
                if (potionItem2 != null)
                    map.getPlayer().consumePotion(StringFactory.STONE_SKIN_POTION.message);
                break;
            case J:
                Item potionItem3 = map.getPlayer().searchForPotion(PotionType.MIGHT_POTION);
                if (potionItem3 != null)
                    map.getPlayer().consumePotion(StringFactory.MIGHT_POTION.message);
                break;
            case B:
                if (map.getPlayer().hasItem(ItemType.BOAT)) {
                    map.getPlayer().leaveBoat();
                }
                break;
            case A:
                if (map.getPlayer().hasItem((ItemType.ALCOHOL))) {
                    map.getPlayer().consumeAlcohol();
                }
                break;
            case S:
                saveModal.show();
                break;
            case L:
                loadModal.show();
                break;
            case M:
                menuModal.show();
                break;
            case ESCAPE:
                manualModal.show();
                break;
        }
    }

    private void refresh(int playerX, int playerY) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int diffX = (int) (canvas.getWidth() / (NumberParameters.TILE_WIDTH_MULTIPLIER.getValue() * Tiles.TILE_WIDTH));
        int diffY = (int) (canvas.getHeight() / (NumberParameters.TILE_WIDTH_MULTIPLIER.getValue() * Tiles.TILE_WIDTH));
        drawingCells(playerX, playerY, diffX, diffY);
        refreshUi();
    }


    private void checkIfInventoryIsEmpty(Map<Item, Integer> playerInventory, String inventoryContents) {
        if (playerInventory.size() == 0) {
            inventoryLabel.setText(StringFactory.INVENTORY_EMPTY.message);
        } else {
            inventoryLabel.setText(inventoryContents);
        }
    }

    private String buildInventoryString(Map<Item, Integer> playerInventory) {
        List<String> itemsInInventory = new ArrayList<>();
        for (Item item : playerInventory.keySet()) {
            itemsInInventory.add(item.getName());
            itemsInInventory.add(playerInventory.get(item).toString() + "\n");
        }
        return String.join(" ", itemsInInventory);
    }

    private void drawingCells(int playerX, int playerY, int diffX, int diffY) {
        for (int x = 0; x < canvas.getWidth() && Math.max(playerX - diffX, 0) + x < map.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight() && Math.max(playerY - diffY, 0) + y < map.getHeight(); y++) {
                Cell cell = map.getCell(Math.max(playerX - diffX, 0) + x, Math.max(playerY - diffY, 0) + y);
                drawingTiles(x, y, cell);
            }
        }
    }

    private void drawingTiles(int x, int y, Cell cell) {
        if (gameConditions.isCellOccupied(cell)) {
            Tiles.drawTile(context, cell.getActor(), x, y);
        } else if (cell.getItem() != null) {
            Tiles.drawTile(context, cell.getItem(), x, y);
        } else {
            Tiles.drawTile(context, cell, x, y);
        }
    }

    private void refreshUi() {
        healthLabel.setText("" + map.getPlayer().getHealth());
        defenseLabel.setText("" + map.getPlayer().getDefense());
        attackLabel.setText("" + map.getPlayer().getAttack());
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        String inventoryContents = buildInventoryString(playerInventory);
        checkIfInventoryIsEmpty(playerInventory, inventoryContents);
    }

    private void enterTheDoor(){
        if (doorIsOpen()) {
            switch (map.getMapName()) {
                case MAP1:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP2);
                    map.setMapName(MapName.MAP2);
                    mapCounter++;
                    break;
                case MAP2:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP3);
                    map.setMapName(MapName.MAP3);
                    mapCounter++;
                    break;
                case MAP3:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP4);
                    map.setMapName(MapName.MAP4);
                    mapCounter++;
                    break;
                case MAP4:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP5);
                    map.setMapName(MapName.MAP5);
                    mapCounter++;
                    break;
            }
        }
        else if (doorIsFake()){
            goToNextMap(MapName.DEAD);
            }
        }

    private void goToNextMap(MapName mapName) {
        setUpNextScene( mapName.getMapName(), map);
    }


    private boolean doorIsOpen(){
        return gameConditions.checkOpenDoor(map.getPlayer().getX(), map.getPlayer().getY(), map);
    }

    private boolean doorIsFake() {
        return gameConditions.checkFakeDoor(map.getPlayer().getX(), map.getPlayer().getY(), map);
    }

}

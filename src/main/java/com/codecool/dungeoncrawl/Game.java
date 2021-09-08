package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.IO.GameMapIO;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.items.ItemActions;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.PotionType;
import com.codecool.dungeoncrawl.logic.map.Tiles;
import com.codecool.dungeoncrawl.logic.map.*;
import com.codecool.dungeoncrawl.logic.util.*;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.map.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    Actions actions = new Actions();
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
    }

    private void setUpModal (Stage modal, String buttonText) {
        Label saveGame = new Label();
        Label error = new Label();
        TextField saveName = new TextField();
        Button actionButton = new Button();
        actionButton.setText(buttonText);
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        EventHandler<ActionEvent> cancelEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modal.hide();
                if (buttonText.equals("Ok")) {
                    menuModal.hide();
                }
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
                    //dbManager.saveGameState(map);
                    dbManager.savePlayer(map.getPlayer());
                    //dbManager.saveInventory(map);
                    modal.hide();
                }
            };
            actionButton.setOnAction(saveEvent);
            saveGame.setText("Save game as:");
            vBox.getChildren().addAll(saveGame, saveName);
        } else if (buttonText.equals("Load")) {
            EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    modal.hide();
                }
            };
            actionButton.setOnAction(loadEvent);
        } else if (buttonText.equals("Ok")) {
            EventHandler<ActionEvent> errorEvent = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    modal.hide();
                }
            };
            actionButton.setOnAction(errorEvent);
            error.setText("IMPORT ERROR! Unfortunately the given file is in wrong format. Please try another one!");
            vBox.getChildren().add(error);
        }
        vBox.getChildren().addAll(actionButton, cancelButton);
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
        EventHandler<ActionEvent> closeEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modal.hide();
            }
        };
        closeButton.setOnAction(closeEvent);
        vBox.getChildren().addAll(controls, closeButton);
        Scene modalScene = new Scene(vBox);
        modal.setScene(modalScene);
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
        EventHandler<ActionEvent> importEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = importWindow.showOpenDialog(modal);
                String path = "";
                if (file != null) {
                    path = file.getPath();
                    if (!path.contains(".json")) {
                        errorModal.show();
                    } else {
                        try {
                            GameMap loadedMap = gameMapIO.loadGameMap(path);
                            map = loadedMap;
                            refresh(map.getPlayer().getX(), map.getPlayer().getY());
                            actionLabel.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        modal.hide();
                    }
                } else {
                    modal.hide();
                }


            }
        };
        importButton.setOnAction(importEvent);
        EventHandler<ActionEvent> exportEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = exportWindow.showSaveDialog(modal);
                String path = "";
                if (file != null) {
                    path = file.getPath();
                    try {
                        gameMapIO.saveGameMap(map, path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    modal.hide();
                } else {
                    modal.hide();
                }
            }
        };
        exportButton.setOnAction(exportEvent);
        EventHandler<ActionEvent> cancelEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modal.hide();
            }
        };
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

    private void setUpSecondScene(String mapToLoad, GameMap previousMap){
        int[] coordinates = MapLoader.getPlayerPosition(mapToLoad);
        map = MapLoader.loadMap(coordinates[2], mapToLoad, previousMap);
        refresh(coordinates[1], coordinates[0]);
    }

    private void setUpUi(GridPane ui) {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        lineBreak.minHeightProperty().bind(inventoryLabel.heightProperty());
        lineBreak2.minHeightProperty().bind(inventoryLabel.heightProperty());
        lineBreak3.minHeightProperty().bind(inventoryLabel.heightProperty());
        setLabels(ui);
        pickUpInfo.setText(StringFactory.MANUAL.message);
        pickUpInfo.setWrapText(true);
        quitLabel.setWrapText(true);
    }

    private void setLabels(GridPane ui) {
        ui.add(new Label(StringFactory.HEALTH_LABEL.message), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label(StringFactory.DEFENSE_LABEL.message), 0, 1);
        ui.add(defenseLabel, 1, 1);
        ui.add(new Label(StringFactory.ATTACK_LABEL.message), 0, 2);
        ui.add(attackLabel, 1, 2);
        ui.add(new Label(StringFactory.ACTION_LABEL.message), 0, 3);
        ui.add(actionLabel, 0, 4, 2, 1);
        ui.add(lineBreak3, 0, 5);
        ui.add(new Label(StringFactory.INVENTORY_LABEL.message), 0, 6);
        ui.add(inventoryLabel, 0, 7, 2, 1);
        ui.add(lineBreak, 0, 8);
        ui.add(pickUpInfo, 0, 9, 2, 1);
        ui.add(lineBreak2, 0, 10);
        ui.add(quitLabel, 0, 11, 2, 1);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        ItemActions itemActions = new ItemActions();
        switch (keyEvent.getCode()) {
            case UP:
                actions.movePlayer(Direction.NORTH.getX(), Direction.NORTH.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                actions.moveMonsters(map.getGhosts(), map.getPlayer().getCell());
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case DOWN:
                actions.movePlayer(Direction.SOUTH.getX(), Direction.SOUTH.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                actions.moveMonsters(map.getGhosts(), map.getPlayer().getCell());
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case LEFT:
                actions.movePlayer(Direction.WEST.getX(), Direction.WEST.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case RIGHT:
                actions.movePlayer(Direction.EAST.getX(), Direction.EAST.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                enterTheDoor();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case Q:
                quitLabel.setText(StringFactory.WANT_TO_QUIT.message);
                confirmQuit = true;
                break;
            case ENTER:
                actions.pickUpItem(map);
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
                Item foodItem = itemActions.searchForItemByType(map, ItemType.FOOD);
                if (foodItem != null)
                    itemActions.consumeFood(map, foodItem.getName());
                break;
            case H:
                Item potionItem = itemActions.searchForPotion(map, PotionType.HEALING_POTION);
                if (potionItem != null)
                    itemActions.consumePotion(map, StringFactory.HEALING_POTION.message);
                break;
            case G:
                Item potionItem2 = itemActions.searchForPotion(map, PotionType.STONE_SKIN_POTION);
                if (potionItem2 != null)
                    itemActions.consumePotion(map, StringFactory.STONE_SKIN_POTION.message);
                break;
            case J:
                Item potionItem3 = itemActions.searchForPotion(map, PotionType.MIGHT_POTION);
                if (potionItem3 != null)
                    itemActions.consumePotion(map, StringFactory.MIGHT_POTION.message);
                break;
            case B:
                if (map.getPlayer().hasItem(ItemType.BOAT)) {
                    itemActions.leaveBoat(map.getPlayer());
                }
                break;
            case A:
                if (map.getPlayer().hasItem((ItemType.ALCOHOL))) {
                    itemActions.consumeAlcohol(map);
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
            switch (mapCounter) {
                case 1:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP2);
                    map.setMapName(MapName.MAP2);
                    mapCounter++;
                    break;
                case 2:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP3);
                    map.setMapName(MapName.MAP3);
                    mapCounter++;
                    break;
                case 3:
                    map.getPlayer().setDrunk(false);
                    goToNextMap(MapName.MAP4);
                    map.setMapName(MapName.MAP4);
                    mapCounter++;
                    break;
                case 4:
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
        setUpSecondScene( mapName.getMapName(), map);
    }


    private boolean doorIsOpen(){
        return gameConditions.checkOpenDoor(map.getPlayer().getX(), map.getPlayer().getY(), map);
    }

    private boolean doorIsFake() {
        return gameConditions.checkFakeDoor(map.getPlayer().getX(), map.getPlayer().getY(), map);
    }

}

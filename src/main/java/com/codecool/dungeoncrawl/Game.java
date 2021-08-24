package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.Undead;
import com.codecool.dungeoncrawl.logic.items.ItemActions;
import com.codecool.dungeoncrawl.logic.util.Actions;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.util.Booleans;
import com.codecool.dungeoncrawl.logic.util.Direction;
import com.codecool.dungeoncrawl.logic.util.Util;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class Game extends Application {

    GameMap map;
    Canvas canvas = new Canvas(
            30 * Tiles.TILE_WIDTH,
            20 * Tiles.TILE_WIDTH);
    Actions actions = new Actions();
    Booleans booleans = new Booleans();
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label quitLabel = new Label();
    Label actionLabel = new Label();
    Label pickUpInfo = new Label();
    boolean confirmQuit = false;
    Pane lineBreak = new Pane();
    Pane lineBreak2 = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        setUpUi(ui);

        BorderPane borderPane = new BorderPane();
        setUpBorderPane(ui, borderPane);

        Scene scene = new Scene(borderPane);
        setUpScene(primaryStage, scene);
    }

    private void setUpBorderPane(GridPane ui, BorderPane borderPane) {
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
    }

    private void setUpScene(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        int[] coordinates = MapLoader.getPlayerPosition();
        map = MapLoader.loadMap(coordinates[2]);
        refresh(coordinates[1], coordinates[0]);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void setUpUi(GridPane ui) {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        lineBreak.minHeightProperty().bind(inventoryLabel.heightProperty());
        lineBreak2.minHeightProperty().bind(inventoryLabel.heightProperty());
        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Action: "), 0, 1);
        ui.add(actionLabel, 1, 1);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 1, 2);
        ui.add(quitLabel, 0, 6, 2, 1);
        ui.add(lineBreak, 0, 3);
        ui.add(pickUpInfo, 0, 4, 2, 1);
        ui.add(lineBreak2, 0, 5);
        pickUpInfo.setText("Pick up items by pressing Enter while standing on the item.");
        pickUpInfo.setWrapText(true);
        quitLabel.setWrapText(true);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Actions actions = new Actions();
        ItemActions itemActions = new ItemActions();
        switch (keyEvent.getCode()) {
            case UP:
                actions.movement(Direction.NORTH.getX(), Direction.NORTH.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case DOWN:
                actions.movement(Direction.SOUTH.getX(), Direction.SOUTH.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case LEFT:
                actions.movement(Direction.WEST.getX(), Direction.WEST.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case RIGHT:
                actions.movement(Direction.EAST.getX(), Direction.EAST.getY(), map, actionLabel);
                actions.monsterInteractions(map);
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case Q:
                quitLabel.setText("Are you sure you want to quit? Y/N");
                confirmQuit = true;
                break;
            case ENTER:
                actions.pickUpItem(map);
                break;
            case Y:
                if (confirmQuit) {
                    System.exit(0);
                }
                break;
            case N:
                confirmQuit = false;
                quitLabel.setText("");
                break;
            case F:
                itemActions.consumeFood(map, "Bread");
                break;
            case P:
                itemActions.consumePotion(map, "Potion");
                break;
        }
    }



    private void refresh(int playerX, int playerY) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int diffX = (int) (canvas.getWidth() / (2 * Tiles.TILE_WIDTH));
        int diffY = (int) (canvas.getHeight() / (2 * Tiles.TILE_WIDTH));
        for (int x = 0; x < canvas.getWidth() && Math.max(playerX - diffX, 0) + x < map.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight() && Math.max(playerY - diffY, 0) + y < map.getHeight(); y++) {
                Cell cell = map.getCell(Math.max(playerX - diffX, 0) + x, Math.max(playerY - diffY, 0) + y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        StringBuilder output = new StringBuilder();
        for (Item item : playerInventory.keySet()) {
            String key = item.getName();
            String value = playerInventory.get(item).toString();
            output.append(" ").append(key).append(" ").append(value).append(" ").append("\n");
        }
        if (playerInventory.size() == 0) {
            inventoryLabel.setText("Empty");
        } else {
            inventoryLabel.setText("" + output);
        }
    }

}

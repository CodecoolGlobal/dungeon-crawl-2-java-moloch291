package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

public class Game extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Inventory: "), 0, 1);
        ui.add(inventoryLabel, 1, 1);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                int playerXUp = map.getPlayer().getX();
                int playerYUp = map.getPlayer().getY();
                if (map.getCell(playerXUp, playerYUp).getItem() != null) {
                    Item item = map.getCell(playerXUp, playerYUp).getItem();
                    map.getPlayer().setInventory(item, 1);
                    map.getCell(playerXUp, playerYUp).setItem(null);
                }
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                int playerXDown = map.getPlayer().getX();
                int playerYDown = map.getPlayer().getY();
                if (map.getCell(playerXDown, playerYDown).getItem() != null) {
                    Item item = map.getCell(playerXDown, playerYDown).getItem();
                    map.getPlayer().setInventory(item, 1);
                    map.getCell(playerXDown, playerYDown).setItem(null);
                }
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                int playerXLeft = map.getPlayer().getX();
                int playerYLeft = map.getPlayer().getY();
                if (map.getCell(playerXLeft, playerYLeft).getItem() != null) {
                    Item item = map.getCell(playerXLeft, playerYLeft).getItem();
                    map.getPlayer().setInventory(item, 1);
                    map.getCell(playerXLeft, playerYLeft).setItem(null);
                }
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                int playerXRight = map.getPlayer().getX();
                int playerYRight = map.getPlayer().getY();
                if (map.getCell(playerXRight, playerYRight).getItem() != null) {
                    Item item = map.getCell(playerXRight, playerYRight).getItem();
                    map.getPlayer().setInventory(item, 1);
                    map.getCell(playerXRight, playerYRight).setItem(null);
                }
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        StringBuilder output = new StringBuilder();
        for (Item item: playerInventory.keySet()) {
            String key = item.getName();
            String value = playerInventory.get(item).toString();
            output.append(" ").append(key).append(" ").append(value).append(" ");
        };
        inventoryLabel.setText("" + output);
    }
}

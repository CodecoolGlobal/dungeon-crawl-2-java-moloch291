package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Actions;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.Util;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.Y;

public class Game extends Application {

    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label quitLabel = new Label();
    Label actionLabel = new Label();

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
        ui.add(new Label("Action: "), 0, 1);
        ui.add(actionLabel, 1, 1);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 1, 2);
        ui.add(quitLabel, 0, 3);

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
        Actions actions = new Actions();
        switch (keyEvent.getCode()) {
            case UP:
                movement(0, -1);
                actions.pickUpItem(map);
                checkNearbyMonsters(map.getPlayer());
                moveMonsters();
                refresh();
                break;
            case DOWN:
                movement(0, 1);
                actions.pickUpItem(map);
                checkNearbyMonsters(map.getPlayer());
                moveMonsters();
                refresh();
                break;
            case LEFT:
                movement(-1, 0);
                actions.pickUpItem(map);
                checkNearbyMonsters(map.getPlayer());
                moveMonsters();
                refresh();
                break;
            case RIGHT:
                movement(1, 0);
                actions.pickUpItem(map);
                checkNearbyMonsters(map.getPlayer());
                moveMonsters();
                refresh();
                break;
            case Q:
                System.exit(0);
                break;
        }
    }
    private void moveMonsters(){
        for (Skeleton skeleton : map.getSkeletons()){
            skeleton.monsterMove(map.getPlayer().getCell());
        }
        for (Orc orc : map.getOrcs()){
            orc.monsterMove(map.getPlayer().getCell());
        }
    }

    private void movement(int moveInRow, int moveInColumn) {
        map.getPlayer().move(moveInRow, moveInColumn);
        lookForDoor();
    }

    private void lookForDoor() {
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        if (doorNextToPlayer(playerX, playerY) && map.getPlayer().hasKey())
            map.openDoor();
    }

    private boolean doorNextToPlayer(int playerX, int playerY) {
        boolean doorToTheLeft = map.getCell(playerX, playerY - 1).getType() == CellType.CLOSED_DOOR;
        boolean doorToTheRight = map.getCell(playerX, playerY + 1).getType() == CellType.CLOSED_DOOR;
        boolean doorBelow = map.getCell(playerX + 1, playerY).getType() == CellType.CLOSED_DOOR;
        boolean doorAbove = map.getCell(playerX - 1, playerY).getType() == CellType.CLOSED_DOOR;
        return doorToTheLeft || doorToTheRight || doorBelow || doorAbove;
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
            output.append(" ").append(key).append(" ").append(value).append(" ").append("\n");
        };
        if (playerInventory.size() == 0) {
            inventoryLabel.setText("Empty");
        } else {
            inventoryLabel.setText("" + output);
        }
    }

    public void checkNearbyMonsters(Actor player) {
        Cell cell = player.getCell();
        Cell nearbyCell = cell.getNeighbor(-1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player);
        }
        nearbyCell = cell.getNeighbor(1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player);
        }
        nearbyCell = cell.getNeighbor(0, -1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player);
        }
        nearbyCell = cell.getNeighbor(0, 1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player);
        }
    }

    private void fight(Cell nearbyCell, Actor player) {
        actionLabel.setText("");
        int playerAttack = player.getAttack();
        int playerDefense = player.getDefense();
        int playerHealth = 100;
        Actor enemy = nearbyCell.getActor();
        int enemyAttack = enemy.getAttack();
        int enemyDefense = enemy.getDefense();
        int enemyHealth = enemy.getHealth();
        while (true) {
            int playerHit = Util.getRandomNumber(playerAttack + 2, playerAttack - 1) - (enemyDefense / 2);
            enemyHealth -= playerHit;
            actionLabel.setText(actionLabel.getText() + "\nYou hit the enemy for " + playerHit);
            if (enemyHealth <= 0) {
                nearbyCell.setActor(null);
                actionLabel.setText(actionLabel.getText() + "\nYou killed the enemy!");
                player.setHealth(playerHealth);
                enemy.setHealth(enemyHealth);
                break;
            }
            int enemyHit = Util.getRandomNumber(enemyAttack + 2, enemyAttack - 1) - (playerDefense / 2);
            playerHealth -= enemyHit;
            actionLabel.setText(actionLabel.getText() + "\nThe enemy hit you for " + enemyHit);
            if (playerHealth <= 0) {
                player.getCell().setActor(null);
                actionLabel.setText(actionLabel.getText() + "\nYou died!");
                enemy.setHealth(enemyHealth);
                System.exit(0);
                break;
            }
        }
    }
}

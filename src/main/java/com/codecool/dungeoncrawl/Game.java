package com.codecool.dungeoncrawl;

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
        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Action: "), 0, 1);
        ui.add(actionLabel, 1, 1);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 1, 2);
        ui.add(quitLabel, 0, 3);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                movement(Direction.NORTH.getX(), Direction.NORTH.getY());
                moveMonsters();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case DOWN:
                movement(Direction.SOUTH.getX(), Direction.SOUTH.getY());
                moveMonsters();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case LEFT:
                movement(Direction.WEST.getX(), Direction.WEST.getY());
                moveMonsters();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case RIGHT:
                movement(Direction.EAST.getX(), Direction.EAST.getY());
                moveMonsters();
                refresh(map.getPlayer().getX(), map.getPlayer().getY());
                break;
            case Q:
                System.exit(0);
                break;
        }
    }

    private void moveMonsters() {
        moveSkeletons();
        moveOrcs();
    }

    private void moveOrcs() {
        for (Orc orc : map.getOrcs()) {
            orc.monsterMove(map.getPlayer().getCell());
        }
    }

    private void moveSkeletons() {
        for (Skeleton skeleton : map.getSkeletons()) {
            skeleton.monsterMove(map.getPlayer().getCell());
        }
    }

    private void movement(int moveInRow, int moveInColumn) {
        map.getPlayer().move(moveInRow, moveInColumn);
        lookForDoor();
        actions.pickUpItem(map);
        checkNearbyMonsters(map.getPlayer());
    }

    private void lookForDoor() {
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        if (doorNextToPlayer(playerX, playerY) && map.getPlayer().hasKey())
            map.openDoor();
    }

    private boolean doorNextToPlayer(int playerX, int playerY) {
        boolean doorToTheLeft = booleans.checkDoorInDirection(playerX, playerY, Direction.NORTH, map);
        boolean doorToTheRight = booleans.checkDoorInDirection(playerX, playerY, Direction.SOUTH, map);
        boolean doorBelow = booleans.checkDoorInDirection(playerX, playerY, Direction.EAST, map);
        boolean doorAbove = booleans.checkDoorInDirection(playerX, playerY, Direction.WEST, map);
        return doorToTheLeft || doorToTheRight || doorBelow || doorAbove;
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

    public void checkNearbyMonsters(Actor player) {
        Cell cell = player.getCell();
        checkForEnemies(player, cell, Direction.WEST);
        checkForEnemies(player, cell, Direction.EAST);
        checkForEnemies(player, cell, Direction.NORTH);
        checkForEnemies(player, cell, Direction.SOUTH);
    }

    private void checkForEnemies(Actor player, Cell playerCell, Direction currentDirection) {
        Cell nearbyCell = playerCell.getNeighbor(currentDirection.getX(), currentDirection.getY());
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

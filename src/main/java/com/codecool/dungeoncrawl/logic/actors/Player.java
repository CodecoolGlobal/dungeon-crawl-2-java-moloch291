package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.Direction;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {

    private final Map<Item, Integer> inventory = new HashMap<>();
    private boolean isDrunk = false;
    private String name;

    public Player(Cell cell) {
        super(cell);
        this.name = "testname";
    }

    @Override
    public void move(int dx, int dy) {
        boolean boatChecker = hasItem(ItemType.BOAT);
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        if (gameConditions.checkNextCellPlayer(nextCell, boatChecker)) {
            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTileName() {
        return "player";
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    public void addToInventory(Item item, Integer quantity) {
        inventory.put(item, quantity);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public void interactions(GameMap map, Label actionLabel) {
        lookForDoor(map);
        checkNearbyMonsters(actionLabel, map);
    }

    // Search for door on neighboring cells and open them if player has the key for it:
    private void lookForDoor(GameMap map) {
        int playerX = this.getCell().getX();
        int playerY = this.getCell().getY();
        if (gameConditions.doorNextToPlayer(playerX, playerY, map) && hasItem(ItemType.KEY)) {
            map.openDoor();
            removeFromInventory(inventory.keySet().stream()
                    .filter(item -> item.getItemType().equals(ItemType.KEY))
                    .findFirst().orElse(null));
        }
    }

    // Check neighboring fields for monsters:
    private void checkNearbyMonsters(Label actionLabel, GameMap map) {
        for (Direction direction : Direction.values()) checkForEnemies(direction, actionLabel, map);
    }

    // Initiate fight if finding a monster near by:
    private void checkForEnemies(Direction currentDirection, Label actionLabel, GameMap map) {
        Cell nearbyCell = this.getCell().getNeighbor(currentDirection.getX(), currentDirection.getY());
        if (gameConditions.isCellOccupied(nearbyCell))
            map.fight(nearbyCell, this, actionLabel);
    }

    public boolean hasItem(ItemType itemType) {
        for (Item item: inventory.keySet()) {
            if (item.getItemType().equals(itemType))
                return true;
        }
        return false;
    }

    public void pickUpItem(GameMap map) {
        int playerX = this.getCell().getX();
        int playerY = this.getCell().getY();
        if (gameConditions.isItemOnPlayerPosition(playerX, playerY, map)) {
            Item item = map.getCell(playerX, playerY).getItem();
            int addedQuantity = 1;
            if (!inventory.containsKey(item)) {
                equipArmorsAndWeapons(map, item, addedQuantity);
            } else map.getPlayer().addToInventory(item, inventory.get(item) + addedQuantity);
            map.getCell(playerX, playerY).setItem(null);
        }
    }

    private void equipArmorsAndWeapons(GameMap map, Item item, int addedQuantity) {
        map.getPlayer().addToInventory(item, addedQuantity);
        ItemActions itemActions = new ItemActions();
        if (gameConditions.checkIfArmor(item)) itemActions.equipArmor(map, item.getName());
        if (gameConditions.checkIfWeapon(item)) itemActions.equipWeapon(map, item.getName());
    }
}

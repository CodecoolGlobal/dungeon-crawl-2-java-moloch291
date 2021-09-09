package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.Direction;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player extends Actor {

    private final Map<Item, Integer> inventory = new HashMap<>();
    private boolean isDrunk = false;
    private final String name;

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
        if (gameConditions.doorNextToPlayer(this.getCell().getX(), this.getCell().getY(), map) &&
                hasItem(ItemType.KEY)) {
            map.openDoor();
            removeFromInventory(
                    inventory.keySet()
                    .stream()
                    .filter(item -> item.getItemType().equals(ItemType.KEY))
                    .findFirst().orElse(null)
            );
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
                equipGear(item, addedQuantity);
            } else map.getPlayer().addToInventory(item, inventory.get(item) + addedQuantity);
            map.getCell(playerX, playerY).setItem(null);
        }
    }

    private void equipGear(Item item, int addedQuantity) {
        addToInventory(item, addedQuantity);
        if (gameConditions.checkIfArmor(item)) equipArmor(item.getName());
        if (gameConditions.checkIfWeapon(item)) equipWeapon(item.getName());
    }

    public Item searchForPotion(PotionType potionType) {
        for (Item item : inventory.keySet()) {
            if (item instanceof Potion &&
                    ((Potion) item).getPotionType().equals(potionType))
                return item;
        }
        return null;
    }

    public void consumeFood() {
        int playerHealth = this.getHealth();
        int replenishHealth = 5;
        this.setHealth(playerHealth + replenishHealth);
        String itemName = Objects.requireNonNull(inventory.keySet()
                .stream()
                .filter(item -> item.getItemType().equals(ItemType.FOOD))
                .findFirst().orElse(null)).getName();
        decrementItem(itemName);
    }

    public void consumePotion(String itemName) {
        if (itemName.equals(StringFactory.HEALING_POTION.message)) {
            this.setHealth(this.getHealth() + PotionType.HEALING_POTION.effectValue);
        } else if (itemName.equals(StringFactory.STONE_SKIN_POTION.message)) {
            this.setDefense(this.getDefense() + PotionType.STONE_SKIN_POTION.effectValue);
        } else if (itemName.equals(StringFactory.MIGHT_POTION.message)) {
            this.setAttack(this.getAttack() + PotionType.MIGHT_POTION.effectValue);
        }
        decrementItem(itemName);
    }

    public void consumeAlcohol() {
        int defenseModifier = -5;
        int attackModifier = 3;
        setDrunk(true);
        if (this.getDefense() > 0)
            this.setDefense(this.getDefense() + defenseModifier);
        this.setAttack(this.getAttack() + attackModifier);
        decrementItem(StringFactory.BEER_CAP.message);
    }

    private void decrementItem(String itemName) {
        int count = 0;
        Item itemFromInventory = null;
        for (Item item : inventory.keySet()) {
            if (item.getName().equals(itemName)) {
                itemFromInventory = item;
                count = inventory.get(item);
            }
        }
        handleInventory(count, itemFromInventory);
    }

    private void handleInventory(int count, Item itemFromInventory) {
        if (count > 1) addToInventory(itemFromInventory, inventory.get(itemFromInventory) - 1);
        else removeFromInventory(itemFromInventory);
    }

    public void leaveBoat() {
        Item boat = null;
        for (Item item : inventory.keySet()) {
            if (item.getItemType().equals(ItemType.BOAT)) boat = item;
        }
        removeFromInventory(boat);
        this.getCell().setType(CellType.WATER);
        new Boat(StringFactory.BOAT_CAP.message, this.getCell());
    }

    public void equipArmor(String itemName) {
        int playerDefense = this.getDefense();
        if (itemName.equals(StringFactory.SHIELD_CAP.message)) {
            this.setDefense(playerDefense + ArmorType.SHIELD.defenseValue);
        } else if (itemName.equals(StringFactory.HELMET_CAP.message)) {
            this.setDefense(playerDefense + ArmorType.HELMET.defenseValue);
        } else if (itemName.equals(StringFactory.BREASTPLATE_CAP.message)) {
            this.setDefense(playerDefense + ArmorType.BREASTPLATE.defenseValue);
        } else if (itemName.equals(StringFactory.GREAVES_CAP.message)) {
            this.setDefense(playerDefense + ArmorType.GREAVES.defenseValue);
        } else if (itemName.equals(StringFactory.GAUNTLETS_CAP.message)) {
            this.setDefense(playerDefense + ArmorType.GAUNTLETS.defenseValue);
        }
    }

    public void equipWeapon(String itemName) {
        int playerAttack = this.getAttack();
        if (itemName.equals(StringFactory.SWORD_CAP.message)) {
            this.setAttack(playerAttack + WeaponType.SWORD.attackValue);
        } else if (itemName.equals(StringFactory.AXE_CAP.message)) {
            this.setAttack(playerAttack + WeaponType.AXE.attackValue);
        } else if (itemName.equals(StringFactory.PIKE_CAP.message)) {
            this.setAttack(playerAttack + WeaponType.PIKE.attackValue);
        }
    }
}

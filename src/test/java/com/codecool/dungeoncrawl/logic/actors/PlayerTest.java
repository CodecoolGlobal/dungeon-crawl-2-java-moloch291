package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    GameMap gameMap;
    Player player;
    Item alcohol;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(4, 4, CellType.FLOOR);
        player = new Player(gameMap.getCell(1,1));
    }

    @Test
    void getNameReturnRightName() {
        String expected = "testname";
        String result = player.getName();

        assertEquals(expected, result);
    }

    @Test
    void playerTileNameIsCorrect(){
        String correctName = "player";
        String playerName = player.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void playerInventoryIsHashMap(){
        Map<Item,Integer> hashMap = new HashMap<>();
        Map<Item,Integer> playerHashMap = player.getInventory();
        assertEquals(hashMap,playerHashMap);
    }

    @Test
    void playerIsNotDrunkONStart(){
        assertFalse(player.isDrunk());
    }

    @Test
    void setPlayerToDrunk(){
        player.setDrunk(true);
        assertTrue(player.isDrunk());
    }

    @Test
    void  addItemToInventory(){
        player.addToInventory(alcohol,1);
        assertTrue(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void removeFromInventory(){
        player.addToInventory(alcohol,1);
        player.removeFromInventory(alcohol);
        assertFalse(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void moveToNextCell(){
        player.move(0,1);
        assertEquals(player.getY(),2);
    }

    @Test
    void moveThrowsWithBadInput(){
        assertThrows(IndexOutOfBoundsException.class,()->player.move(0,99));
    }


    @Test
    void consumingFoodIncreasesPlayerHealth() {
        int healthAddedAfterEating = 5;
        int expected = player.getHealth() + healthAddedAfterEating;

        player.addToInventory(
                new Food(
                        StringFactory.FISH_CAP.message,
                        gameMap.getCell(0, 0),
                        FoodType.FISH
                ),
                1
        );
        player.consumeFood();
        int result = player.getHealth();

        assertEquals(expected, result);
    }

    @Test
    void consumingFoodThrowsExceptionWithoutFoodInInventory() {
        assertThrows(
                NullPointerException.class,
                () -> player.consumeFood()
        );
    }

    @Test
    void consumingHealingPotionIncreasesHealth() {
        int expected = player.getHealth() + PotionType.HEALING_POTION.effectValue;

        player.consumePotion(StringFactory.HEALING_POTION.message);
        int result = player.getHealth();

        assertEquals(expected, result);
    }

    @Test
    void consumingStoneSkinPotionIncreasesDefense() {
        int expected = player.getDefense() + PotionType.STONE_SKIN_POTION.effectValue;

        player.consumePotion(StringFactory.STONE_SKIN_POTION.message);
        int result = player.getDefense();

        assertEquals(expected, result);
    }

    @Test
    void consumingMightPotionIncreasesAttack() {
        int expected = player.getAttack() + PotionType.MIGHT_POTION.effectValue;

        player.consumePotion(StringFactory.MIGHT_POTION.message);
        int result = player.getAttack();

        assertEquals(expected, result);
    }

    @Test
    void consumptionDecrementsItemInInventory() {
        Food cheese = new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.CHEESE
        );
        player.addToInventory(cheese, 2);
        player.consumeFood();

        int expectedAmount = 1;
        int result = player.getInventory().get(cheese);

        assertEquals(expectedAmount, result);
    }

    @Test
    void consumptionOfSingleItemDeletesFromInventory() {
        Potion healingPotion = new Potion(
                StringFactory.HEALING_POTION.message,
                gameMap.getCell(0, 0),
                PotionType.HEALING_POTION
        );
        player.addToInventory(healingPotion, 1);
        player.consumePotion(StringFactory.HEALING_POTION.message);

        assertNull(player.getInventory().get(healingPotion));
    }

    @Test
    void consumingAlcoholResultsInBeingDrunk() {
        player.consumeAlcohol();

        assertTrue(player.isDrunk());
    }

    @Test
    void consumingAlcoholIncreasesPlayerAttack() {
        int expected = player.getAttack() + 3;

        player.consumeAlcohol();
        int result = player.getAttack();

        assertEquals(expected, result);
    }

    @Test
    void consumingAlcoholDecreasesPlayerDefense() {
        int expected = 0;
        player.setDefense(5);

        player.consumeAlcohol();
        int result = player.getDefense();

        assertEquals(expected, result);
    }

    @Test
    void leaveBoatLeavesBoatOnPlayerPosition() {
        String expected = StringFactory.BOAT.message;
        player.addToInventory(
                new Boat(
                        StringFactory.BOAT_CAP.message,
                        player.getCell()
                ),
                1
        );

        player.setCell(gameMap.getCell(1, 1));
        player.leaveBoat();
        String result = gameMap.getCell(1, 1)
                .getItem()
                .getTileName();

        assertEquals(expected, result);
    }

    @Test
    void equipArmorIncreasesDefenseOfPlayer() {
        int expected = player.getDefense() + ArmorType.SHIELD.defenseValue;

        player.equipArmor(StringFactory.SHIELD_CAP.message);
        int result = player.getDefense();

        assertEquals(expected, result);
    }

    @Test
    void equipWeaponIncreasesAttackOfPlayer() {
        int expected = player.getAttack() + WeaponType.AXE.attackValue;

        player.equipWeapon(StringFactory.AXE_CAP.message);
        int result = player.getAttack();

        assertEquals(expected, result);
    }

    @Test
    void pickUpItemSavesToInventory() {
        Food cheese = new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(1, 1),
                FoodType.CHEESE
                );
        player.pickUpItem(gameMap);

        assertTrue(player.getInventory().containsKey(cheese));
    }

    @Test
    void pickUpItemSavesToInventoryAndDecreasesAmount() {
        new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(1, 2),
                FoodType.CHEESE
        );
        new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(1, 3),
                FoodType.CHEESE
        );

        player.move(0, 1);
        player.pickUpItem(gameMap);
        player.move(0, 1);
        player.pickUpItem(gameMap);

        assertEquals(2, player.getInventory().size());
    }

    @Test
    void searchForPotionReturnsNullWithoutAddedPotion() {
        assertNull(player.searchForPotion(PotionType.HEALING_POTION));
    }

    @Test
    void searchForPotionReturnsRightPotionIfExists() {
        Potion expected = new Potion(
                StringFactory.HEALING_POTION.message,
                gameMap.getCell(1, 1),
                PotionType.HEALING_POTION
        );
        player.addToInventory(expected, 1);
        player.addToInventory(
                new Potion(
                        StringFactory.STONE_SKIN_POTION.message,
                        gameMap.getCell(1, 1),
                        PotionType.STONE_SKIN_POTION
                ),
                1
        );

        assertEquals(expected, player.searchForPotion(PotionType.HEALING_POTION));
    }

    @Test
    void keyDisappearsAfterOpeningDoor() {
        player.addToInventory(new Key(
                StringFactory.KEY_CAP.message,
                gameMap.getCell(1, 1),
                KeyType.DOOR_KEY
        ), 1);
        gameMap.getCell(1, 2).setType(CellType.CLOSED_DOOR);
        player.interactions(gameMap, null);

        assertFalse(player.hasItem(ItemType.KEY));
    }

    @Test
    void keyChangesClosedDoorToOpen() {
        player.addToInventory(new Key(
                StringFactory.KEY_CAP.message,
                gameMap.getCell(1, 1),
                KeyType.DOOR_KEY
        ), 1);
        gameMap.getCell(1, 2).setType(CellType.CLOSED_DOOR);
        player.interactions(gameMap, null);

        assertEquals(CellType.OPEN_DOOR, gameMap.getCell(1, 2).getType());
    }

    @AfterAll
    static void tearDown() {
        System.out.println("ItemActions tested:");
    }
}
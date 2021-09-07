package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemActionsTest {

    private GameMap gameMap;
    private Player player;
    private ItemActions itemActions;
    private final int amountToAdd = 1;

    @BeforeAll
    static void initTest() {
        System.out.println("Testing ItemActions methods...");
    }

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(0, 0));
        gameMap.setPlayer(player);
        itemActions = new ItemActions();
    }

    @Test
    void searchForItemByTypeWithSimilarTypes() {
        Food bread = new Food(
                StringFactory.BREAD_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.BREAD
                );
        Food cheese = new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.CHEESE
        );

        player.addToInventory(bread, amountToAdd);
        player.addToInventory(cheese, amountToAdd);

        assertEquals(cheese, itemActions.searchForItemByType(gameMap, ItemType.FOOD));
    }

    @Test
    void searchForItemByTypeWithDifferentTypes() {
        Weapon sword = new Weapon(
                StringFactory.SWORD_CAP.message,
                gameMap.getCell(0, 0),
                WeaponType.SWORD
        );
        Food cheese = new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.CHEESE
        );

        player.addToInventory(sword, amountToAdd);
        player.addToInventory(cheese, amountToAdd);

        assertEquals(cheese, itemActions.searchForItemByType(gameMap, ItemType.FOOD));
    }

    @Test
    void searchForItemByTypeWithNoneExistentType() {
        Weapon sword = new Weapon(
                StringFactory.SWORD_CAP.message,
                gameMap.getCell(0, 0),
                WeaponType.SWORD
        );
        Food cheese = new Food(
                StringFactory.CHEESE_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.CHEESE
        );

        player.addToInventory(sword, amountToAdd);
        player.addToInventory(cheese, amountToAdd);

        assertNull(itemActions.searchForItemByType(gameMap, ItemType.KEY));
    }

    @Test
    void consumingFoodIncreasesPlayerHealth() {
        int healthAddedAfterEating = 5;
        int expected = player.getHealth() + healthAddedAfterEating;

        itemActions.consumeFood(gameMap, StringFactory.CHEESE_CAP.message);
        int result = player.getHealth();

        assertEquals(expected, result);
    }

    @Test
    void consumingHealingPotionIncreasesHealth() {
        int expected = player.getHealth() + PotionType.HEALING_POTION.effectValue;

        itemActions.consumePotion(gameMap, StringFactory.HEALING_POTION.message);
        int result = player.getHealth();

        assertEquals(expected, result);
    }

    @Test
    void consumingStoneSkinPotionIncreasesDefense() {
        int expected = player.getDefense() + PotionType.STONE_SKIN_POTION.effectValue;

        itemActions.consumePotion(gameMap, StringFactory.STONE_SKIN_POTION.message);
        int result = player.getDefense();

        assertEquals(expected, result);
    }

    @Test
    void consumingMightPotionIncreasesAttack() {
        int expected = player.getAttack() + PotionType.MIGHT_POTION.effectValue;

        itemActions.consumePotion(gameMap, StringFactory.MIGHT_POTION.message);
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
        itemActions.consumeFood(gameMap, StringFactory.CHEESE_CAP.message);

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
        itemActions.consumePotion(gameMap, StringFactory.HEALING_POTION.message);

        assertNull(player.getInventory().get(healingPotion));
    }

    @Test
    void consumeAlcoholResultsInBeingDrunk() {
        itemActions.consumeAlcohol(gameMap, StringFactory.BREAD_CAP.message);

        assertTrue(player.isDrunk());
    }

    @Test
    void leaveBoat() {
    }

    @Test
    void equipArmor() {
    }

    @Test
    void equipWeapon() {
    }

    @AfterAll
    static void tearDown() {
        System.out.println("ItemActions tested:");
    }
}
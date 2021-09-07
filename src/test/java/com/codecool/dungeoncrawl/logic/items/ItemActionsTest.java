package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemActionsTest {

    private GameMap gameMap;
    private Player player;
    private ItemActions itemActions;

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

        player.addToInventory(bread, 1);
        player.addToInventory(cheese, 1);

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

        player.addToInventory(sword, 1);
        player.addToInventory(cheese, 1);

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

        player.addToInventory(sword, 1);
        player.addToInventory(cheese, 1);

        assertNull(itemActions.searchForItemByType(gameMap, ItemType.KEY));
    }

    @Test
    void consumeFood() {
    }

    @Test
    void consumePotion() {
    }

    @Test
    void consumeAlcohol() {
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
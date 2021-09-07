package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    public static GameMap gameMap;
    public static Food testFood;

    @BeforeAll
    static void setUp() {
        System.out.println("Food item tests started...");
    }

    @BeforeEach
    void setUpVariables() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        testFood = new Food(
                StringFactory.APPLE_CAP.message,
                gameMap.getCell(0, 0),
                FoodType.APPLE
        );
    }

    @Test
    void getTileName() {
        assertEquals(StringFactory.APPLE.message, testFood.getTileName());
    }

    @Test
    void negativeTestForGetTile() {
        assertNotEquals(StringFactory.FISH.message, testFood.getTileName());
    }

     @AfterAll
    static void tearDown() {
         System.out.println("Food tests done:");
     }
}
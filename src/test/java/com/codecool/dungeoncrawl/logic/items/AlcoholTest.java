package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AlcoholTest {

    public static GameMap gameMap;
    public static Alcohol testBeer;

    @BeforeAll
    static void setUpMessage() {
        System.out.println("Alcohol item test started...");
    }

    @BeforeEach
    void beforeAll() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        testBeer = new Alcohol(
                gameMap.getCell(0, 0)
        );
    }

    @Test
    void getTileNameWithNonCapitalLetter() {
        assertEquals("beer", testBeer.getTileName());
    }

    @Test
    void getTileNameWithCapitalLetter() {
        assertNotEquals("Beer", testBeer.getTileName());
    }

    @AfterAll
    static void tearDownMessage() {
        System.out.println("Alcohol class tests finished:");
    }
}
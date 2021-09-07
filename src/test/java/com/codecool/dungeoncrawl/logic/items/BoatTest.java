package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    private final GameMap gameMap = new GameMap(3, 3, CellType.WATER);
    private final Boat testBoat = new Boat(StringFactory.BOAT_CAP.message, gameMap.getCell(0, 0));

    @BeforeAll
    static void setUp() {
        System.out.println("Testing the boat...");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("The boat is tested:");
    }

    @Test
    void getTileName() {
        assertEquals(StringFactory.BOAT.message, testBoat.getTileName());
    }

    @Test
    void getTileNameWithoutMatch() {
        assertNotEquals(StringFactory.CHEESE.message, testBoat.getTileName());
    }
}
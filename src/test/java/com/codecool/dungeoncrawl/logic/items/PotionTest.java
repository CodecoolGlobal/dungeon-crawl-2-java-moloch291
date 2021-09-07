package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PotionTest {

    private final GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    private final Potion testPotion = new Potion(
            StringFactory.HEALING_POTION.message,
            gameMap.getCell(0, 0),
            PotionType.HEALING_POTION
    );

    @BeforeAll
    static void initTest() {
        System.out.println("Testing potion items...");
    }

    @Test
    void getTileNameWithMatch() {
        assertEquals("healing potion", testPotion.getTileName());
    }

    @Test
    void getTileNameWithoutMatch() {
        assertNotEquals("stone skin potion", testPotion.getTileName());
    }

    @Test
    void testConstructorWithNullType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Potion(
                        StringFactory.HEALING_POTION.message,
                        gameMap.getCell(0, 1),
                        null
                )
        );
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Potions tested:");
    }
}
package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    private static GameMap gameMap;
    private Key testKey;

    @BeforeAll
    static void initTest() {
        System.out.println("Keys under test...");
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @BeforeEach
    void setUp() {
        testKey = new Key(
                StringFactory.KEY_CAP.message,
                gameMap.getCell(0, 0),
                KeyType.DOOR_KEY
                );
    }

    @Test
    void getTileNameWithMatch() {
        assertEquals(StringFactory.KEY.message, testKey.getTileName());
    }

    @Test
    void getTileNameWithoutMatch() {
        assertNotEquals(StringFactory.KEY_CAP.message, testKey.getTileName());
    }

    @Test
    void testConstructorWithNullType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Key(
                        StringFactory.KEY_CAP.message,
                        gameMap.getCell(0, 2),
                        null
                )
        );
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Keys tested:");
    }
}
package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    private Weapon testWeapon;
    private GameMap gameMap;

    @BeforeAll
    static void initTest() {
        System.out.println("Testing Weapon class...");
    }

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        testWeapon = new Weapon(
                StringFactory.SWORD_CAP.message,
                gameMap.getCell(0, 0),
                WeaponType.SWORD
                );
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Weapons tested:");
    }

    @Test
    void getTileNameWithMatch() {
        assertEquals(StringFactory.SWORD.message, testWeapon.getTileName());
    }

    @Test
    void getTileNameWithoutMatch() {
        assertNotEquals(StringFactory.SWORD_CAP.message, testWeapon.getTileName());
    }

    @Test
    void testGetTileNameWithNullWeaponType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Weapon(
                        StringFactory.SWORD_CAP.message,
                        gameMap.getCell(0, 1),
                        null
                        )
                );
    }
}
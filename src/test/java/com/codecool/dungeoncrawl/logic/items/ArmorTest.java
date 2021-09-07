package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ArmorTest {

    private Armor testArmor1;
    private Armor testArmor2;

    @BeforeAll
    static void initTest() {
        System.out.println("Start testing Armor class...");
    }

    @BeforeEach
    void setUp() {
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        testArmor1 = new Armor(
                StringFactory.HELMET_CAP.message,
                gameMap.getCell(0, 0),
                ArmorType.HELMET
        );
        testArmor2 = new Armor(
                StringFactory.GAUNTLETS_CAP.message,
                gameMap.getCell(0, 1),
                ArmorType.GAUNTLETS
        );
    }

    @Test
    void getTileNameWithMatch() {
        assertEquals(StringFactory.GAUNTLETS.message, testArmor2.getTileName());
    }

    @Test
    void getTileNameWithoutMatch() {
        assertNotEquals(StringFactory.GAUNTLETS.message, testArmor1.getTileName());
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Armor tests finished:");
    }
}
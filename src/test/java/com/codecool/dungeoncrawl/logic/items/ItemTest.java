package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    public static GameMap gameMap;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Abstract Item tests started...");
    }

    @BeforeEach
    public void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getName() {
        Key testKey = new Key(
                "Key",
                gameMap.getCell(0, 1),
                ItemType.KEY,
                KeyType.DOOR_KEY
        );

        assertEquals("Key", testKey.getName());
    }

    @Test
    void setName() {
        Alcohol testBeer = new Alcohol(
                gameMap.getCell(0, 1)
        );
        String newName = "This is a beer!";

        testBeer.setName(newName);

        assertEquals(newName, testBeer.getName());
    }

    @Test
    void setNameWithNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Food(
                        null,
                        gameMap.getCell(0, 1),
                        FoodType.BREAD
                )
            );
    }

    @Test
    void getCell() {
        Cell testCell = gameMap.getCell(0, 1);
        Potion testPotion = new Potion(
                StringFactory.HEALING_POTION.message,
                testCell,
                PotionType.HEALING_POTION
        );

        assertEquals(testCell, testPotion.getCell());
    }

    @Test
    void setCellWithNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Food(
                        StringFactory.FISH.message,
                        null,
                        FoodType.FISH
                )
        );
    }

    @Test
    void setCell() {
        Armor testArmor = new Armor(
                "Breastplate",
                gameMap.getCell(0, 1),
                ItemType.ARMOR,
                ArmorType.BREASTPLATE
        );

        Cell newCell = gameMap.getCell(1, 1);
        testArmor.setCell(newCell);

        assertEquals(newCell, testArmor.getCell());
    }

    @Test
    void getItemType() {
        Weapon testWeapon = new Weapon(
                "Axe",
                gameMap.getCell(0, 1),
                ItemType.WEAPON,
                WeaponType.AXE
        );

        assertEquals(ItemType.WEAPON, testWeapon.getItemType());
    }

    @Test
    void setItemType() {
        Boat testBoat = new Boat(
                "Boat",
                gameMap.getCell(0, 1),
                ItemType.BOAT
        );
        testBoat.setItemType(ItemType.FOOD);

        assertEquals(ItemType.FOOD, testBoat.getItemType());
    }

    @Test
    void setSubclassTypeAsNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Food(
                        StringFactory.APPLE.message,
                        gameMap.getCell(0, 0),
                        null
                )
        );
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Abstract Item test finished!");
    }
}
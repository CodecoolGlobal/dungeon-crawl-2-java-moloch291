package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    public static GameMap gameMap;
    public static Item mockItem;
    public static String mockName = "mock";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Abstract Item tests started...");
    }

    @BeforeEach
    public void setUp() {
        // Test map:
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        mockItem = Mockito.spy(new Item(
                mockName,
                new Cell(gameMap, 0, 0, CellType.FLOOR),
                ItemType.FOOD
        ) { // Override method implementation:
            @Override
            public String getTileName() {
                return null;
            }
        });
    }

    @Test
    void getName() {
        // Assert getName abstract method:
        Mockito.when(mockItem.getName()).thenReturn(mockName);
    }

    @Test
    void constructorSetNameWithNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Mockito.spy(new Item(
                        null,
                        new Cell(gameMap, 0, 0, CellType.FLOOR),
                        ItemType.FOOD
                ) {
                    @Override
                    public String getTileName() {
                        return null;
                    }
                }));
    }

    @Test
    void getCell() {
        Cell testCell = gameMap.getCell(0, 0);
        // Assert abstractGetCell:
        Mockito.when(mockItem.getCell()).thenReturn(testCell);
    }

    @Test
    void constructorSetCellWithNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Mockito.spy(new Item(
                        mockName,
                        null,
                        ItemType.FOOD
                ) {
                    @Override
                    public String getTileName() {
                        return null;
                    }
                }));
    }

    @Test
    void setCell() {
        Cell newCell = gameMap.getCell(1, 1);

        mockItem.setCell(newCell);
        assertEquals(newCell, mockItem.getCell());
    }

    @Test
    void getItemType() {
        ItemType expected = ItemType.FOOD;

        Mockito.when(mockItem.getItemType()).thenReturn(expected);
    }

    @Test
    void constructorSetSubclassTypeAsNullVariable() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Mockito.spy(new Item(
                        mockName,
                        new Cell(gameMap, 0, 0, CellType.FLOOR),
                        null
                ) {
                    @Override
                    public String getTileName() {
                        return null;
                    }
                }));
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Abstract Item test finished!");
    }
}
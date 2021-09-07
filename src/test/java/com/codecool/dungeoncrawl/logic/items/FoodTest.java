package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    public static GameMap gameMap;
    public static Food testFood;
    public static int typeIndex;
    public static ArrayList<FoodType> foodTypes;

    @BeforeAll
    static void setUp() {
        System.out.println("Food item tests started...");
        typeIndex = 0;
        foodTypes = new ArrayList<>(Arrays.asList(FoodType.values()));
        System.out.println(foodTypes);
    }

    @BeforeEach
    void setUpVariables() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getTileName() {
    }
}
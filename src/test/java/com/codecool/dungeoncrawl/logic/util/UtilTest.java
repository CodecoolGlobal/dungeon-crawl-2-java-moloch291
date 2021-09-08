package com.codecool.dungeoncrawl.logic.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    @BeforeAll
    static void initTest() {
        System.out.println("Begin testing util methods...");
    }

    @Test
    void getAttackerHit() {
    }

    @Test
    void exitGame() {
    }

    @Test
    void getRandomNumber() {
        int[] testNumbers = new int[10];
        for (int index = 0; index < testNumbers.length; index++) {
            testNumbers[index] = Util.getRandomNumber(0, 100);

        }
    }

    @Test
    void getRandomTile() {
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Util tests finished:");
    }
}
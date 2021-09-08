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
    void getRandomNumberOnlyInRange() {
        int[] randomNumbers = getRandomNumbers();
        for (int randomNumber : randomNumbers) {
            System.out.println("Testing " + randomNumber + "...");
            assertTrue(inRange(randomNumber));
        }
    }

    @Test
    void getRandomTile() {
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Util tests finished:");
    }



    private boolean inRange(int randomNumber) {
        return randomNumber >= 0 && randomNumber <= 100;
    }

    private int[] getRandomNumbers() {
        int min = 0;
        int max = 100;
        int[] testNumbers = new int[10];
        for (int index = 0; index < testNumbers.length; index++)
            testNumbers[index] = Util.getRandomNumber(min, max);
        return testNumbers;
    }
}
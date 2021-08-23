package com.codecool.dungeoncrawl.logic;

import java.util.concurrent.TimeUnit;

public class Util {

    public static void waitOneSec() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            System.out.println("Can not wait for some reason, Mr. Developer");
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

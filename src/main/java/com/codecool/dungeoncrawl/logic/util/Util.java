package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.util.concurrent.TimeUnit;

public class Util {
    private final String[] redbrickRefs = {"redbrick", "redbrick alter"};
    private final String[] houseRefs = {"house", "house alter", "house alter 2", "house alter 3"};
    private final String[] lakeHouseRefs = {"lake house", "lake house alter"};
    

    public static void waitOneSec() {
        try {
            Thread.sleep(NumberParameters.WAIT_TIME.getValue());
        } catch (Exception e) {
            System.out.println("Can not wait for some reason, Mr. Developer");
        }
    }

    public int getAttackerHit(Actor attacker, Actor defender) {
        return getRandomNumber(
                attacker.getAttack() + NumberParameters.ATTACK_BONUS.getValue(),
                attacker.getAttack() - NumberParameters.ATTACK_NERF.getValue()
        ) - (defender.getDefense() / NumberParameters.DEFENSE_DIVISOR.getValue());
    }

    public void exitGame() {
        System.exit(0);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public String getRandomTile(String tileName, GameMap map) {
        String actualTileName = tileName;
        if (map.getPlayer().isDrunk()) {
            switch (tileName) {
                case "redbrick":
                    actualTileName = redbrickRefs[getRandomNumber(0, 2)];
                    break;
                case "house":
                    actualTileName = houseRefs[getRandomNumber(0, 4)];
                    break;
                case "lake house":
                    actualTileName = lakeHouseRefs[getRandomNumber(0, 2)];
                    break;
            }
            return actualTileName;
        }
        return tileName;
    }
}

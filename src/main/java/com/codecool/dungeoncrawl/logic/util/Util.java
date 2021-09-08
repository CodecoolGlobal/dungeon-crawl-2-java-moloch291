package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.io.Serializable;

public class Util implements Serializable {
    private final String[] redbrickRefs = {"redbrick", "redbrick alter"};
    private final String[] houseRefs = {"house", "house alter", "house alter 2", "house alter 3"};
    private final String[] lakeHouseRefs = {"lake house", "lake house alter"};
    private final String gameManual = "Up/Down/Left/Right arrows - Movement\n" +
            "Enter - Pick up items while standing on them or embark ship\n" +
            "F - Use food (Replenishes 5 health points)\n" +
            "H - Use Healing potion (Replenishes 20 health points)\n" +
            "G - Use Stone skin potion (Adds 5 defense)\n" +
            "J - Use Potion of might (Adds 5 attack)\n" +
            "B - Disembark ship\n" +
            "A - Use alcohol\n" +
            "S - Save menu\n" +
            "L - Load menu\n" +
            "M - Export/Import menu\n" +
            "Q - Quit game";

    public String getGameManual() {
        return gameManual;
    }

    public static int getAttackerHit(Actor attacker, Actor defender) {
        return getRandomNumber(
                attacker.getAttack() + NumberParameters.ATTACK_BONUS.getValue(),
                attacker.getAttack() - NumberParameters.ATTACK_NERF.getValue()
        ) - (defender.getDefense() / NumberParameters.DEFENSE_DIVISOR.getValue());
    }

    public static void exitGame() {
        System.exit(0);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }


    public static String getRandomTile(String tileName, GameMap map) {
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

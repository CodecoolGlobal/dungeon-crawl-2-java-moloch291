package com.codecool.dungeoncrawl.logic.util;

public enum NumberParameters {

    TILE_WIDTH_MULTIPLIER_V(30),
    TILE_WIDTH_MULTIPLIER_V1(20),
    ATTACK_BONUS(2),
    ATTACK_NERF(1),
    DEFENSE_DIVISOR(2);


    private final int value;

    NumberParameters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

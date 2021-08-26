package com.codecool.dungeoncrawl.logic.util;

public enum NumberParameters {

    // Used in Game for instancing new Canvas:
    TILE_WIDTH_MULTIPLIER_V(30),
    TILE_WIDTH_MULTIPLIER_V1(20),
    TILE_WIDTH_MULTIPLIER(2),

    // Used in Util/getAttackerHit for calculating attack values:
    ATTACK_BONUS(2),
    ATTACK_NERF(1),
    DEFENSE_DIVISOR(2),
    // Used Kraken as Health setter:
    KRAKEN_HEALTH(1000000);

    private final int value;

    NumberParameters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

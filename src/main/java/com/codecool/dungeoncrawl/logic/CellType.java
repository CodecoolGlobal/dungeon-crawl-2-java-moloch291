package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    DOOR("door"),
    BRICK_WALL("redbrick"),
    WATER("water"),
    HOUSE("house"),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

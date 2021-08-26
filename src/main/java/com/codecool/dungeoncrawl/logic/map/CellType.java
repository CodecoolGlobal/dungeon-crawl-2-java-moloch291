package com.codecool.dungeoncrawl.logic.map;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    BRICK_WALL("redbrick"),
    WATER("water"),
    HOUSE("house"),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door"),
    FAKE_DOOR("fake door");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

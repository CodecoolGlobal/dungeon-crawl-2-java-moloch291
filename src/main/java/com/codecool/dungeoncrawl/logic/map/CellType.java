package com.codecool.dungeoncrawl.logic.map;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    FLOOR2("floor second"),
    WALL("wall"),
    BRICK_WALL("redbrick"),
    WATER("water"),
    HOUSE("house"),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door"),
    FAKE_DOOR("fake door"),
    RIVER("river"),
    RAMP_START("ramp start"),
    RAMP_MIDDLE("ramp middle"),
    RAMP_END("ramp end"),
    TORCH("torch"),
    LADDER("ladder"),
    LADDER_UPPER("ladder upper"),
    LAKE_HOUSE("lake house"),
    TREE("tree"),
    WEED("weed"),
    MOSS("moss"),
    ROAD_SIGN("road sign"),
    CAMPFIRE("campfire"),
    CAULDRON("cauldron"),
    ROCKS("rocks"),
    TENT("tent");



    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

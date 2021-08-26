package com.codecool.dungeoncrawl.logic.map;

public enum MapName {
    MAP1("/map1.txt"),
    MAP2("/map2.txt"),
    MAP3("/map3.txt"),
    MAP4("/map4.txt"),
    MAP5("/map5.txt"),
    DEAD("/dead.txt");

    private String mapName;

    public String getMapName() {
        return mapName;
    }

    MapName(String mapName) {
        this.mapName = mapName;
    }
}

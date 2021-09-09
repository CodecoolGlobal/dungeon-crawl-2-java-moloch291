package com.codecool.dungeoncrawl.model;

public class GameState extends BaseModel {
    private final String savedAt;
    private final String currentMap;
    private PlayerModel player;

    public GameState(String currentMap, String savedAt, PlayerModel player) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
    }

    public String getSavedAt() {
        return savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}

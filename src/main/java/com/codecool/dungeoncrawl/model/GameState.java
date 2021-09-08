package com.codecool.dungeoncrawl.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private LocalDateTime savedAt;
    private String currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;

    public GameState(String currentMap, Date savedAt, PlayerModel player) {
        this.currentMap = currentMap;
        this.savedAt = LocalDateTime.now();
        this.player = player;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}

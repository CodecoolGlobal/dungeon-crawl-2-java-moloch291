package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state, String saveName);
    void update(GameState state, String saveName);
    GameState get(int id);
    List<String> getAll();
    boolean checkSaveName(String saveName);
    int getPlayerId(String saveName);
}

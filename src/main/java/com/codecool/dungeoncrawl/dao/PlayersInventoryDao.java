package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayersInventory;

import java.util.List;

public interface PlayersInventoryDao {
    void add(PlayersInventory playersInventory);
    void update(PlayersInventory playersInventory);
    PlayersInventory get(int id);
    List<PlayersInventory> getAll();
}

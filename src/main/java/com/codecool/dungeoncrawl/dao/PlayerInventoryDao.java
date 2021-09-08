package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.PlayerInventory;

import java.util.List;

public interface PlayerInventoryDao {
    void add(PlayerInventory playerInventory);
    void update(PlayerInventory playerInventory);
    int get(String currentItemName);
    List<PlayerInventory> getAll(int player_id);
}
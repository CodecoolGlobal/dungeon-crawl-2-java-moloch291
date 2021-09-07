package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayersInventory;

import javax.sql.DataSource;
import java.util.List;

public class PlayersInventoryDaoJdbc implements PlayersInventoryDao{
    private DataSource dataSource;

    public PlayersInventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayersInventory playersInventory) {

    }

    @Override
    public void update(PlayersInventory playersInventory) {

    }

    @Override
    public PlayersInventory get(int id) {
        return null;
    }

    @Override
    public List<PlayersInventory> getAll() {
        return null;
    }
}

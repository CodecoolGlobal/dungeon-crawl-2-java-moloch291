package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerInventory;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private PlayerInventoryDao playerInventoryDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao); //should it be separated
        playerInventoryDao = new PlayerInventoryDaoJdbc(dataSource, playerDao);
    }

    public PlayerModel savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
        return model;
    }

    public void saveGameState(String currentMap, String savedAt, PlayerModel player) {
        GameState model = new GameState(currentMap, savedAt, player);
        gameStateDao.add(model);
    }

    public void savePlayerInventory(int playerId, Map<Item, Integer> inventory) {
        PlayerInventory model = new PlayerInventory(playerId, inventory);
        playerInventoryDao.add(model);
    }

    public PlayerModel updatePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.update(model);
        return model;
    }

    public void updateGameState(String currentMap, String savedAt, PlayerModel player) {
        GameState model = new GameState(currentMap, savedAt, player);
        gameStateDao.update(model);
    }

    public void updatePlayerInventory(int playerId, Map<Item, Integer> inventory) {
        PlayerInventory model = new PlayerInventory(playerId, inventory);
        playerInventoryDao.update(model);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}

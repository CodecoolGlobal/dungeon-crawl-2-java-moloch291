package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerInventory;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerInventoryDaoJdbc implements PlayerInventoryDao{

    private final DataSource dataSource;
    private final PlayerDao playerDao;

    public PlayerInventoryDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.playerDao = playerDao;
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerInventory playerInventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO players_inventory (player_id, item_id, amount) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, playerInventory.getPlayerId());
            st.setInt(2, playerInventory.getItemId());
            st.setInt(3, playerInventory.getAmount());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            playerInventory.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(PlayerInventory playerInventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE players_inventory SET player_id = ?, item_id = ?, amount = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, playerInventory.getPlayerId());
            st.setInt(2, playerInventory.getItemId());
            st.setInt(3, playerInventory.getAmount());
            st.setInt(4, playerInventory.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerInventory get(int player_id) {
        /*
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM players_inventory WHERE player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, player_id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int id = rs.getInt(1);

            int playerId = rs.getInt(1);
            PlayerModel playerModel = playerDao.get(id);

            // FINISH - create and return new Book class instance
            PlayerInventory playerInventory = new PlayerInventory(id);
            playerInventory.setId(id);
            return
            gameState.setId(id);
            return gameState;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
           */
        return new PlayerInventory(1, 1, 1, "asd");
    }



    @Override
    public List<PlayerInventory> getAll(int player_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT players_inventory.item_id, " +
                    "            players_inventory.amount," +
                    "            inventory_handler.item_name FROM players_inventory" +
                    "     FULL OUTER JOIN inventory_handler" +
                    "     ON players_inventory.item_id = inventory_handler.id" +
                    "     WHERE players_inventory.player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, player_id);
            ResultSet rs = st.executeQuery();

            List<PlayerInventory> result = new ArrayList<>();
            while (rs.next()) {
                int itemId = rs.getInt(1);
                int amount = rs.getInt(2);
                String itemName = rs.getString(3);

                PlayerModel playerModel = playerDao.get(player_id);

                PlayerInventory playerInventory = new PlayerInventory(player_id, itemId, amount, itemName);
                playerInventory.setPlayerId(player_id);
                result.add(playerInventory);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

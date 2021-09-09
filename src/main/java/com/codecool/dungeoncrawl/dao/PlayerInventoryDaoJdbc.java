package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerInventory;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            Map<Item, Integer> inventory = playerInventory.getInventory();
            for (Item item: inventory.keySet()) {
                int currentItemAmount = inventory.get(item);
                int itemId = get(item.getName());
                addInventory(playerInventory, conn, itemId, currentItemAmount);
            }
        } catch (SQLException throwable) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(PlayerInventory playerInventory) {
        try (Connection conn = dataSource.getConnection()) {
            Map<Item, Integer> inventory = playerInventory.getInventory();
            for (Item item : inventory.keySet()) {
                int currentItemId = get(item.getName());
                int currentItemAmount = inventory.get(item);
                if (checkItemIfInventory(1, currentItemId) == 0) {
                    addInventory(playerInventory, conn, currentItemId, currentItemAmount);
                } else {
                    String sql = "UPDATE players_inventory SET amount = ? WHERE player_id = ? AND item_id = ?";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setInt(1, currentItemAmount);
                    st.setInt(2, 1);
                    st.setInt(3, currentItemId);
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInventory(PlayerInventory playerInventory, Connection conn, int currentItemId, int currentItemAmount) throws SQLException {
        String sql = "INSERT INTO players_inventory (player_id, item_id, amount) VALUES (?, ?, ?)";
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, playerInventory.getPlayerId());
        st.setInt(2, currentItemId);
        st.setInt(3, currentItemAmount);
        st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        playerInventory.setId(rs.getInt(1));
    }

    public int checkItemIfInventory(int playerId, int itemId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM players_inventory WHERE item_id = ? AND player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, itemId);
            st.setInt(2, playerId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int get(String currentItemName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM inventory_handler WHERE item_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, currentItemName);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<PlayerInventory> getAll(int playerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT players_inventory.item_id, " +
                    "            players_inventory.amount," +
                    "            inventory_handler.item_name FROM players_inventory" +
                    "     FULL OUTER JOIN inventory_handler" +
                    "     ON players_inventory.item_id = inventory_handler.id" +
                    "     WHERE players_inventory.player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, playerId);
            ResultSet rs = st.executeQuery();

            List<PlayerInventory> result = new ArrayList<>();
            while (rs.next()) {
                int itemId = rs.getInt(1);
                int amount = rs.getInt(2);
                String itemName = rs.getString(3);

                PlayerModel playerModel = playerDao.get(playerId);

                PlayerInventory playerInventory = new PlayerInventory(playerId, itemId, amount, itemName);
                playerInventory.setPlayerId(playerId);
                result.add(playerInventory);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

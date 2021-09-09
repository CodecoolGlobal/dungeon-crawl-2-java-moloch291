package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.playerDao = playerDao;
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state, String saveName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (save_name, current_map, saved_at, player_id) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, saveName);
            st.setString(2, state.getCurrentMap());
            st.setString(3, state.getSavedAt());
            st.setInt(4, state.getPlayer().getId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            state.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(GameState state, String saveName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET current_map = ?, saved_at = ? WHERE save_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, state.getCurrentMap());
            st.setString(2, state.getSavedAt());
            st.setString(3, saveName);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int playerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, current_map, saved_at FROM game_state WHERE player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, playerId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String currentMap = rs.getString(2);
            String savedAt = rs.getString(3);

            PlayerModel playerModel = playerDao.get(playerId);

            // FINISH - create and return new Book class instance
            GameState gameState = new GameState(currentMap, savedAt, playerModel);

            gameState.setId(rs.getInt(1));
            return gameState;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT save_name FROM game_state";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<String> result = new ArrayList<>();

            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }

    }

    @Override
    public boolean checkSaveName(String saveName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT save_name FROM game_state WHERE save_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, saveName);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


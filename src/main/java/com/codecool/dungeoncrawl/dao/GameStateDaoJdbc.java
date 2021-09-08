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
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, state.getCurrentMap());
            st.setDate(2, state.getSavedAt());
            st.setInt(3, state.getPlayer().getId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            state.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException();
        }
    }

        @Override
        public void update(GameState state) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "UPDATE game_state SET current_map = ?, saved_at = ?, player_id = ? WHERE id = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, state.getCurrentMap());
                st.setDate(2, state.getSavedAt());
                st.setInt(3, state.getPlayer().getId());
                st.setInt(4, state.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public GameState get(int id) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT current_map, saved_at, player_id FROM game_sate WHERE id = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                String currentMap = rs.getString(1);
                java.sql.Date savedAt = rs.getDate(2);

                int playerId = rs.getInt(3);
                PlayerModel playerModel = playerDao.get(playerId);

                // FINISH - create and return new Book class instance
                GameState gameState = new GameState(currentMap, savedAt, playerModel);
                gameState.setId(id);
                return gameState;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<GameState> getAll() {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT id, current_map, saved_at, player_id FROM game_state";
                ResultSet rs = conn.createStatement().executeQuery(sql);

                List<GameState> result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String currentMap = rs.getString(2);
                    java.sql.Date savedAt = rs.getDate(3);
                    int playerId = rs.getInt(4);

                    PlayerModel playerModel = playerDao.get(playerId);


                    GameState gameState = new GameState(currentMap, savedAt, playerModel);
                    gameState.setId(id);
                    result.add(gameState);
                }
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}


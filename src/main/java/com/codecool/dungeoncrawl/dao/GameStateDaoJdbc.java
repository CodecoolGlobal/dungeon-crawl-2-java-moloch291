package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
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
            return null;
        }

        @Override
        public List<GameState> getAll() {
            return null;
        }
}


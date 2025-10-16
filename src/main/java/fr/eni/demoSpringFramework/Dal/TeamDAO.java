package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamDAO implements ITeamDAO {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Team> findAllTeams() {
        return jdbcTemplate.query("SELECT id, name FROM TEAM ORDER BY id", new TeamRowMapper());
    }

    @Override
    public Optional<Team> findTeamByName(String name) {
        String sql = "SELECT id, name FROM TEAM WHERE name=:name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return findOneBy(sql, params);
    }

    @Override
    public Optional<Team> findTeamById(int id) {
        String sql = "SELECT id, name FROM TEAM WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return findOneBy(sql, params);
    }

    @Override
    public int insertOne(TeamDTO teamDTO) throws SqlException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = (new MapSqlParameterSource()).addValue("name", teamDTO.name());

        namedParameterJdbcTemplate.update(
                "INSERT INTO TEAM (name) VALUES (:name)",
                params,
                keyHolder,
                new String[]{"id"}
        );

        Number key = keyHolder.getKey();

        if (key != null) {
            return key.intValue();
        }

        throw new SqlException("Insert Error");
    }

    @Override
    public boolean deleteOne(int id) {
        String sql = "DELETE FROM TEAM WHERE id=?";

        return jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt;
        }) == 1;
    }

    private Optional<Team> findOneBy(String sql, MapSqlParameterSource params) {
        try {
            Team team = namedParameterJdbcTemplate.queryForObject(sql, params, new TeamRowMapper());

            return Optional.ofNullable(team);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    static class TeamRowMapper implements RowMapper<Team> {

        @Override
        public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
            Team team = new Team();
            team.setId(rs.getInt("id"));
            team.setName(rs.getString("name"));

            return team;
        }

    }
}

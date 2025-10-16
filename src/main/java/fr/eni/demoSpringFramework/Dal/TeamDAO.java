package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Do.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return jdbcTemplate.query("SELECT id, name FROM TEAM", new TeamRowMapper());
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

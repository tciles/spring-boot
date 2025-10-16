package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayerDAO implements IPlayerDAO {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayerDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Player> findOneById(int id) {
        String sql = """
            SELECT p.id, p.firstname, p.lastname, p.email, p.team_id, t.name AS team_name
            FROM PLAYER p
            INNER JOIN TEAM t ON t.id = p.team_id
            WHERE p.id = :id
            ORDER BY p.id
            """;

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        try {
            Player player = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, new PlayerRowMapper());

            return Optional.ofNullable(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Player> findAll() {
        String sql = """
            SELECT p.id, p.firstname, p.lastname, p.email, p.team_id, t.name AS team_name
            FROM PLAYER p
            INNER JOIN TEAM t ON t.id = p.team_id
            ORDER BY p.id
            """;

        return jdbcTemplate.query(sql, new PlayerRowMapper());
    }

    public List<Player> findAllByTeamName(String teamName) {
        String sql = """
            SELECT p.id, p.firstname, p.lastname, p.email, p.team_id, t.name AS team_name
            FROM PLAYER p
            INNER JOIN TEAM t ON t.id = p.team_id
            WHERE t.name = :name
            ORDER BY p.id
            """;

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", teamName);

        return namedParameterJdbcTemplate.query(sql, sqlParameterSource, new PlayerRowMapper());
    }

    public Optional<Player> findOneByFirstNameAndLastName(String firstName, String lastName) {
        String sql = """
            SELECT TOP(1) p.id, p.firstname, p.lastname, p.email, p.team_id, t.name AS team_name
            FROM PLAYER p
            INNER JOIN TEAM t ON t.id = p.team_id
            WHERE firstname = :firstName AND lastname = :lastName
            ORDER BY p.id
            """;

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("firstName", firstName)
                .addValue("lastName", lastName);

        try {
            Player player = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, new PlayerRowMapper());

            return Optional.ofNullable(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public int insertOne(PlayerDTO playerDTO) throws SqlException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = (new MapSqlParameterSource())
                .addValue("firstname", playerDTO.firstName())
                .addValue("lastname", playerDTO.lastName())
                .addValue("email", playerDTO.email())
                .addValue("team_id", playerDTO.teamId());

        String sql = """
            INSERT INTO PLAYER (firstname, lastname, email, team_id) 
            VALUES (:firstname, :lastname, :email, :team_id)
            """;

        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        Number key = keyHolder.getKey();

        if (key != null) {
            return key.intValue();
        }

        throw new SqlException("Insert Error");
    }

    @Override
    public boolean deleteOne(int id) {
        String sql = "DELETE FROM PLAYER WHERE id=?";

        return jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt;
        }) == 1;
    }

    @Override
    public boolean updateTeam(Player player) throws SqlException {
        String sql = """
                UPDATE PLAYER SET team_id = :team_id WHERE id = :id
                """;

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("team_id", player.getTeamId())
                .addValue("id", player.getId());

        return namedParameterJdbcTemplate.update(sql, sqlParameterSource) == 1;
    }

    private String getBaseSelectQueryPart() {
        return """
            SELECT p.id, p.firstname, p.lastname, p.email, p.team_id, t.name AS team_name
            FROM PLAYER p
            INNER JOIN TEAM t ON t.id = p.team_id
            """;
    }

    static class PlayerRowMapper implements RowMapper<Player> {
        @Override
        public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setFirstName(rs.getString("firstname"));
            player.setLastName(rs.getString("lastname"));
            player.setEmail(rs.getString("email"));
            player.setTeamId(rs.getInt("team_id"));
            player.setTeamName(rs.getString("team_name"));

            return player;
        }
    }
}

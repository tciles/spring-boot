package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface IPlayerDAO {
    List<Player> findAll();

    Optional<Player> findOneById(int id);

    List<Player> findAllByTeamName(String teamName);

    Optional<Player> findOneByFirstNameAndLastName(String firstName, String lastName);

    int insertOne(PlayerDTO playerDTO) throws SqlException;

    boolean updateTeam(Player player) throws SqlException;

    boolean deleteOne(int id);
}

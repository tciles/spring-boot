package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Dal.IPlayerDAO;
import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class PlayerWithDAOService implements IPlayerService {

    private final IPlayerDAO playerDAO;

    public PlayerWithDAOService(IPlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        return playerDAO.findOneById(id);
    }

    @Override
    public Set<Player> getPlayers() {
        return new HashSet<>(playerDAO.findAll());
    }

    @Override
    public Set<Player> getPlayersByEmail(Set<String> emails) {
        return Set.of();
    }

    @Override
    public Set<Player> getPlayersByTeamName(String teamName) {
        return new HashSet<>(playerDAO.findAllByTeamName(teamName));
    }

    @Override
    public Optional<Player> getByFirstNameAndLastName(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public Player addPlayer(PlayerDTO playerDto) {
        try {
            Optional<Player> found = playerDAO.findOneByFirstNameAndLastName(playerDto.firstName(), playerDto.lastName());

            if (found.isPresent()) {
                throw new SqlException("Player already exists");
            }

            int insertedId = playerDAO.insertOne(playerDto);

            Optional<Player> player = getPlayer(insertedId);

            if (player.isEmpty()) {
                throw new RuntimeException("Player can not be created");
            }

            return player.get();
        } catch (SqlException e) {
            throw new RuntimeException("Insert Player Failed.", e);
        }
    }

    @Override
    public boolean removePlayer(int id) {
        return playerDAO.deleteOne(id);
    }
}

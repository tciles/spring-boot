package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerService implements IPlayerService {

    private static int id = 0;
    private final Set<Player> players = new HashSet<>();

    PlayerService() {
        addPlayer(new PlayerDTO("Thomas", "CILES", "thomas.ciles2025@campus-eni.fr"));
        addPlayer(new PlayerDTO("John", "Doe", "john.doe@campus-eni.fr"));
        addPlayer(new PlayerDTO("Jane", "Doe", "jane.doe@campus-eni.fr"));
        addPlayer(new PlayerDTO("Jane", "Doe2", null));
    }

    public static void resetId() {
        id = 0;
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    @Override
    public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public Player addPlayer(PlayerDTO playerDto) {
        Optional<Player> player = getByFirstNameAndLastName(playerDto.firstName(), playerDto.lastName());

        if (player.isPresent()) {
            throw new RuntimeException("Player already exists");
        }

        id++;
        Player newPlayer = new Player();
        BeanUtils.copyProperties(playerDto, newPlayer);

        newPlayer.setId(id);
        players.add(newPlayer);

        return newPlayer;
    }

    @Override
    public Optional<Player> getByFirstNameAndLastName(String firstName, String lastName) {
        for (Player p : players) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    @Override
    public Set<Player> getPlayersByEmail(Set<String> emails) {
        Set<Player> players = new HashSet<>();

        for (Player player : this.players) {
            if (emails.contains(player.getEmail())) {
                players.add(player);
            }
        }

        return players;
    }

    @Override
    public Set<Player> getPlayersByTeamName(String teamName) {
        Set<Player> players = new HashSet<>();

        for (Player player : this.players) {
            Team team = player.getTeam();

            if (team == null) {
                continue;
            }

            if (player.getTeam().getName().equals(teamName)) {
                players.add(player);
            }
        }

        return players;
    }
}

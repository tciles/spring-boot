package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerService implements IPlayerService {

    private static int id = 0;
    private final Set<Player> players = new HashSet<>();

    PlayerService() {
        addPlayer(new Player("Thomas", "CILES", "thomas.ciles2025@campus-eni.fr"));
        addPlayer(new Player("John", "Doe", "john.doe@campus-eni.fr"));
        addPlayer(new Player("Jane", "Doe", "jane.doe@campus-eni.fr"));
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

    public Set<Player> getPlayers() {
        return players;
    }

    public Player addPlayer(Player player) {
        if (getByEmail(player.getEmail()) != null) {
            throw new RuntimeException("Player already exists");
        }

        id++;
        player.setId(id);
        players.add(player);

        return player;
    }

    public Player getByEmail(String name) {
        for (Player player : players) {
            if (player.getEmail().equals(name)) {
                return player;
            }
        }

        return null;
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

}

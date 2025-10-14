package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService implements IPlayerService {

    private static int id = 0;
    private final List<Player> players = new ArrayList<>();

    {
        addPlayer(new Player("Thomas", "CILES", "thomas.ciles2025@campus-eni.fr"));
        addPlayer(new Player("John", "Doe", "john.doe@campus-eni.fr"));
        addPlayer(new Player("Jane", "Doe", "jane.doe@campus-eni.fr"));
    }

    public List<Player> getPlayers() {
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
}

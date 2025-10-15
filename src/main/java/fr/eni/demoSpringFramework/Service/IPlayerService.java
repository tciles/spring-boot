package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;

import java.util.Optional;
import java.util.Set;

public interface IPlayerService {

    Optional<Player> getPlayer(int id);

    Set<Player> getPlayers();

    Set<Player> getPlayersByEmail(Set<String> emails);
}

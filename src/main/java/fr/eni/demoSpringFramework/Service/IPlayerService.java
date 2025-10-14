package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Player;

import java.util.Set;

public interface IPlayerService {
    Set<Player> getPlayers();

    Set<Player> getPlayersByEmail(Set<String> emails);
}

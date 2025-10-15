package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;

import java.util.Optional;
import java.util.Set;

public interface IPlayerService {

    /**
     * Get a Player by his ID
     *
     * @param id Player ID
     * @return The Player or Empty
     */
    Optional<Player> getPlayer(int id);

    /**
     * Get All Players
     *
     * @return Set of Players
     */
    Set<Player> getPlayers();

    /**
     * Get Players by his email.
     *
     * @param emails Set of Player's emails
     * @return Set of Players
     */
    Set<Player> getPlayersByEmail(Set<String> emails);

    /**
     * Get a Player by his firstName and lastName
     *
     * @param firstName Player firstname
     * @param lastName  Player lastname
     * @return The Player
     */
    Optional<Player> getByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Create a Player
     *
     * @param player Player Object
     * @return The new Player
     */
    Player addPlayer(PlayerDTO player);
}

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
     * Get Players by his Team.name
     * @param teamName Team.name
     *
     * @return Set of Players
     */
    Set<Player> getPlayersByTeamName(String teamName);

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

    /**
     * Remove a Player
     *
     * @param id Player.id
     *
     * @return If the Player is already deleted
     */
    boolean removePlayer(int id);
}

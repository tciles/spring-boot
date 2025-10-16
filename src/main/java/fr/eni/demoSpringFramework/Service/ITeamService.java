package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;

import java.util.Optional;
import java.util.Set;

public interface ITeamService {
    /**
     * Get all Teams.
     *
     * @return The Teams.
     */
    Set<Team> getTeams();

    /**
     * Get Team By Name
     *
     * @param name Team Name
     * @return The Team
     */
    Optional<Team> getTeam(String name);

    /**
     * Get Team By ID
     *
     * @param id Team ID
     * @return The Team
     */
    Optional<Team> getTeam(int id);

    /**
     * Create a Team.
     *
     * @param teamDto Team Payload
     * @return The created team.
     */
    Team addTeam(TeamDTO teamDto);

    /**
     * Remove a team by his ID.
     *
     * @param id Team ID
     * @return If is deleted successfully
     */
    boolean removeTeam(Integer id);

    /**
     * Add a Player to the Team.
     *
     * @param teamId Team ID
     * @param player Player to add
     * @return If is added successfully
     */
    boolean addPlayer(int teamId, Player player);

    /**
     * Remove a Player to the Team.
     *
     * @param teamId   Team ID
     * @param playerId Player ID
     * @return If is removed successfully
     */
    boolean removePlayer(int teamId, int playerId);
}

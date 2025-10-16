package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;

import java.util.List;
import java.util.Optional;

public interface ITeamDAO {
    /**
     * Get all Team
     * @return List of Team
     */
    List<Team> findAllTeams();

    /**
     * Fin One By Name
     *
     * @param name Team.name
     *
     * @return Team if found
     */
    Optional<Team> findTeamByName(String name);

    /**
     * Fin One By ID
     *
     * @param id Team.id
     *
     * @return Team if found
     */
    Optional<Team> findTeamById(int id);

    /**
     * Add team
     *
     * @param teamDTO Team payload
     *
     * @return Inserted ID
     */
    int insertOne(TeamDTO teamDTO);

    /**
     * Delete Team
     *
     * @param id Team.id
     *
     * @return If the Team was deleted
     */
    boolean deleteOne(int id);
}

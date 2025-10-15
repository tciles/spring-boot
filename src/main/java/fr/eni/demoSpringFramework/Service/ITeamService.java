package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;

import java.util.Optional;
import java.util.Set;

public interface ITeamService {
    Set<Team> getTeams();

    Optional<Team> getTeam(String name);

    Optional<Team> getTeam(int id);

    Optional<Team> getTeamByName(String name);

    Team addTeam(TeamDTO teamDto);

    boolean removeTeam(Integer id);

    void addPlayers(Team team, Set<Player> players);

    boolean addPlayer(int teamId, Player player);

    boolean removePlayer(int teamId, int playerId);
}

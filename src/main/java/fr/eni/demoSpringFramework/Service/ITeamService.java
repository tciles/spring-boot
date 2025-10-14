package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;

import java.util.Set;

public interface ITeamService {
    Set<Team> getTeams();

    Team getTeamByName(String name);

    Team addTeam(TeamDTO teamDto);

    boolean removeTeam(String name);

    boolean removeTeam(Integer id);

    void addPlayers(Team team, Set<Player> players);

    boolean removePlayer(Team team, int id);
}

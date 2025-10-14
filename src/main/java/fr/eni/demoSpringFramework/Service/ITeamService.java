package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Player;
import fr.eni.demoSpringFramework.Dto.Team;

import java.util.Set;

public interface ITeamService {
    Set<Team> getTeams();

    Team getTeamByName(String name);

    Team addTeam(Team team);

    boolean removeTeam(String name);

    boolean removeTeam(Integer id);

    void addPlayers(Team team, Set<Player> players);

    boolean removePlayer(Team team, int id);
}

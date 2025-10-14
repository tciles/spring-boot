package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Team;

import java.util.List;

public interface ITeamService {
    List<Team> getTeams();

    Team getTeamByName(String name);

    boolean removeTeam(String name);

    boolean removeTeam(Integer id);
}

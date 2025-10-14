package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Team;
import fr.eni.demoSpringFramework.Provider.TeamsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements ITeamService {
    private final List<Team> teams = TeamsProvider.getTeams();

    public List<Team> getTeams() {
        return teams;
    }

    public boolean removeTeam(String name) {
        return teams.removeIf(team -> team.getName().equals(name));
    }

    public boolean removeTeam(Integer id) {
        return teams.removeIf(team -> team.getId() == id);
    }

    public Team getTeamByName(String name) {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }

        return null;
    }
}

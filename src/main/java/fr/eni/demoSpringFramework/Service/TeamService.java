package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService implements ITeamService {
    private static int id = 0;
    private final List<Team> teams = new ArrayList<>();

    {
        addTeam(new Team("U15F1"));
        addTeam(new Team("U15M1"));
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getTeamByName(String name) {
        if (name.isEmpty()) {
            return null;
        }

        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }

        return null;
    }

    public Team addTeam(Team team) {
        if (getTeamByName(team.getName()) != null) {
            throw new RuntimeException("Team already exists");
        }

        id++;
        team.setId(id);

        teams.add(team);

        return team;
    }

    public boolean removeTeam(String name) {
        if (name.isEmpty()) {
            return false;
        }

        return teams.removeIf(team -> team.getName().equals(name));
    }

    public boolean removeTeam(Integer id) {
        return teams.removeIf(team -> team.getId() == id);
    }
}

package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dto.Player;
import fr.eni.demoSpringFramework.Dto.Team;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeamService implements ITeamService {
    private static int id = 0;
    private final Set<Team> teams = new HashSet<>();

    public TeamService() {
        teams.add(new Team("U15F1"));
        teams.add(new Team("U15M1"));
    }

    public TeamService(Set<Team> teams) {
        this.teams.addAll(teams);
    }

    public Set<Team> getTeams() {
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

    public void addPlayers(Team team, Set<Player> players) {
        team.setPlayers(players);
    }
}

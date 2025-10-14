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
        addTeam(new Team("U15F1"));
        addTeam(new Team("U15M1"));
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

        Team team = getTeamByName(name);

        if (null == team) {
            return false;
        }

        return removeTeam(team.getId());
    }

    public boolean removeTeam(Integer id) {
        return teams.removeIf(team -> {
            if (team.getId() != id) {
                return false;
            }

            for (Player player : team.getPlayers()) {
                player.setTeam(null);
            }

            return true;
        });
    }

    public void addPlayers(Team team, Set<Player> players) {
        for (Player player : players) {
            if (team.hasPlayer(player)) {
                continue;
            }

            team.addPlayer(player);
        }
    }

    public boolean removePlayer(Team team, int id) {
        Set<Player> players = team.getPlayers();

        return players.removeIf(player -> {
            if (player.getId() != id) {
                return false;
            }

            player.setTeam(null);

            return true;
        });
    }
}

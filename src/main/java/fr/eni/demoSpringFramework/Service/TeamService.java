package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeamService implements ITeamService {
    private static int id = 0;
    private final Set<Team> teams = new HashSet<>();

    public TeamService() {
        addTeam(new TeamDTO("U15F1"));
        addTeam(new TeamDTO("U15M1"));
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

    public Team addTeam(TeamDTO teamDto) {
        if (getTeamByName(teamDto.name()) != null) {
            throw new RuntimeException("Team already exists");
        }

        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);

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

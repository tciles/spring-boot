package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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
        for (Team team : teams) {
            addTeam(new TeamDTO(team.getName()));
        }
    }

    public static void resetId(){
        id = 0;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Optional<Team> getTeamByName(String name) {
        if (name.isEmpty()) {
            return Optional.empty();
        }

        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return Optional.of(team);
            }
        }

        return Optional.empty();
    }

    public Optional<Team> getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return Optional.of(team);
            }
        }

        return Optional.empty();
    }

    public Optional<Team> getTeam(int id) {
        for (Team team : teams) {
            if (team.getId() == id) {
                return Optional.of(team);
            }
        }

        return Optional.empty();
    }

    public Team addTeam(TeamDTO teamDto) {
        Optional<Team> found = getTeam(teamDto.name());

        if (found.isPresent()) {
            throw new RuntimeException("Team already exists");
        }

        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);

        id++;
        team.setId(id);
        teams.add(team);

        System.out.println("Team added: " + team.getId() + " " + team.getName());

        return team;
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

    public boolean addPlayer(int teamId, Player player) {
        return getTeam(teamId).map(team -> {
            if (team.hasPlayer(player)) {
                return false;
            }

            team.addPlayer(player);

            return true;
        }).orElse(false);
    }

    public boolean removePlayer(int teamId, int playerId) {
        return getTeam(teamId).map(value -> value.getPlayers().removeIf(player -> {
            if (player.getId() != playerId) {
                return false;
            }

            player.setTeam(null);

            return true;
        })).orElse(false);
    }
}

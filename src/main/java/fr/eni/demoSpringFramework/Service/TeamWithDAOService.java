package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Dal.ITeamDAO;
import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class TeamWithDAOService implements ITeamService {

    private final ITeamDAO teamDAO;

    public TeamWithDAOService(ITeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    @Override
    public Set<Team> getTeams() {
        return new HashSet<>(teamDAO.findAllTeams());
    }

    @Override
    public Optional<Team> getTeam(String name) {
        return teamDAO.findTeamByName(name);
    }

    @Override
    public Optional<Team> getTeam(int id) {
        return teamDAO.findTeamById(id);
    }

    @Override
    public Team addTeam(TeamDTO teamDto) {
        int insertedId;

        try {
            insertedId = teamDAO.insertOne(teamDto);
        } catch (SqlException e) {
            throw new RuntimeException("Insert Team Failed.", e);
        }

        Optional<Team> team = getTeam(insertedId);

        if (team.isEmpty()) {
            throw new RuntimeException("Team can not be created");
        }

        return team.get();
    }

    @Override
    public boolean removeTeam(Integer id) {
        return teamDAO.deleteOne(id);
    }

    @Override
    public void addPlayers(Team team, Set<Player> players) {

    }

    @Override
    public boolean addPlayer(int teamId, Player player) {
        return false;
    }

    @Override
    public boolean removePlayer(int teamId, int playerId) {
        return false;
    }
}

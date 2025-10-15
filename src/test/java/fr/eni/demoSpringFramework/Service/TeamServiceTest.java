package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TeamServiceTest {
    @Test
    @DisplayName("Test get all")
    public void testGetAllTeams() {
        Set<Team> teams = new HashSet<>();
        teams.add(new Team("U15F1"));
        teams.add(new Team("U15M1"));

        ITeamService service = new TeamService(teams);
        TeamService.resetId();

        assertEquals(2, service.getTeams().size());
    }

    @Test
    @DisplayName("Test get one by name")
    public void testGetOneTeamByName() {
        Set<Team> teams = new HashSet<>();
        teams.add(new Team("U15F1"));
        teams.add(new Team("U15M1"));

        ITeamService service = new TeamService(teams);
        TeamService.resetId();

        Optional<Team> expected = Optional.of(new Team("U15F1"));

        assertEquals(expected, service.getTeam("U15F1"));
        assertEquals(expected, service.getTeam(1));
        assertTrue(service.getTeam("XXXX").isEmpty());
    }

    @Test
    @DisplayName("Test add one team")
    public void testAddTeam() {
        TeamDTO teamDto = new TeamDTO("__TEST__");

        ITeamService service = new TeamService(new HashSet<>());
        TeamService.resetId();

        Team team = service.addTeam(teamDto);
        Team expected = new Team("__TEST__");

        assertEquals(expected, team);
        assertEquals(Optional.of(expected), service.getTeam("__TEST__"));
    }
}

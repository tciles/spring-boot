package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamServiceTest {
    @Test
    @DisplayName("Test getAll")
    public void testGetAllTeams() {
        Set<Team> teams = new HashSet<>();
        teams.add(new Team("U15F1"));
        teams.add(new Team("U15M1"));

        ITeamService service = new TeamService(teams);

        assertEquals(2, service.getTeams().size());
    }
}

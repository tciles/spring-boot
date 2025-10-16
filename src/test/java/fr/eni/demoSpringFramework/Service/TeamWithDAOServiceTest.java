package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "dbname=spring_boot_test"
})
public class TeamWithDAOServiceTest {

    @Autowired
    private TeamWithDAO service;


    @Test
    @DisplayName("Test get all")
    public void testGetAllTeams() {
        assertFalse(service.getTeams().isEmpty());
    }

    @Test
    @DisplayName("Test get one by name")
    public void testGetOneTeamByName() {
        Optional<Team> expected = Optional.of(new Team("U15F1"));

        assertEquals(expected, service.getTeam("U15F1"));
        assertEquals(expected, service.getTeam(1));
        assertTrue(service.getTeam("XXXX").isEmpty());
    }

    @Test
    @DisplayName("Test add one team")
    public void testAddTeam() {
        String randomName = UUID.randomUUID().toString().substring(0, 10);
        TeamDTO teamDto = new TeamDTO(randomName);

        Team team = service.addTeam(teamDto);
        Team expected = new Team(randomName);

        assertEquals(expected, team);
        assertEquals(Optional.of(expected), service.getTeam(randomName));
    }

    @Test
    @DisplayName("Test delete one by id")
    public void testDeleteTeamById() {
        String randomName = UUID.randomUUID().toString().substring(0, 10);
        TeamDTO teamDto = new TeamDTO(randomName);
        Team team = service.addTeam(teamDto);

        assertTrue(service.removeTeam(team.getId()));
        assertFalse(service.removeTeam(team.getId()));
    }
}

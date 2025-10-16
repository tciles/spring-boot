package fr.eni.demoSpringFramework.Dal;

import fr.eni.demoSpringFramework.Do.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class TeamDaoTest {

    @Autowired
    private TeamDAO dao;

    @Test
    public void getTeams() {
        List<Team> teams = dao.findAllTeams();
        assertFalse(teams.isEmpty());
    }

}

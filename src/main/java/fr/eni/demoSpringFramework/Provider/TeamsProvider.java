package fr.eni.demoSpringFramework.Provider;

import fr.eni.demoSpringFramework.Dto.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamsProvider {
    static int id = 0;
    static ArrayList<Team> teams = new ArrayList<>();

    public static List<Team> getTeams() {
        addTeam("U15F1");
        addTeam("U15M1");

        return teams;
    }

    private static void addTeam(String name) {
        id++;
        teams.add(new Team(id, name));
    }
}

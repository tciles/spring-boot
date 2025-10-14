package fr.eni.demoSpringFramework.Dto;

import java.util.ArrayList;
import java.util.Objects;

public class Team {

    private int id;
    private String name;
    private ArrayList<Player> players;

    {
        players = new ArrayList<>();
    }

    public Team(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Team team)) return false;

        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

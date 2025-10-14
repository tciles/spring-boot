package fr.eni.demoSpringFramework.Dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Team {

    private int id;
    private String name;
    private Set<Player> players;

    {
        players = new HashSet<>();
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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        for (Player player : players) {
            player.setTeam(this);
        }

        this.players = players;
    }

    public void addPlayer(Player player) {
        player.setTeam(this);
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

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }
}

package fr.eni.demoSpringFramework.Dto;

import java.util.Objects;

public class Player {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private Team team;

    public Player(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Player player)) return false;

        return Objects.equals(email, player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}

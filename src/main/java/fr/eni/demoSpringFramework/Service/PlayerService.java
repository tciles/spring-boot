package fr.eni.demoSpringFramework.Service;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService implements IPlayerService {

    private static int id = 0;
    private final Set<Player> players = new HashSet<>();

    PlayerService() {
        addPlayer(new PlayerDTO("Thomas", "CILES", "thomas.ciles2025@campus-eni.fr"));
        addPlayer(new PlayerDTO("John", "Doe", "john.doe@campus-eni.fr"));
        addPlayer(new PlayerDTO("Jane", "Doe", "jane.doe@campus-eni.fr"));
        addPlayer(new PlayerDTO("Jane", "Doe2", null));
    }

    public static void resetId() {
        id = 0;
    }

    @Override
    public Set<Player> getPlayers() {
        return new HashSet<>(players);
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        return players.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public Optional<Player> getByFirstNameAndLastName(String firstName, String lastName) {
        return players
                .stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public Player addPlayer(PlayerDTO playerDto) {
        Optional<Player> player = getByFirstNameAndLastName(playerDto.firstName(), playerDto.lastName());

        if (player.isPresent()) {
            throw new RuntimeException("Player already exists");
        }

        id++;
        Player newPlayer = new Player();
        BeanUtils.copyProperties(playerDto, newPlayer);

        newPlayer.setId(id);
        players.add(newPlayer);

        return newPlayer;
    }

    @Override
    public Set<Player> getPlayersByEmail(Set<String> emails) {
        return players.stream().filter(player -> emails.contains(player.getEmail())).collect(Collectors.toSet());
    }

    @Override
    public Set<Player> getPlayersByTeamName(String teamName) {
        return players.stream().filter(player -> {
            Team team = player.getTeam();

            if (team == null) {
                return false;
            }

            return team.getName().equals(teamName);
        }).collect(Collectors.toSet());
    }

    @Override
    public boolean removePlayer(int id) {
        Optional<Player> player = getPlayer(id);

        if (player.isEmpty()) {
            throw new RuntimeException("Player not found");
        }

        return players.removeIf(p -> p.getId() == id);
    }
}

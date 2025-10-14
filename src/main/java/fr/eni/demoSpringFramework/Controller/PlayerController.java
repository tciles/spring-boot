package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.IPlayerService;
import fr.eni.demoSpringFramework.Service.ITeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final IPlayerService playerService;
    private final ITeamService teamService;

    public PlayerController(IPlayerService playerService,  ITeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping(path = {"", "/"}, name = "app_players_by_team")
    public ResponseEntity<Payload<Set<Player>>> getPlayersByTeam(
            @RequestParam("team") Optional<String> name
    ) {
        if (name.isEmpty()) {
            return new ResponseEntity<>(Payload.create(playerService.getPlayers()), HttpStatus.OK);
        }

        Team team = teamService.getTeamByName(name.get());
        Payload<Set<Player>> payload;

        if (null == team) {
            payload = Payload.create(null, "Team " + name.get() + " Not Found",  HttpStatus.NOT_FOUND);
        } else {
            payload = Payload.create(team.getPlayers(), "Players for the team " + team.getName(), HttpStatus.OK);
        }

        return new ResponseEntity<>(payload, payload.getHttpStatus());
    }
}

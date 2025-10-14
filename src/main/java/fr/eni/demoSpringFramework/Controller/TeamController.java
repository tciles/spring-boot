package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Dto.Player;
import fr.eni.demoSpringFramework.Dto.Team;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.IPlayerService;
import fr.eni.demoSpringFramework.Service.ITeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    private final IPlayerService playerService;

    public TeamController(ITeamService teamService, IPlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping(path = {"", "/"})
    @ResponseBody
    public Payload<Set<Team>> getAll() {
        try {
            return Payload.create(teamService.getTeams());
        } catch (Exception e) {
            return Payload.create(new HashSet<>());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Payload<Team>> getOneByName(@PathVariable String name) {
        Team team = teamService.getTeamByName(name);

        if (null == team) {
            return new ResponseEntity<>(Payload.create(null, "Team Not Found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Payload.create(team), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Payload<Team>> addOneTeam(@RequestBody Team team) {
        try {
            return new ResponseEntity<>(Payload.create(teamService.addTeam(team)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Payload.create(
                    null,
                    "Error: Team can not be created",
                    HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Payload<Boolean>> deleteOneTeam(@PathVariable String name) {
        try {
            return new ResponseEntity<>(Payload.create(teamService.removeTeam(name)), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(Payload.create(
                    null,
                    "Error: Team can not be deleted",
                    HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{name}/add-player")
    @ResponseBody
    public ResponseEntity<Payload<Team>> addTeamPlayer(@PathVariable String name, @RequestBody Set<String> emails) {
        Team team = teamService.getTeamByName(name);

        if (null == team) {
            return new ResponseEntity<>(Payload.create(null, "Team Not Found"), HttpStatus.NOT_FOUND);
        }

        Set<Player> players = playerService.getPlayersByEmail(emails);

        teamService.addPlayers(team, players);

        return new ResponseEntity<>(Payload.create(team), HttpStatus.OK);
    }
}

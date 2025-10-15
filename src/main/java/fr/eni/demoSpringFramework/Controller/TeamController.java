package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.IPlayerService;
import fr.eni.demoSpringFramework.Service.ITeamService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    private final IPlayerService playerService;

    public TeamController(ITeamService teamService, IPlayerService playerService, View error) {
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
        if (name.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Payload.create(null, "Bad parameter <name>")
            );
        }

        return teamService.getTeamByName(name)
                .map(team -> ResponseEntity.ok(Payload.create(team)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Payload.create(null, "Team Not Found", HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    public ResponseEntity<Payload<Team>> addOneTeam(
            @Valid @RequestBody TeamDTO teamDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            String message = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(Payload.create(
                    null,
                    message,
                    HttpStatus.BAD_REQUEST
            ));
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(Payload.create(teamService.addTeam(teamDto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Payload.create(
                        null,
                        "Error: Team can not be created",
                        HttpStatus.BAD_REQUEST
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Payload<Boolean>> deleteOneTeam(@PathVariable String id) {
        try {
            int teamId = NumberUtils.parseNumber(id, Integer.class);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Payload.create(teamService.removeTeam(teamId)));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Payload.create(
                            null,
                            "Error: Team id bad format",
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                 Payload.create(
                      null,
                      "Error: Team can not be deleted",
                      HttpStatus.BAD_REQUEST
                 )
            );
        }
    }

    @PatchMapping("/{name}/add-players")
    public ResponseEntity<Payload<Team>> addTeamPlayer(@PathVariable String name, @RequestBody Set<String> emails) {
        Optional<Team> team = teamService.getTeamByName(name);

        if (team.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Payload.create(null, "Team Not Found"));
        }

        teamService.addPlayers(team.get(), playerService.getPlayersByEmail(emails));

        return new ResponseEntity<>(Payload.create(team.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Payload<Boolean>> deleteTeamPlayer(
            @PathVariable("teamId") String teamIdParam,
            @PathVariable("playerId") String playerIdParam
    ) {
        try {
            int teamId = NumberUtils.parseNumber(teamIdParam, Integer.class);
            int playerId = NumberUtils.parseNumber(playerIdParam, Integer.class);

            boolean isRemoved = teamService.removePlayer(teamId, playerId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Payload.create(isRemoved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Payload.create(null, "Team <id> bad format"));
        }
    }
}

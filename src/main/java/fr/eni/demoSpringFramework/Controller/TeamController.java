package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Do.Team;
import fr.eni.demoSpringFramework.Dto.TeamDTO;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.IPlayerService;
import fr.eni.demoSpringFramework.Service.ITeamService;
import jakarta.validation.Valid;
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

@CrossOrigin(value = {
        "http://localhost:4200"
})
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    private final IPlayerService playerService;

    public TeamController(ITeamService teamService, IPlayerService playerService, View error) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    /**
     * Get all teams
     *
     * @return The Response
     */
    @GetMapping(path = {"", "/"})
    @ResponseBody
    public Payload<Set<Team>> getAll() {
        try {
            return Payload.create(teamService.getTeams());
        } catch (Exception e) {
            return Payload.create(new HashSet<>());
        }
    }

    /**
     * Get a Team by his id
     *
     * @param id Team id
     * @return The Response
     */
    @GetMapping("/{id}/view")
    public ResponseEntity<Payload<Team>> getOneById(@PathVariable String id) {
        if (id.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Payload.create(null, "Bad parameter <id>")
            );
        }

        return teamService.getTeam(Integer.parseInt(id))
                .map(team -> ResponseEntity.ok(Payload.create(team)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Payload.create(null, "Team Not Found", HttpStatus.NOT_FOUND)));
    }

    /**
     * Get a Team by his name
     *
     * @param name Team Name
     * @return The Response
     */
    @GetMapping("/{name}")
    public ResponseEntity<Payload<Team>> getOneByName(@PathVariable String name) {
        if (name.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Payload.create(null, "Bad parameter <name>")
            );
        }

        return teamService.getTeam(name)
                .map(team -> ResponseEntity.ok(Payload.create(team)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Payload.create(null, "Team Not Found", HttpStatus.NOT_FOUND)));
    }

    /**
     * Create a Team
     *
     * @param teamDto Payload of type TeamDTO
     * @param result  Validation Result
     * @return The Response
     */
    @PostMapping
    public ResponseEntity<?> addOneTeam(
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

    /**
     * Delete a Team
     *
     * @param id Team ID
     * @return The Response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneTeam(@PathVariable String id) {
        try {
            int teamId = NumberUtils.parseNumber(id, Integer.class);

            boolean isDeleted = teamService.removeTeam(teamId);

            if (!isDeleted) {
                throw new SqlException("Team Not Deleted");
            }

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
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

    /**
     * Add a player to a team.
     *
     * @param teamIdParam   Team ID
     * @param playerIdParam Player ID
     * @return The Response
     */
    @PatchMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Payload<Boolean>> addTeamPlayer(
            @PathVariable("teamId") String teamIdParam,
            @PathVariable("playerId") String playerIdParam
    ) {
        try {
            int teamId = NumberUtils.parseNumber(teamIdParam, Integer.class);
            int playerId = NumberUtils.parseNumber(playerIdParam, Integer.class);

            Optional<Player> player = playerService.getPlayer(playerId);

            if (player.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Payload.create(null, "Player Not Found"));
            }

            boolean isAdded = teamService.addPlayer(teamId, player.get());

            return ResponseEntity.status(HttpStatus.OK).body(Payload.create(isAdded, "Player added"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Payload.create(null, "Team/Player <id> bad format"));
        }
    }

    /**
     * Remove a player from a team.
     *
     * @param teamIdParam   Team ID
     * @param playerIdParam Player ID
     * @return The Response
     */
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
            return ResponseEntity.badRequest().body(Payload.create(null, "Team/Player <id> bad format"));
        }
    }
}

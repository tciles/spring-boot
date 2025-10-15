package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Do.Player;
import fr.eni.demoSpringFramework.Dto.PlayerDTO;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.IPlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final IPlayerService playerService;

    public PlayerController(IPlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Get All Players or One Player by his name.
     *
     * @param name Optional Player name
     *
     * @return The Response.
     */
    @GetMapping(path = {"", "/"}, name = "app_players_by_team")
    public ResponseEntity<Payload<Set<Player>>> getPlayersByTeam(
            @RequestParam("team") Optional<String> name
    ) {
        if (name.isEmpty()) {
            return new ResponseEntity<>(Payload.create(playerService.getPlayers()), HttpStatus.OK);
        }

        Payload<Set<Player>> payload1 = new Payload<>(playerService.getPlayersByTeamName(name.get()));

        return ResponseEntity.status(payload1.getHttpStatus()).body(payload1);

        /*
        Payload<Set<Player>> payload = teamService.getTeam(name.get())
                .map(value -> Payload.create(value.getPlayers(), "Players for the team " + value.getName(), HttpStatus.OK))
                .orElseGet(() -> Payload.create(null, "Team " + name.get() + " Not Found", HttpStatus.NOT_FOUND));

        return ResponseEntity.status(payload.getHttpStatus()).body(payload);
        */
    }

    /**
     * Create a new Player
     *
     * @param playerDto The Payload
     * @param result    Validation Result
     * @return The Response
     */
    @PostMapping
    public ResponseEntity<Payload<Player>> addPlayer(
            @Valid @RequestBody PlayerDTO playerDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            String message = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(Payload.create(null, message, HttpStatus.BAD_REQUEST));
        }

        try {
            Player player = playerService.addPlayer(playerDto);

            return ResponseEntity.ok(Payload.create(player));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(Payload.create(null, "Error: Player can not be created", HttpStatus.BAD_REQUEST));
        }
    }
}

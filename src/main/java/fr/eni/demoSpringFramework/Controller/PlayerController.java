package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Dal.Error.SqlException;
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

@CrossOrigin
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

        Payload<Set<Player>> payload = new Payload<>(playerService.getPlayersByTeamName(name.get()));

        return ResponseEntity.status(payload.getHttpStatus()).body(payload);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(
            @PathVariable("id") int id
    ) {
        try {
            boolean isDeleted = playerService.removePlayer(id);

            if (!isDeleted) {
                throw new SqlException("");
            }

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Payload.create(
                    null,
                    "Error: Player can not be deleted",
                    HttpStatus.BAD_REQUEST
            ));
        }
    }
}

package fr.eni.demoSpringFramework.Controller;

import fr.eni.demoSpringFramework.Dto.Team;
import fr.eni.demoSpringFramework.Response.Payload;
import fr.eni.demoSpringFramework.Service.ITeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(path = {"", "/"})
    @ResponseBody
    public Payload<List<Team>> getAll() {
        return Payload.create(teamService.getTeams());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Payload<Team>> getOneById(@PathVariable String name) {
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
            return new ResponseEntity<>(Payload.create(null, "Error: Team can not be created", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Payload<Boolean>> deleteOneTeam(@PathVariable String name) {
        try {
            return new ResponseEntity<>(Payload.create(teamService.removeTeam(name)), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(Payload.create(null, "Error: Team can not be deleted", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}

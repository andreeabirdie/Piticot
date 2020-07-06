package games;

import domain.Game;
import domain.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.GameRepository;
import repository.RoundRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/games")
public class GamesController {
    private static final String template="TemplateGame";

    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(value = "/{gameID}", method = RequestMethod.GET)
    public Map<String, String> getStart(@PathVariable Integer gameID){
        Map<String, String> result = new HashMap<>();
        Game g = gameRepository.findOne(gameID);

        result.put("configuration", g.getStartConfiguration());
        result.put("player A", g.getPlayer1());
        result.put("player B", g.getPlayer2());
        result.put("player C", g.getPlayer3());


        return result;
    }

    @RequestMapping(value = "/rounds/{gameID}/{playerID}", method = RequestMethod.GET)
    public List<Round> getRounds(@PathVariable Integer gameID, @PathVariable String playerID) {
        List<Round> rounds = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            Round r = roundRepository.findOne(gameID, playerID, i);
            if(r != null){
                rounds.add(r);
            }
        }
        return rounds;
    }

}
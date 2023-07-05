package paf.visa.day26_pafworkshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import paf.visa.day26_pafworkshop.service.BoardgamesService;

@RestController
@RequestMapping("/games")
public class BoardgamesRestController {
    
    @Autowired
    private BoardgamesService boardgamesService;

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public String findGamesWithLimitOffset(@RequestParam(name="limit") Integer limit, @RequestParam(name="offset") Integer offset) {
        return boardgamesService.findGamesWithLimitOffset(limit, offset).toString();
    }

    @GetMapping(path="/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findGamesByRankWithLimitOffset(@RequestParam(name="limit") Integer limit, @RequestParam(name="offset") Integer offset) {
        return boardgamesService.findGamesByRankingWithLimitOffset(limit,offset).toString();
    }

    @GetMapping(path="/{game_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findGameById(@PathVariable Integer gameId) {
        return boardgamesService.findGameById(gameId);
    }
}

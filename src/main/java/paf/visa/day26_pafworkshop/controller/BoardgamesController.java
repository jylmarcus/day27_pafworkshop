package paf.visa.day26_pafworkshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import paf.visa.day26_pafworkshop.model.Comment;
import paf.visa.day26_pafworkshop.model.Game;
import paf.visa.day26_pafworkshop.service.BoardgamesService;

@Controller
public class BoardgamesController {
    
    @Autowired
    private BoardgamesService boardgamesService;

    @GetMapping("/search")
    public ModelAndView getGame(@RequestParam(name="gameName") String gameName) {
        //refactor this part for after searching then select specific game
        //need a search result page listing all games matching the search term
        List<Game> gamesList = boardgamesService.findGamesByName(gameName);

        ModelAndView mav = new ModelAndView();

        if(gamesList.size() == 0) {
            mav.setViewName("not-found");
            mav.addObject("message", "Cannot find %s".formatted(gameName));
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }

        mav.setViewName("search-result");
        mav.addObject("title", gameName);
        mav.addObject("gamesList", gamesList);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @GetMapping(path="/games/{game_id}")
    public ModelAndView findGameById(@PathVariable Integer gameId) {

        Optional<Game> optGame = boardgamesService.findGameById(gameId);
        Optional<List<Comment>> optComments = boardgamesService.findCommentsById(gameId);

        ModelAndView mav = new ModelAndView();

        if(optGame.isEmpty()) {
            mav.setViewName("not-found");
            mav.addObject("message", "Cannot find %d".formatted(gameId));
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }

        mav.setViewName("game-details");
        mav.addObject("title", optGame.get().getName());
        mav.addObject("game", optGame.get());
        mav.addObject("comment", new Comment());
        mav.addObject("comments", optComments.get());
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @PostMapping(path="/review")
    public ModelAndView addComment(@ModelAttribute("comment") Comment comment) {
        boardgamesService.postComment(comment);
        return findGameById(comment.getGameId());
    }
}

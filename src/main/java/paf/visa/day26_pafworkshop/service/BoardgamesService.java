package paf.visa.day26_pafworkshop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.visa.day26_pafworkshop.model.Comment;
import paf.visa.day26_pafworkshop.model.Game;
import paf.visa.day26_pafworkshop.repository.BoardgamesRepository;
import paf.visa.day26_pafworkshop.repository.CommentRepository;

@Service
public class BoardgamesService {
    
    @Autowired
    private BoardgamesRepository boardgamesRepository;

    @Autowired 
    private CommentRepository commentRepository;

    public List<Game> findGamesByName(String gameName) {
        List<Document> result = boardgamesRepository.findGamesByName(gameName);
        List<Game> gamesList = new ArrayList<Game>();
        for(Document d :result){
            Game game = new Game();
            game.setId(d.getInteger("gid"));
            game.setName(d.getString("name"));
            gamesList.add(game);
        }
        //query the average of the game's rating using gid and set the average

        return gamesList;
    }

    public Optional<Game> findGameById(Integer gameId) {
        Optional<Document> result = boardgamesRepository.findGameById(gameId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Game game = createGame(result.get());

        return Optional.of(game);
    }

    public Optional<List<Comment>> findCommentsById(Integer gameId) {
        Optional<List<Document>> result = commentRepository.findCommentsByGId(gameId);
        if(result.isEmpty()) {
            return Optional.empty();
        }

        List<Comment> commentList = new ArrayList<Comment>();
        for (Document d : result.get()) {
            Comment comment = createComment(d);
            commentList.add(comment);
        }

        return Optional.of(commentList);
    }

    public void postComment(Comment comment) {
        comment.setCommentId(createCommentId());
        commentRepository.insertComment(comment);
    }

    public Game createGame(Document document) {
        Game game = new Game();
        game.setId(document.getInteger("gid"));
        game.setName(document.getString("name"));
        game.setYear(LocalDate.of(document.getInteger("year"), 1, 1));
        game.setRank(document.getInteger("ranking"));
        game.setUsersRated(document.getInteger("users_rated"));
        game.setUrl(document.getString("url"));
        game.setThumbnail(document.getString("image"));

        return game;
    }

    public Comment createComment(Document document) {
        Comment comment = new Comment();
        comment.setGameId(document.getInteger("gid"));
        comment.setCommentId(document.getString("c_id"));
        comment.setUserName(document.getString("user"));
        comment.setRating(document.getInteger("rating"));
        comment.setComment(document.getString("c_text"));

        return comment;
    }

    public String createCommentId(){
        String cId = UUID.randomUUID().toString().substring(0, 8);
        while(commentRepository.findCommentByCId(cId).isPresent()) {
            cId = UUID.randomUUID().toString().substring(0, 8);
        }
        return cId;
    }
}

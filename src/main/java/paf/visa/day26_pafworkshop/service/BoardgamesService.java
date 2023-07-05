package paf.visa.day26_pafworkshop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import paf.visa.day26_pafworkshop.model.Game;
import paf.visa.day26_pafworkshop.repository.BoardgamesRepository;

@Service
public class BoardgamesService {
    
    @Autowired
    private BoardgamesRepository boardgamesRepository;

    public JsonObject findGamesWithLimitOffset(Integer limit, Integer offset) {
        List<Document> results = boardgamesRepository.findGamesWithLimitOffset(limit, offset);
        JsonArrayBuilder gamesArrayBuilder = Json.createArrayBuilder();
        for (Document d: results) {
            JsonObject json = Json.createObjectBuilder()
            .add("game_id", d.getInteger("_id"))
            .add("name", d.getString("name")).build();

            gamesArrayBuilder.add(json);
        }

        JsonArray gamesArray = gamesArrayBuilder.build();

        JsonObject gamesJson = Json.createObjectBuilder()
        .add("games", gamesArray)
        .add("offset", offset)
        .add("limit", limit)
        .add("total", results.size())
        .add("timestamp", (new Date()).toString()).build();

        return gamesJson;
    }

    public JsonObject findGamesByRankingWithLimitOffset(Integer limit, Integer offset) {
        List<Document> results = boardgamesRepository.findGamesByRankingWithLimitOffset(limit, offset);
        JsonArrayBuilder gamesArrayBuilder = Json.createArrayBuilder();
        for (Document d: results) {
            JsonObject json = Json.createObjectBuilder()
            .add("game_id", d.getInteger("_id"))
            .add("name", d.getString("name")).build();

            gamesArrayBuilder.add(json);
        }

        JsonArray gamesArray = gamesArrayBuilder.build();

        JsonObject gamesJson = Json.createObjectBuilder()
        .add("games", gamesArray)
        .add("offset", offset)
        .add("limit", limit)
        .add("total", results.size())
        .add("timestamp", (new Date()).toString()).build();

        return gamesJson;
    }

    public String findGameById(Integer gameId) {
        Optional<Game> result = boardgamesRepository.findGameById(gameId);
        if(result.isEmpty()) {
            return "Game not found";
        }
        Game game = result.get();
        JsonObject gameJson = Json.createObjectBuilder()
        .add("game_id", game.getId())
        .add("name", game.getName())
        .add("year", game.getYear().toString())
        .add("ranking", game.getRank())
        .add("average", game.getAverage())
        .add("users_rated", game.getUserRating())
        .add("url", game.getUrl())
        .add("thumbnail", game.getThumbnail())
        .add("timestamp", game.getTimestamp().toString()).build();

        return gameJson.toString();
    }
}

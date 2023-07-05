package paf.visa.day26_pafworkshop.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import paf.visa.day26_pafworkshop.model.Game;

@Repository
public class BoardgamesRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String C_GAMES = "games";
    public static final String F_NAME = "name";
    /*
     * db.boardgames.find().limit(limit).skip(offset)
     */

    public List<Document> findGamesWithLimitOffset(Integer limit, Integer offset) {
        limit = limit == null? 25 : limit;
        offset = offset == null? 0: offset;
        //Set filter

        //Create Mongo query with filter
        Query query = new Query();
        query.limit(limit).skip(offset);

        List<Document> results = mongoTemplate.find(query, Document.class, C_GAMES);
        return results;
    }

    public List<Document> findGamesByRankingWithLimitOffset(Integer limit, Integer offset) {
        limit = limit == null? 25 : limit;
        offset = offset == null? 0: offset;
        //Set filter

        //Create Mongo query with filter
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "ranking")).limit(limit).skip(offset);

        List<Document> results = mongoTemplate.find(query, Document.class, C_GAMES);
        return results;
    }

    public Optional<Game> findGameById(Integer gameId) {
        ObjectId docId = new ObjectId(Integer.toString(gameId));
        return Optional.ofNullable(mongoTemplate.findById(docId, Game.class, C_GAMES));
    }
}

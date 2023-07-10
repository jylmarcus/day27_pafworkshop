package paf.visa.day26_pafworkshop.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BoardgamesRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String C_GAMES = "game";
    public static final String C_COMMENTS = "comment";
    public static final String F_NAME = "name";
    public static final String F_GAME_ID = "gid";
    /*
     * db.boardgames.find().limit(limit).skip(offset)
     */

    public List<Document> findGamesByName(String gameName) {
        Query query = new Query();
        Criteria criteria = Criteria.where(F_NAME).is(gameName);
        query.addCriteria(criteria);

        List<Document> results = mongoTemplate.find(query, Document.class, C_GAMES);
        return results;
    }

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

    public Optional<Document> findGameById(Integer gid) {
        Query query = Query.query(Criteria.where(F_GAME_ID).is(gid));
        return Optional.ofNullable(mongoTemplate.findById(query, Document.class, C_GAMES));
    }
}

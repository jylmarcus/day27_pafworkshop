package paf.visa.day26_pafworkshop.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import paf.visa.day26_pafworkshop.model.Comment;

@Repository
public class CommentRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String C_COMMENTS = "comment";
    public static final String F_USERNAME = "user";
    public static final String F_GAME_ID = "gid";
    public static final String F_RATING = "rating";
    public static final String F_COMMENT_TEXT = "c_ text";
    public static final String F_COMMENT_ID = "c_id";

    public Optional<List<Document>> findCommentsByGId(Integer gid) {
        Query query = Query.query(Criteria.where(F_GAME_ID).is(gid));
        return Optional.ofNullable(mongoTemplate.find(query, Document.class, C_COMMENTS));
    }

    public Optional<String> findCommentByCId(String cid) {
        Query query = Query.query(Criteria.where(F_COMMENT_ID).is(cid));
        return Optional.ofNullable(mongoTemplate.findById(query, String.class, C_COMMENTS));
    }

    public ObjectId insertComment(Comment comment) {
        Document doc = new Document();
        doc.put(F_COMMENT_ID, comment.getCommentId());
        doc.put(F_USERNAME, comment.getUserName());
        doc.put(F_RATING, comment.getRating());
        doc.put(F_COMMENT_TEXT, comment.getComment());
        doc.put(F_GAME_ID, comment.getGameId());

        Document newDoc = mongoTemplate.insert(doc, C_COMMENTS);
        return newDoc.getObjectId("_id");
    }
}

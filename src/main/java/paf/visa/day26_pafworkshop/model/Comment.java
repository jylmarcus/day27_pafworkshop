package paf.visa.day26_pafworkshop.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable{
    private Integer gameId;
    private String commentId;
    private String userName;
    private Integer rating;
    private String comment;
}

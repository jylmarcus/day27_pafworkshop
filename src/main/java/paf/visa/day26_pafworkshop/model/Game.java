package paf.visa.day26_pafworkshop.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable{
    private Integer id;
    private String name;
    private LocalDateTime year;
    private Integer rank;
    private Integer average;
    private Double userRating;
    private String url;
    private String thumbnail;
    private LocalDateTime timestamp;

}

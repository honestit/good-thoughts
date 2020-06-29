package pl.coderslab.webinars.goodthoughts.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"content", "author"})
@Entity
public class Quote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String url;
    private String translation;
    private String author;
    private String category;
    private LocalDateTime createdOn;
}

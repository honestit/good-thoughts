package pl.coderslab.webinars.goodthoughts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.webinars.goodthoughts.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    boolean existsByContentAndAuthor(String content, String author);
}

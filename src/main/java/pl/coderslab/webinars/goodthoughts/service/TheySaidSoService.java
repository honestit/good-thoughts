package pl.coderslab.webinars.goodthoughts.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TheySaidSoService {

    @Cacheable(cacheNames = "pages")
    public Document getQuoteOfTheyDayDocument() throws IOException {
        log.info("Pobieranie treści strony z cytatami dnia");
        return Jsoup.connect("https://theysaidso.com/quote-of-the-day/").get();
    }
}

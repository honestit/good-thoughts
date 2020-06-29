package pl.coderslab.webinars.goodthoughts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.webinars.goodthoughts.model.Quote;
import pl.coderslab.webinars.goodthoughts.model.QuoteCategory;
import pl.coderslab.webinars.goodthoughts.repository.QuoteRepository;

import java.io.IOException;
import java.util.Collection;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class DefaultQuoteService implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final TranslationService translationService;
    private final TheySaidSoService theySaidSoService;

    @Cacheable(cacheNames = {"quotes"}, key = "'INSPIRING'")
    public Quote getDefaultQuote() {
        Quote quote = getQuoteByCategory("Inspiring Quote of the day");
        String translation = translationService.getTranslation(quote.getContent());
        quote.setTranslation(translation);
        quote.setCategory(QuoteCategory.INSPIRING.name().toLowerCase());
        log.info("Domyślny cytat: {}", quote);
        return quote;
    }

    @Cacheable(cacheNames = {"quotes"}, key = "#category.name()")
    public Quote getQuote(QuoteCategory category) {
        Quote quote = getQuoteByCategory(category.getPhrase());
        String translation = translationService.getTranslation(quote.getContent());
        quote.setTranslation(translation);
        quote.setCategory(category.name().toLowerCase());
        log.info("Cytat z kategorii '{}': {}", category, quote);
        return quote;
    }

    @Override
    @CachePut(cacheNames = {"quotes"}, key = "#quote.category.toUpperCase()")
    public Quote rememberQuote(Quote quote) {
        log.debug("Zapamiętuje cytat: {}", quote);
        boolean exists = quoteRepository.existsByContentAndAuthor(quote.getContent(), quote.getAuthor());
        if (!exists) {
            quoteRepository.save(quote);
            log.info("Cytat zapamiętany pod id = {}", quote.getId());
        } else {
            log.debug("Cytat już wcześniej zapamiętany");
        }
        return quote;
    }

    @Override
    public Collection<Quote> favouriteQuotes() {
        log.debug("Pobieranie listy zapamiętanych cytatów");
        return quoteRepository.findAll();
    }

    private Quote getQuoteByCategory(String phrase) {
        try {
            Document document = theySaidSoService.getQuoteOfTheyDayDocument();
            log.trace("Pobrany dokument:\n{}", document);
            String quoteContent = getQuoteContent(phrase, document);
            log.debug("Pobrana treść: {}", quoteContent);
            String quoteAuthor = getQuoteAuthor(phrase, document);
            log.debug("Pobrany autor: {}", quoteAuthor);
            String quoteURL = getQuoteURL(phrase, document);
            log.debug("Pobrany url: {}", quoteURL);
            Quote quote = new Quote();
            quote.setContent(quoteContent);
            quote.setAuthor(quoteAuthor);
            quote.setUrl(quoteURL);
            return quote;
        } catch (IOException e) {
            log.error("Błąd połączenia do strony theysaidso.com", e);
            return null;
        }
    }

    private String getQuoteAuthor(String phrase, Document document) {
        Elements authorElement = document.select("div.carousel-inner a[title=\"" + phrase + "\"] + span span[itemprop=\"name\"]");
        return authorElement.text();
    }

    private String getQuoteContent(String phrase, Document document) {
        Elements quoteElement = document.select("div.carousel-inner a[title=\"" + phrase + "\"] span[itemprop=\"text\"]");
        return quoteElement.text();
    }

    private String getQuoteURL(String phrase, Document document) {
        Elements quoteLink = document.select("div.carousel-inner p.lead a[title=\"" + phrase + "\"]");
        return "https://theysaidso.com" + quoteLink.attr("href");
    }

    private Document getDocument() throws IOException {
        return Jsoup.connect("https://theysaidso.com/quote-of-the-day/").get();
    }
}

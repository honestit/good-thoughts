package pl.coderslab.webinars.goodthoughts.service;

import pl.coderslab.webinars.goodthoughts.model.Quote;
import pl.coderslab.webinars.goodthoughts.model.QuoteCategory;

import java.util.Collection;

public interface QuoteService {

    Quote getDefaultQuote();

    Quote getQuote(QuoteCategory category);

    Quote rememberQuote(Quote quote);

    Collection<Quote> favouriteQuotes();
}

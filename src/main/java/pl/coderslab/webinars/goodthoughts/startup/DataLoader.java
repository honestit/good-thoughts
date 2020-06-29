package pl.coderslab.webinars.goodthoughts.startup;

import lombok.RequiredArgsConstructor;
import pl.coderslab.webinars.goodthoughts.model.Quote;
import pl.coderslab.webinars.goodthoughts.model.QuoteCategory;
import pl.coderslab.webinars.goodthoughts.service.QuoteService;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class DataLoader {

    private final QuoteService quoteService;

    @PostConstruct
    public void loadData() {
        for (QuoteCategory quoteCategory : QuoteCategory.values()) {
            Quote quote = quoteService.getQuote(quoteCategory);
            quoteService.rememberQuote(quote);
        }
    }
}

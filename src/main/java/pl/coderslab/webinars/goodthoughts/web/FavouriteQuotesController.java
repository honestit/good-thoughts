package pl.coderslab.webinars.goodthoughts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.webinars.goodthoughts.model.Quote;
import pl.coderslab.webinars.goodthoughts.service.QuoteService;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/quotes/favourite")
@Controller @RequiredArgsConstructor
public class FavouriteQuotesController {

    private final QuoteService quoteService;

    @GetMapping
    public String getFavouriteQuotesPage(Model model) {
        Collection<Quote> quotes = quoteService.favouriteQuotes();
        Map<Integer, Collection<Quote>> quotesMap = getQuotesMap(quotes);
        model.addAttribute("quotesMap", quotesMap);
        return "favourite";
    }

    @GetMapping("/{category}")
    public String getFavouriteQuotesPage(@PathVariable String category, Model model) {
        List<Quote> favouriteQuotesByCategory = quoteService.favouriteQuotes()
                .stream()
                .filter(quote -> quote.getCategory().toLowerCase().equalsIgnoreCase(category.toLowerCase()))
                .collect(Collectors.toList());
        Map<Integer, Collection<Quote>> quotesMap = getQuotesMap(favouriteQuotesByCategory);
        model.addAttribute("quotesMap", quotesMap);
        return "favourite";
    }

    private Map<Integer, Collection<Quote>> getQuotesMap(Collection<Quote> quotes) {
        Map<Integer, Collection<Quote>> quotesMap = new LinkedHashMap<>();
        int counter = 0;
        int count = quotes.size() / 4 + 1;
        List<Quote> quotesToAdd = new ArrayList<>();
        for (Quote quote : quotes) {
            quotesToAdd.add(quote);
            if (quotesToAdd.size() == 4) {
                quotesMap.put(counter, quotesToAdd);
                counter++;
                quotesToAdd = new ArrayList<>();
            }
        }
        if (quotesMap.size() < count) {
            quotesMap.put(counter, quotesToAdd);
        }
        return quotesMap;
    }
}

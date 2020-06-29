package pl.coderslab.webinars.goodthoughts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.webinars.goodthoughts.model.Quote;
import pl.coderslab.webinars.goodthoughts.model.QuoteCategory;
import pl.coderslab.webinars.goodthoughts.service.QuoteService;

@RequestMapping("")
@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final QuoteService quoteService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("quote", quoteService.getDefaultQuote());
        return "index";
    }

    @GetMapping("/{category}")
    public String getHomePage(@PathVariable String category, @RequestParam(required = false) String remember, Model model) {
        QuoteCategory quoteCategory = QuoteCategory.valueOf(category.toUpperCase());
        Quote quote = quoteService.getQuote(quoteCategory);
        if (remember != null) {
            quoteService.rememberQuote(quote);
        }
        model.addAttribute("quote", quote);
        return "index";

    }
}

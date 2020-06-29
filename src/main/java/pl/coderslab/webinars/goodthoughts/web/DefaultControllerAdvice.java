package pl.coderslab.webinars.goodthoughts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.webinars.goodthoughts.model.QuoteCategory;

import java.util.Collection;
import java.util.EnumSet;

@ControllerAdvice @RequiredArgsConstructor
public class DefaultControllerAdvice {

    @ModelAttribute("categories")
    public Collection<QuoteCategory> categories() {
        return EnumSet.allOf(QuoteCategory.class);
    }
}

package pl.coderslab.webinars.goodthoughts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service @Slf4j
public class TranslationService {

    @Cacheable(cacheNames = "translation", key = "#text")
    public String getTranslation(String text) {
        try {
            log.debug("Pobieram tłumaczenie dla tekstu: {}", text);
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse response = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://www.tlumaczangielskopolski.pl/wp-content/themes/translatica/inc/translate/translator.php?from=en&to=pl&text=" + URLEncoder.encode(text, "UTF-8")))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            String content = (String) response.body();
            log.debug("Odpowiedź z tłumaczeniem: {}", content);
            int indexOfBracketStart = content.indexOf("[");
            int indexOfBracketStop = content.lastIndexOf("]");
            return content.substring(indexOfBracketStart + 2, indexOfBracketStop - 1);
        } catch (IOException | InterruptedException | RuntimeException e) {
            log.error("Błąd pobierania tłumaczenia", e);
            return "Brak tłumaczenia";
        }
    }
}

package pl.coderslab.webinars.goodthoughts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching @EnableScheduling @Slf4j
public class GoodThoughtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodThoughtsApplication.class, args);
	}

	@Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(cacheNames = {"quotes", "pages"}, allEntries = true)
    public void invalidateDailyQuotes() {
	    log.warn("Wszystkie zapamiętane cytaty wyczyszczono");
    }

}

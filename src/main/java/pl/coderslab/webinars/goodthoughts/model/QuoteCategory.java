package pl.coderslab.webinars.goodthoughts.model;

public enum QuoteCategory {

    INSPIRING("Inspirujące", "Inspiring Quote of the day"),
    MANAGEMENT("Menedżerskie", "Management Quote of the day"),
    SPORT("Sportowe", "Sports Quote of the day"),
    LIFE("Życiowe", "Quote of the day about life"),
    FUNNY("Śmieszne", "Funny Quote of the day"),
    LOVE("O miłości", "Quote of the day about Love"),
    ART("O sztuce", "Art quote of the day"),
    STUDENTS("Studenckie", "Quote of the day for students");

    private final String description;
    private final String phrase;

    private QuoteCategory(String description, String phrase) {
        this.description = description;
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getDescription() {
        return description;
    }
}

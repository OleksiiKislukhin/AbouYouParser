package parser.service;

import org.jsoup.Jsoup;

import java.io.IOException;

public class JsonFromUrl {
    private static final String USER = "Mozilla";
    private static final String COOKIE_NAME = "_qwerty_";
    private static final String COOKIE_VALUE = "qwerty=219ffwef9w0f";
    private static final String REFERRER_VALUE = "https://www.aboutyou.de/c/maenner/bekleidung-20290";
    private static final int TIMEOUT_MS = 20000;

    public String getContent(String source) {
        try {
            return Jsoup.connect(source)
                    .ignoreContentType(true)
                    .userAgent(USER)
                    .cookie(COOKIE_NAME, COOKIE_VALUE)
                    .referrer(REFERRER_VALUE)
                    .followRedirects(true)
                    .timeout((int) (Math.random() * TIMEOUT_MS))
                    .execute()
                    .body();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
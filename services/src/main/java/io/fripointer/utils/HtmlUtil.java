package io.fripointer.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlUtil {
    
    public static Elements getElementsByTag(String tag, String html) {
        Document htmlDocument = Jsoup.parse(html);
        return htmlDocument.select(tag);
    }
    
    public static List<String> getImageSources(String html) {
        Elements images = getElementsByTag("img", html);
        return images.stream()
            .map(elem -> elem.attr("src"))
            .collect(Collectors.toList());
    }
    
}

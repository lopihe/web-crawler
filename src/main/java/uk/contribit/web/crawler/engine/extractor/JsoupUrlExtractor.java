package uk.contribit.web.crawler.engine.extractor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import uk.contribit.web.crawler.url.Url;

/**
 * URL extractor using Jsoup.
 *
 */
public class JsoupUrlExtractor implements UrlExtractor {

    @Override
    public List<Url> extract(Url url) throws IOException {
        Document doc = Jsoup.connect(url.getTarget()).get();
        Elements links = doc.select("a[href]");

        return links.stream()
                .map(e -> e.attr("abs:href"))
                .filter(target -> target != null && !target.isEmpty())
                .map(target -> Url.fromString(target))
                .collect(Collectors.toList());
        // TODO: Add img and import links
    }
}

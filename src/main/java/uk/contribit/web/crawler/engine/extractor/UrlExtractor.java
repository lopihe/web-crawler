package uk.contribit.web.crawler.engine.extractor;

import java.io.IOException;
import java.util.List;

import uk.contribit.web.crawler.url.Url;

public interface UrlExtractor {
    List<Url> extract(Url url) throws IOException;
}

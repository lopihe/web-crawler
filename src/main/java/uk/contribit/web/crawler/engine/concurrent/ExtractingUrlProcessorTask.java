package uk.contribit.web.crawler.engine.concurrent;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import uk.contribit.web.crawler.engine.extractor.UrlExtractor;
import uk.contribit.web.crawler.url.Url;

/**
 * URL processor task using an extractor to do the parsing.
 *
 */
public class ExtractingUrlProcessorTask implements Callable<Collection<Url>>, UrlProcessorTask {
    private final Url url;
    private final UrlExtractor extractor;

    @Inject
    public ExtractingUrlProcessorTask(Url url, UrlExtractor extractor) {
        this.url = url;
        this.extractor = extractor;
    }

    @Override
    public Collection<Url> call() throws IOException {
        return extractor.extract(url);
    }
}

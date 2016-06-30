package uk.contribit.web.crawler.engine.concurrent;

import javax.inject.Inject;

import uk.contribit.web.crawler.engine.extractor.UrlExtractor;
import uk.contribit.web.crawler.url.Url;

public class SimpleUrlProcessorTaskFactory implements UrlProcessorTaskFactory {
    private final UrlExtractor urlExtractor;

    @Inject
    public SimpleUrlProcessorTaskFactory(UrlExtractor urlExtractor) {
        this.urlExtractor = urlExtractor;
    }

    @Override
    public UrlProcessorTask create(Url url) {
        return new ExtractingUrlProcessorTask(url, urlExtractor);
    }

}

package uk.contribit.web.crawler.engine.concurrent;

import uk.contribit.web.crawler.url.Url;

public interface UrlProcessorTaskFactory {
    UrlProcessorTask create(Url url);
}

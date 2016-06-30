package uk.contribit.web.crawler.engine.concurrent;

import java.util.Collection;

import uk.contribit.web.crawler.url.Url;

/**
 * A task that is invoked for every URL that needs to be downloaded and parsed.
 *
 */
public interface UrlProcessorTask {

    /**
     * Extracts URLs from the page designated by the given url. Will not return
     * nulls,
     */
    Collection<Url> call() throws Exception;
}
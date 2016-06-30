package uk.contribit.web.crawler.engine;

import java.util.function.Predicate;

import uk.contribit.web.crawler.url.Url;
import uk.contribit.web.crawler.url.UrlVisitor;

/**
 * Crawls a tree of urls starting from a single URL.
 *
 */
public interface WebCrawler {
    /**
     * Starting from a URL maps out links contained in the pages. Processed URLs
     * are sent to a Visitor. This method can be called from multiple threads,
     * repeatedly.
     *
     * @param origin
     *            starting URL
     * @param followUrl
     *            filter to identify which URLs to follow
     * @param urlVisitor
     *            URL visitor to further process IR:s
     */
    void crawl(String origin, Predicate<Url> followUrl, UrlVisitor urlVisitor);

    /**
     * Shut down the crawler when it's not needed anymore.
     *
     * @return true if it was successful, false if other more radical measures
     *         need to be taken
     * @throws InterruptedException
     */
    boolean shutdown() throws InterruptedException;
}

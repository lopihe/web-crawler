package uk.contribit.web.crawler.engine;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import uk.contribit.web.crawler.engine.concurrent.UrlProcessorTaskFactory;
import uk.contribit.web.crawler.url.Url;
import uk.contribit.web.crawler.url.UrlVisitor;

/**
 * Concurrent web crawler implementation using and Executor.
 *
 */
public final class ConcurrentWebCrawler implements WebCrawler {
    private static final Logger LOG = Logger.getLogger(ConcurrentWebCrawler.class.getName());
    private static final long DEFAULT_SHUTDOWN_TIMEOUT_MS = 5000;
    private final ExecutorService executor;
    private final UrlProcessorTaskFactory taskFactory;

    @Inject
    public ConcurrentWebCrawler(ExecutorService executor, UrlProcessorTaskFactory taskFactory) {
        this.executor = executor;
        this.taskFactory = taskFactory;
    }

    @Override
    public void crawl(String start, Predicate<Url> followUrl, UrlVisitor urlVisitor) {
        BatchExecutor batchExecutor = new BatchExecutor(executor);
        ConcurrentHashMap<Url, Url> seenUrls = new ConcurrentHashMap<>();

        submit(Url.fromString(start), followUrl, seenUrls, urlVisitor, batchExecutor);
        batchExecutor.awaitCompletion();
    }

    @Override
    public boolean shutdown() throws InterruptedException {
        executor.shutdown();
        return executor.awaitTermination(DEFAULT_SHUTDOWN_TIMEOUT_MS, MILLISECONDS);
    }

    private void submit(Url startUrl, Predicate<Url> followUrl, ConcurrentMap<Url, Url> seenUrls,
            UrlVisitor urlVisitor, BatchExecutor batchExecutor) {

        if (!seen(startUrl, seenUrls)) {
            urlVisitor.visit(startUrl);
            if (followUrl.test(startUrl)) {
                batchExecutor.submit(() -> {
                    try {
                        for (Url url : taskFactory.create(startUrl).call()) {
                            submit(url, followUrl, seenUrls, urlVisitor, batchExecutor);
                        }
                    }
                        catch (Exception e) {
                            LOG.log(Level.SEVERE, "Cannot process " + startUrl, e);
                        }
                    });
            }
        }
    }

    private boolean seen(Url sUrl, ConcurrentMap<Url, Url> seenUrls) {
        // to avoid locking a new instance is created
        Url startUrl = Url.fromString(sUrl.getTarget());
        // and checked for identity rather than equality
        return seenUrls.computeIfAbsent(startUrl, k -> k) != startUrl;
    }
}

package uk.contribit.web.crawler.engine;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Matchers;
import org.mockito.Mockito;

import uk.contribit.web.crawler.engine.ConcurrentWebCrawler;
import uk.contribit.web.crawler.engine.concurrent.UrlProcessorTask;
import uk.contribit.web.crawler.engine.concurrent.UrlProcessorTaskFactory;
import uk.contribit.web.crawler.url.Url;
import uk.contribit.web.crawler.url.UrlVisitor;

public class ConcurrentWebCrawlerTest {
    private static final String VALID_URL_TARGET = "http://test.com";
    private static final String VALID_URL_TARGET_2 = "http://test.com/x";
    private static final Url VALID_URL = Url.fromString(VALID_URL_TARGET);
    private static final Url VALID_URL_2 = Url.fromString(VALID_URL_TARGET_2);

    @Rule
    public final Timeout timeout = Timeout.seconds(5);

    @Test
    public void testCrawl() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        UrlProcessorTaskFactory taskFactory = createTaskFactory(emptyList());

        ConcurrentWebCrawler crawler = new ConcurrentWebCrawler(executor, taskFactory);

        UrlVisitor visitor = Mockito.mock(UrlVisitor.class);
        crawler.crawl(VALID_URL_TARGET, url -> true, visitor);

        verify(visitor, times(1)).visit(eq(VALID_URL));
    }

    @Test
    public void testDuplicateUrl() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        UrlProcessorTaskFactory taskFactory = createTaskFactory(singletonList(VALID_URL));

        ConcurrentWebCrawler crawler = new ConcurrentWebCrawler(executor, taskFactory);

        UrlVisitor visitor = Mockito.mock(UrlVisitor.class);
        crawler.crawl(VALID_URL_TARGET, url -> true, visitor);

        verify(visitor, times(1)).visit(eq(VALID_URL));
    }

    @Test
    public void testTwoUrls() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        UrlProcessorTaskFactory taskFactory = createTaskFactory(singletonList(VALID_URL_2));

        ConcurrentWebCrawler crawler = new ConcurrentWebCrawler(executor, taskFactory);

        UrlVisitor visitor = Mockito.mock(UrlVisitor.class);
        crawler.crawl(VALID_URL_TARGET, url -> true, visitor);

        verify(visitor, times(1)).visit(eq(VALID_URL));
        verify(visitor, times(1)).visit(eq(VALID_URL_2));
    }

    @Test
    public void testNoFollowUrls() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        UrlProcessorTask urlProcessorTask = mock(UrlProcessorTask.class);

        UrlProcessorTaskFactory taskFactory = mock(UrlProcessorTaskFactory.class);
        when(taskFactory.create(Matchers.<Url> any())).thenReturn(urlProcessorTask);

        ConcurrentWebCrawler crawler = new ConcurrentWebCrawler(executor, taskFactory);

        UrlVisitor visitor = Mockito.mock(UrlVisitor.class);
        crawler.crawl(VALID_URL_TARGET, url -> false, visitor);

        verify(visitor, times(1)).visit(eq(VALID_URL));
        verifyZeroInteractions(urlProcessorTask);
    }

    private UrlProcessorTaskFactory createTaskFactory(List<Url> list) throws Exception {
        UrlProcessorTask urlProcessorTask = mock(UrlProcessorTask.class);
        when(urlProcessorTask.call()).thenReturn(list);

        UrlProcessorTaskFactory taskFactory = mock(UrlProcessorTaskFactory.class);
        when(taskFactory.create(Matchers.<Url> any())).thenReturn(urlProcessorTask);
        return taskFactory;
    }
}

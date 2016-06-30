package uk.contribit.web.crawler.app;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.contribit.web.crawler.app.WebCrawlerApp;
import uk.contribit.web.crawler.catalogue.UrlCatalogue;
import uk.contribit.web.crawler.catalogue.UrlCatalogueFactory;
import uk.contribit.web.crawler.engine.WebCrawler;

public class CrawlerAppTest {
    private static final String VALID_URL = "http://test.com";

    @Test
    public void shouldPrintCatalogue() throws InterruptedException {
        WebCrawler webCrawler = mock(WebCrawler.class);
        when(webCrawler.shutdown()).thenReturn(true);
        UrlCatalogue catalogue = mock(UrlCatalogue.class);
        UrlCatalogueFactory catalogueFactory = mock(UrlCatalogueFactory.class);
        when(catalogueFactory.create(any())).thenReturn(catalogue);

        WebCrawlerApp app = new WebCrawlerApp(webCrawler, catalogueFactory);
        app.run(VALID_URL);

        verify(webCrawler, times(1)).crawl(eq(VALID_URL), any(), eq(catalogue));
        verify(catalogue, times(1)).print();
    }
}

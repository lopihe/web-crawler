package uk.contribit.web.crawler.app.guice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.contribit.web.crawler.catalogue.SectionedCatalogueFactory;
import uk.contribit.web.crawler.catalogue.UrlCatalogueFactory;
import uk.contribit.web.crawler.engine.ConcurrentWebCrawler;
import uk.contribit.web.crawler.engine.WebCrawler;
import uk.contribit.web.crawler.engine.concurrent.SimpleUrlProcessorTaskFactory;
import uk.contribit.web.crawler.engine.concurrent.UrlProcessorTaskFactory;
import uk.contribit.web.crawler.engine.extractor.JsoupUrlExtractor;
import uk.contribit.web.crawler.engine.extractor.UrlExtractor;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Guice bindings and configuration for the whole application.
 *
 */
public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebCrawler.class).to(ConcurrentWebCrawler.class);
        bind(UrlProcessorTaskFactory.class).to(SimpleUrlProcessorTaskFactory.class);
        bind(UrlExtractor.class).to(JsoupUrlExtractor.class);
        bind(UrlCatalogueFactory.class).to(SectionedCatalogueFactory.class);
    }

    @Provides
    private ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(2);
    }
}

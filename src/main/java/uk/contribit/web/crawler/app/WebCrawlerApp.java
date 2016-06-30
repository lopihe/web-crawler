package uk.contribit.web.crawler.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import uk.contribit.web.crawler.app.guice.Module;
import uk.contribit.web.crawler.catalogue.UrlCatalogue;
import uk.contribit.web.crawler.catalogue.UrlCatalogueFactory;
import uk.contribit.web.crawler.engine.WebCrawler;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main entry point for web crawler application.
 *
 */
public class WebCrawlerApp {
    private static final Logger LOG = Logger.getLogger(WebCrawlerApp.class.getName());

    private final WebCrawler webCrawler;
    private final UrlCatalogueFactory catalogueFactory;

    @Inject
    public WebCrawlerApp(WebCrawler webCrawler, UrlCatalogueFactory catalogueFactory) {
        this.webCrawler = webCrawler;
        this.catalogueFactory = catalogueFactory;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());

        WebCrawlerApp app = injector.getInstance(WebCrawlerApp.class);
        if (args.length == 0) {
            LOG.severe("Please provide a starting url as an argument.");
            System.exit(1);
        }
        app.run(args[0]);
    }

    void run(String initialUrl) {
        UrlCatalogue urlCatalogue = catalogueFactory.create(initialUrl);
        LOG.info("Crawling website, starting at " + initialUrl);
        webCrawler.crawl(initialUrl, url -> url.isSameDomain(initialUrl), urlCatalogue);
        LOG.info("URLs found:");
        urlCatalogue.print();
        try {
            if (!webCrawler.shutdown()) {
                LOG.log(Level.WARNING, "Could not shut down engine, shutting down");
                System.exit(2);
            }
        } catch (InterruptedException e) {
            // Shutting down anyway
            LOG.log(Level.WARNING, "Shutdown interrupted, shutting down", e);
        }
    }
}

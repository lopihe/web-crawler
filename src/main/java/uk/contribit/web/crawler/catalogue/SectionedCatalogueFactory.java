package uk.contribit.web.crawler.catalogue;

public class SectionedCatalogueFactory implements UrlCatalogueFactory {
    @Override
    public UrlCatalogue create(String initialUrl) {
        return new SectionedCatalogue(initialUrl);
    }
}

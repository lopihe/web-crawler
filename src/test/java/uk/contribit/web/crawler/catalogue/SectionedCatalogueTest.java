package uk.contribit.web.crawler.catalogue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uk.contribit.web.crawler.catalogue.SectionedCatalogue.Section.EXTERNAL;
import static uk.contribit.web.crawler.catalogue.SectionedCatalogue.Section.INTERNAL;

import org.junit.Test;

import uk.contribit.web.crawler.catalogue.SectionedCatalogue;
import uk.contribit.web.crawler.url.Url;

public class SectionedCatalogueTest {
    private static final String URL_BASE = "http://test.com";
    private static final Url INTERNAL_URL = Url.fromString(URL_BASE + "/x");
    private static final Url EXTERNAL_URL = Url.fromString("http://tester.com");

    @Test
    public void testSectioning() {
        SectionedCatalogue cat = new SectionedCatalogue(URL_BASE);
        cat.visit(INTERNAL_URL);
        cat.visit(EXTERNAL_URL);

        assertNotNull(cat.getLinks());
        assertNotNull(cat.getLinks().values());
        assertEquals(2, cat.getLinks().values().size());

        assertNotNull(cat.getLinks().get(INTERNAL));
        assertEquals(INTERNAL_URL, cat.getLinks().get(INTERNAL).iterator().next());
        assertNotNull(cat.getLinks().get(EXTERNAL));
        assertEquals(EXTERNAL_URL, cat.getLinks().get(EXTERNAL).iterator().next());

    }
}

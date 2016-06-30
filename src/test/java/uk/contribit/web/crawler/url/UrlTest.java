package uk.contribit.web.crawler.url;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.contribit.web.crawler.url.Url;

public class UrlTest {
    private static final String VALID_URL = "http://test.com";
    private static final String INVALID_URL = "?testcom";

    @Test
    public void testValidUrl() throws Exception {
        Url.fromString(VALID_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUrl() throws Exception {
        Url.fromString(INVALID_URL);
    }

    @Test
    public void testIsSameDomain() throws Exception {
        assertTrue(Url.fromString(VALID_URL).isSameDomain(VALID_URL));
    }

    @Test
    public void testDifferntDomain() throws Exception {
        assertFalse(Url.fromString(VALID_URL).isSameDomain(INVALID_URL));
    }

}

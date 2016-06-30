package uk.contribit.web.crawler.catalogue;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.contribit.web.crawler.url.Url;

/**
 * Catalogue that does categorisation of the URLs based on
 * internal/external/static classes.
 *
 *
 */
public class SectionedCatalogue implements UrlCatalogue {
    private final Map<Section, Collection<Url>> sectionedLinks = new ConcurrentHashMap<>();
    private final Map<Section, Collection<Url>> visibleLinks = Collections.unmodifiableMap(sectionedLinks);
    private final String initialUrl;

    SectionedCatalogue(String initialUrl) {
        this.initialUrl = initialUrl;
    }

    @Override
    public void visit(Url url) {
        Section section = determineSection(url);
        Collection<Url> urlSection = sectionedLinks.computeIfAbsent(section, k -> new ConcurrentLinkedQueue<>());
        urlSection.add(url);
    }

    @Override
    public void print() {
        for (Section section : sectionedLinks.keySet()) {
            System.out.println(section + " links:");
            for (Url url : sectionedLinks.get(section)) {
                System.out.println(url);
            }
            System.out.println();
        }
    }

    public Map<Section, Collection<Url>> getLinks() {
        return visibleLinks;
    }

    private Section determineSection(Url url) {
        Section section;
        if (url.isSameDomain(initialUrl)) {
            section = Section.INTERNAL;
        } else if (url.isFile()) {
            section = Section.STATIC;
        } else {
            section = Section.EXTERNAL;
        }
        return section;
    }

    public static enum Section {
        INTERNAL, EXTERNAL, STATIC
    }
}

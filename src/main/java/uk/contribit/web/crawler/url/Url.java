package uk.contribit.web.crawler.url;

import java.net.MalformedURLException;
import java.net.URL;

public final class Url {
    private final String target;

    public static Url fromString(String target) {
        try {
            URL u = new URL(target);
            // TODO: handle ports
            return new Url(u.getProtocol() + "://" + u.getHost() + u.getPath());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Url(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void accept(UrlVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isSameDomain(String initialUrl) {
        // TODO: parse initialUrl and compare properly (no ports, etc)
        return target.startsWith(initialUrl);
    }

    public boolean isFile() {
        // TODO: implement
        return false;
    }

    @Override
    public int hashCode() {
        return target == null ? 0 : target.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Url other = (Url) obj;
        if (target == null) {
            if (other.target != null) {
                return false;
            }
        } else if (!target.equals(other.target)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return target;
    }

}

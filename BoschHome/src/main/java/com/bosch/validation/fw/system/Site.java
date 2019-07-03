package com.bosch.validation.fw.system;

import java.util.Locale;
import java.util.Set;


/**
 * Base interface for the definition of a web-site. It specified base url, admin and test users, available languages,
 * and also functions for accessing login and other site specific information.
 */
public interface Site {

    /**
     * returns the fully qualified base url of the site, e.g. "http://www.boschrexroth.com/en/xc/". This will be taken
     * for basic connect to the site as well as for matching urls for crawling purposes
     *
     * @return the url as {@link String}
     */
    String baseUrl();

    /**
     * returns a {@link Set} of {@link Locale}s defining the available languages of the site. This set must return at
     * least a single Locale as default language.
     *
     * @return {@link Set} of {@link Locale}
     */
    Set<Locale> availableLanguages();
}

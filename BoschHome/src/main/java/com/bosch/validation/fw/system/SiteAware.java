package com.bosch.validation.fw.system;

/**
 * Common interface definition for a page object that offers to set and get the corresponding site.
 */
public interface SiteAware {

    /**
     * returns the {@link Site} this object belongs to.
     * 
     * @return the {@link Site} instance
     */
    public Site getSite();

    /**
     * Sets the {@link Site} this object belongs to.
     * 
     * @param site
     *            the {@link Site} instance
     */
    public void setSite(Site site);
}

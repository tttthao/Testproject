package com.bosch.validation.fw.setup;

import org.openqa.selenium.WebDriver;

public interface PageObject {

    public abstract <P extends PageObject> P initPageObject(Class<P> pageObjectClass);

    public abstract boolean isReady();

    public abstract WebDriver getDriver();
}
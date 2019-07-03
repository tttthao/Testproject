package com.bosch.validation.fw.exception;

import org.openqa.selenium.WebDriver;

public class PageObjectInitializationFailedException extends IllegalStateException {

    private static final long serialVersionUID = 7914302132738071603L;

    public PageObjectInitializationFailedException() {
        super();
    }

    public PageObjectInitializationFailedException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PageObjectInitializationFailedException(String arg0) {
        super(arg0);
    }

    public PageObjectInitializationFailedException(Throwable arg0) {
        super(arg0);
    }

    public PageObjectInitializationFailedException(Class<?> pageObjectClass, Throwable arg0) {
        super("PageObject '" + pageObjectClass.getName() + "' initialization failed: " + arg0.getMessage(), arg0);
    }

    public PageObjectInitializationFailedException(WebDriver driver, Class<?> pageObjectClass, Throwable arg0) {
        super("PageObject '" + pageObjectClass.getName() + "' initialization failed on page '" + driver.getTitle()
                + "' with url: " + driver.getCurrentUrl() + ": " + arg0.getMessage(), arg0);
    }

}

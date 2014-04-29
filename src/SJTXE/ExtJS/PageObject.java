package SJTXE.ExtJS;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import SJTXE.Configuration;
import SJTXE.Utils;

/**
 * Base class for page objects. Focus is on single page application with lots of AJAX udpates.
 * 
 * @author huguogang
 *
 */
public class PageObject {
    //TODO: root of the page might be a configuration
    // then we can easily configure testing of staging or production hosts
    
    protected WebDriver _driver;
    protected String _url;
    public String getURL() {
        return _url;
    }
    protected WebDriverWait _wait;
    
    public void setImplicitWait(int waitSec) {
        _wait = (WebDriverWait) new WebDriverWait(_driver, waitSec)
                    .ignoring(WebDriverException.class);
    }
    
    public boolean isLoaded() {
        String url = _driver.getCurrentUrl();
        return url.toUpperCase().equalsIgnoreCase(_url);
    }
    
    public PageObject(WebDriver driver,String url) throws IOException {
        _driver = driver;
        _url = url;
        setImplicitWait(Configuration.getInstance().getImplicitWait());
    }
    /**
     * Ask the browser to load this page.
     * @throws IOException 
     * @throws InterruptedException 
     */
    public void load() throws IOException, InterruptedException {
        _driver.get(_url);
        Utils.waitForExtReady(_driver);
        Utils.injectSJTXE(_driver);
        //Often times, the page will get some initial configuration before it's ready.
        Utils.waitForAjax(_driver);
    }
}

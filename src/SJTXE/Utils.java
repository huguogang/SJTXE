package SJTXE;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Utility functions.
 * 
 * @author huguogang
 *
 */
public final class Utils {
    /**
     * Ensure string is legal Windows/Mac file name. All illegal
     * characters will be replaced.
     * 
     * @param filename    Proposed file name that might need clean up
     * @return A string that can be used as file name
     */
    public static String ensureLegalFileName(String filename) {
        String fn = filename.replace("\\", "%5C")
                    .replace("/", "-")
                    .replace(":", "-")
                    .replace("?", "-")
                    .replace("*", "-")
                    .replace("<", "-")
                    .replace(">", "-")
                    .replace("|", "-")
                    .replace("\"", "-")
                    .replace(",", "-")
                    .replace(";", "-");
        return fn;
    }
    
    /**
     * Inject underscore to client side, if it is not already used by the current page.
     * 
     * @param driver
     * @throws IOException
     */
    public static void injectUnderscore(WebDriver driver) throws IOException {
        injectLibHelper(driver, "return this._ === undefined;", "underscore.js");
    }
    
    /**
     * Inject jQuery to client side if not already used by the current page.
     * 
     * @param driver
     * @throws IOException
     */
    public static void injectJQuery(WebDriver driver) throws IOException {
        injectLibHelper(driver, "return this.$ === undefined", "jquery.js");
    }
    
    /**
     * Inject utility JS functions to the client side.
     * 
     * @param driver
     * @throws IOException
     */
    public static void injectSJTXE(WebDriver driver) throws IOException {
        //dependencies
        injectJQuery(driver);
        injectUnderscore(driver);
        //no need to check, assume it's never loaded
        injectLibHelper(driver, "return true;", "SJTXE.js");
    }
    
    private static void injectLibHelper(WebDriver driver, String js, 
            String libFilename) throws IOException {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        boolean needInjection = (boolean) (je.executeScript(js));
        if(needInjection) {
            URL u = Resources.getResource(Utils.class, libFilename);
            js = Resources.toString(u, Charsets.UTF_8);
            je.executeScript(js);
        }
    }
    
    /**
     * Wait until all ExtJS Ajax call are finished.
     * 
     * @param driver
     * @throws InterruptedException
     * @throws IOException
     */
    public static void waitForAjax(WebDriver driver) 
            throws InterruptedException, IOException{
        waitForAjax(driver, Configuration.getInstance().getAjaxWait());
    }
    /**
     * Wait until all ExtJS Ajax call are finished. 
     * 
     * @param driver
     * @param timeOutInSeconds
     * @throws InterruptedException
     */
    public static void waitForAjax(WebDriver driver, int timeOutInSeconds) 
            throws InterruptedException {
        //this might be called right after a user key press or mouse click
        //we should give brower sometime to react to user action and start Ajax call
        Thread.sleep(500);
        (new WebDriverWait(driver, timeOutInSeconds))
            .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (Boolean) ((JavascriptExecutor) d)
                        .executeScript("return !SJTXE.hasActiveAjaxCalls();");
            }
        });
        //extra time for ExtJS to digest data and update GUI
        Thread.sleep(2000);
    }
    
    public static ArrayList<Object> getStoreData(WebDriver driver, String storeID) {
        @SuppressWarnings(value="unchecked")
        ArrayList<Object> ret = (ArrayList<Object>) ((JavascriptExecutor) driver)
            .executeScript("return SJTXE.getStoreData(arguments[0]);", storeID);
        return ret;
    }
    
    public static void waitForExtReady(WebDriver driver) throws IOException {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, Configuration.getInstance().getImplicitWait())
            .ignoring(WebDriverException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (Boolean) ((JavascriptExecutor) d)
                        .executeScript("return Ext.isReady;");
            }
        });
    }
    
    public static Boolean hasJSError(WebDriver driver) {
        return (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return SJTXE.hasExceptions();");
    }
    
    public static Boolean hasAjaxError(WebDriver driver) {
        return (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return SJTXE.hasAjaxFailure();");
    }
}

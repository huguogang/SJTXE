package SJTXE.ExtJS.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import SJTXE.Configuration;

import com.google.common.base.Function;

/**
 * Base class for all ExtJS components.
 * TODO: more component query strategy, e.g. xPath 
 * @author huguogang
 * 
 */
public abstract class Component implements WebElement {
    protected WebDriver _driver;
    /**
     * ExtJS component query that can uniquely identify this component.
     */
    protected String _query;
    protected String _HTMLID;
    protected WebElement _element;
    protected Component _parent = null;

    public Component(WebDriver driver, String query, Component parent) {
        _driver = driver;
        _query = query;
        _parent = parent;
    }

    /**
     * Get the WebElement for the ExtJS component
     * 
     * @return
     * @throws IOException
     */
    public WebElement getElement() {
        // for some reason, direct returning
        // "Ext.ComponentQuery.query(query)[0]"
        // does not work. will cause javascript timeout in browsers
        // so will use ExtJS to get ID, then use Selenium to get WebElement
        waitForRendered();
        String id = waitForComponent();
        _element = _driver.findElement(By.id(id));
        return _element;
    }

    /**
     * Get child of the component. Assuming one and only one match.
     * 
     * @param componentQuery
     *            ExtJS component query relative to this component (similar to
     *            .down method)
     * @return The first children that matches the component query
     */
    public WebElement getChild(String componentQuery) {
        waitForRendered();
        String query = getFullQuery() + " " + componentQuery;
        String js = "return Ext.ComponentQuery.query(\"" + query + "\")[0].id;";
        String id = (String) ((JavascriptExecutor) _driver).executeScript(js);
        return _driver.findElement(By.id(id));
    }

    /**
     * wait until component is rendered in browser
     * 
     * @throws IOException
     */
    public void waitForRendered() {
        FluentWait<Component> wait = new FluentWait<Component>(this);
        wait.withTimeout(Configuration.getInstance().getImplicitWait(),
                TimeUnit.SECONDS).ignoring(WebDriverException.class)
                .until(new Function<Component, Boolean>() {
                    public Boolean apply(Component c) {
                        String js = "return Ext.ComponentQuery.query(\""
                                + c.getFullQuery() + "\")[0].rendered;";
                        return (Boolean) ((JavascriptExecutor) _driver)
                                .executeScript(js);
                    }
                });
    }

    /**
     * Wait until component is available (Notice even if ExtJS object is
     * created, it may not necessarily been rendered)
     * 
     * @return id of the component
     * @throws InterruptedException
     * @throws IOException
     */
    public String waitForComponent() {
        try {
            FluentWait<Component> wait = new FluentWait<Component>(this);
            String ret = wait
                    .withTimeout(Configuration.getInstance().getImplicitWait(),
                            TimeUnit.SECONDS)
                    .ignoring(WebDriverException.class)
                    .until(new Function<Component, String>() {
                        public String apply(Component c) {
                            String js = "return Ext.ComponentQuery.query(\""
                                    + c.getFullQuery() + "\")[0].id;";
                            return (String) ((JavascriptExecutor) _driver)
                                    .executeScript(js);
                        }
                    });
            // slow down to human speed. otherwise, test result is very
            // sensitive to timing
            Thread.sleep(100);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void click() {
        getElement().click();
    }

    @Override
    public void submit() {
        getElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getElement().sendKeys(keysToSend);

    }

    @Override
    public void clear() {
        getElement().clear();
    }

    @Override
    public String getTagName() {
        return getElement().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return getElement().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    @Override
    public String getText() {
        return getElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getElement().getSize();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getElement().getCssValue(propertyName);
    }

    /**
     * helper, find IinputEl which is usually the WebElement that can access key
     * or mouse
     * 
     * @return WebElement for the inputEl
     */
    protected WebElement getInputEl() {
        waitForRendered();
        String js = "return Ext.ComponentQuery.query(\"" + getFullQuery()
                + "\")[0].inputEl.id;";
        String id = (String) ((JavascriptExecutor) _driver).executeScript(js);
        return _driver.findElement(By.id(id));
    }

    /**
     * run a method call on this component
     * 
     * @param methodLiteral  An string literal to be called. Example: setValue('newValue')
     * @return The object returned from method
     */
    protected Object callMethod(String methodLiteral) {
        String js = "Ext.ComponentQuery.query(\"" + getFullQuery() + "\")[0]."
                + methodLiteral + ";";
        return ((JavascriptExecutor) _driver).executeScript(js);
    }

    /**
     * Full component query for this component.
     * http://developertips.blogspot.com/2013/09/locate-extjs-component-selenium-web.html
     * 
     * @return
     */
    protected String getFullQuery() {
        String query = _query;
        Component parent = _parent;
        while (parent != null) {
            query = parent._query + " " + query;
            parent = parent._parent;
        }
        return query;
    }
}
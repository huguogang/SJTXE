package test.ExtJS.examples.xmlform;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import SJTXE.JUnit.TestCase;
/**
 * Test case from ExtJS sample: XML Form
 * 
 * @author huguogang
 *
 */
public class XMLFormTest extends TestCase {
    private static Iterable<Object[]> _browsers;
    private Page _page;

    @Parameters(name = "Browser {index}: {0}")
    public static Iterable<Object[]> getBrowsers() {
        _browsers = _getBrowsers();
        return _browsers;
    }

    public XMLFormTest(String browser, WebDriver driver) {
        super(browser, driver);
    }

    @Override
    protected void initPageObject() {
        try {
            String url = "http://docs.sencha.com/extjs/4.2.2/extjs-build/examples/form/xml-form.html";
            //strong typed page object
            _page = new Page(_driver, url);
            //page object used by the base class
            _pageObject = _page;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void oneTimeTearDown() {
        for (Object[] b : _browsers) {
            WebDriver d = (WebDriver) b[1];
            d.quit();
        }
    }

    @Test
    public void loadTest() {
        System.out.println("load test");
        _page.loadButton.click();
    }

    @Test
    public void submitTest() {
        System.out.println("submit test");
    }
}

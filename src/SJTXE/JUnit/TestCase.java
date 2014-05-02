package SJTXE.JUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import SJTXE.Configuration;
import SJTXE.Utils;
import SJTXE.ExtJS.PageObject;

import com.opera.core.systems.OperaDriver;
/**
 * Base class for all JUnit test cases.
 * TODO: I use Parameterized runner to enable testing multiple browsers using 
 *       a single test case. It might better be served by a custom runner?
 * 
 * @author huguogang
 *
 */
@RunWith(Parameterized.class)
public abstract class TestCase {
	/**
	 * test configuration shared by all cases
	 */
	protected static Configuration getConfig() {
		return Configuration.getInstance();
	}

	protected static Iterable<Object[]> _getBrowsers() {
		ArrayList<Object[]> browsers = new ArrayList<Object[]>();
		WebDriver driver;
		for (String browser : getConfig().getBrowsers()) {
			switch (browser) {
			case "InternetExplorer":
			    //  http://developertips.blogspot.com/2013/10/remeber-to-reset-ie-zoom-selenium-web.html
				//  commented out the lines below, because
				// 	seems like is zoom level is not 100%, there are some problems
				//  in web driver (button clicks are missing). So we should not ignore
				//	this warning.
				
				// if not set, IE may have problem starting if user previous visited 
			    // the page under test, and had a zooming level that is not 100%
				//DesiredCapabilities caps = DesiredCapabilities
				//		.internetExplorer();
				//caps.setCapability("ignoreZoomSetting", true);
				//driver = new InternetExplorerDriver(caps);
				driver = new InternetExplorerDriver();
				break;
			case "Firefox":
				driver = new FirefoxDriver();
				break;
			case "Chrome":
				driver = new ChromeDriver();
				break;
			case "Safari":
				driver = new SafariDriver();
				break;
			case "Opera":
			    driver = new OperaDriver();
			    break;
			default:
				throw new IllegalArgumentException(
						"Unknow browser in Configuration: " + browser);
			}
			//it is usually desirable to maximize browser window:
			// http://developertips.blogspot.com/2013/10/always-maximize-browser-window-selenium.html
			driver.manage().window().maximize();
			driver.manage()
					.timeouts()
					.implicitlyWait(getConfig().getImplicitWait(),
							TimeUnit.SECONDS);
			browsers.add(new Object[] { browser, driver });
		}
		return browsers;
	}
	
	/**
	 * expose test name
	 */
	@Rule
	public TestName name = new TestName();
	
	@Rule
	public TestRule rule = new TestRule(this, getConfig().getScreenshotFolder());
	/**
	 * Page under test. Assuming single page applications.
	 */
	protected PageObject _pageObject;

	protected WebDriver _driver;	
	public WebDriver getDriver() {
		return _driver;
	}
	
	protected String _browserName;
	public String getBrowserName() {
		return _browserName;
	}
	
	public TestCase(String browserName, WebDriver driver) {
		_driver = driver;
		_browserName = browserName;
		initPageObject();
	}
	
	@Before
	public void setup() throws InterruptedException, IOException {
		_pageObject.load();		
	}
	
	@After
	public void tearDown() {
		assertThat("No AJAX error", Utils.hasAjaxError(_driver), equalTo(false));
		assertThat("No JavaScript error", Utils.hasJSError(_driver), equalTo(false));
	}
	/**
	 * load page that is the test target and make sure it's in a known state
	 * for example: user authentication normally should be handled here
	 */
	protected abstract void initPageObject();	
}

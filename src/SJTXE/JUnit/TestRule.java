package SJTXE.JUnit;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import SJTXE.Utils;

/**
 * JUnit test rule: 
 *  - take screenshot on failure
 *  - ...
 *  
 * @author huguogang
 *
 */
public class TestRule extends TestWatcher {
	protected TestCase _test;
	protected String _folder;
	public TestRule(TestCase test, String folder) {
		_test = test;
		_folder = folder;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		super.failed(e, description);
		String name = _test.name.getMethodName();
		name += "(" + _test.getClass().getSimpleName() + ")"; 
		name = Utils.ensureLegalFileName(name);
		name = _folder + File.separator + name + ".png";
		
		//take a screenshot
		File screenshot = ((TakesScreenshot)_test.getDriver())
		        .getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(name));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}

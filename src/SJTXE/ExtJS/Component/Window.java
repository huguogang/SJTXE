package SJTXE.ExtJS.Component;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Wrapper class for ExtJS Window component.
 * 
 * @author huguogang
 *
 */
public class Window extends Component {
	public Window(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	public boolean isHidden() {
	    //The WebElement might not exist, but ExtJS component should already been there
		String js = "return Ext.ComponentQuery.query(\"" + getFullQuery() + "\")[0].isHidden();";
		return (boolean) ((JavascriptExecutor) _driver).executeScript(js);
	}
	
	public boolean isVisible() {
	    //The WebElement might not exist if the component is not rendered,
	    //but ExtJS component should already been there
		String js = "return Ext.ComponentQuery.query(\"" + getFullQuery() + "\")[0].isVisible();";
		return (boolean) ((JavascriptExecutor) _driver).executeScript(js);
	}
	
	public void close() {
	    //TODO: close this window
		throw new NotImplementedException();
	}
}

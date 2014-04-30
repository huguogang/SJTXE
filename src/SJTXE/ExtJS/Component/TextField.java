package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;

/**
 * Wrapper class for ExtJS TextField component.
 * 
 * @author huguogang
 *
 */
public class TextField extends Component {
	
	public TextField(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getInputEl().sendKeys(keysToSend);
	}
}

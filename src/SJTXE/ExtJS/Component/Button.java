package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;
/**
 * Wrapper for ExtJS button component.
 * 
 * @author huguogang
 *
 */
public class Button extends Component {
	public Button(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
}

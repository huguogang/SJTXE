package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;

public class TextField extends Component {
	
	public TextField(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getInputEl().sendKeys(keysToSend);
	}
}

package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;

public class TextAreaField extends Component {
	public TextAreaField(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getInputEl().sendKeys(keysToSend);
	}	
	
	@Override
	public void clear() {
		getInputEl().clear();
	}
}

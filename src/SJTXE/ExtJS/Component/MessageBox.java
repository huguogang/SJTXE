package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;

public class MessageBox extends Component {
	protected Button _btnYes;

	public MessageBox(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
		initChildren();
	}

	public static void clickYes(WebDriver driver) {
	    //assuming a singleton messagebox control
		MessageBox b = new MessageBox(driver, "messagebox", null);
		b.clickYes();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getInputEl().sendKeys(keysToSend);
	}

	@Override
	public void clear() {
		getInputEl().clear();
	}

	public void clickYes() {
		_btnYes.click();
	}

	private void initChildren() {
		_btnYes = new Button(_driver, "button[text='Yes']", this);
	}
}

package SJTXE.ExtJS.Component;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Wrapper class for ExtJS combo component
 * 
 * @author huguogang
 *
 */
public class ComboBox extends Component {
	
	public ComboBox(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		getInputEl().sendKeys(keysToSend);
	}
	
	/**
	 * submit by pressing enter in the combobox
	 * 
	 * @throws InterruptedException 
	 */
	public void sendEnterToSumbit() throws InterruptedException {
		sendKeys(Keys.ENTER);
		//combobox may take some delay to register the selected item
		Thread.sleep(500);
		try {
			sendKeys(Keys.ENTER);
		} catch (Exception ex) {
			// ignore, sometimes the first enter is enough to dismiss
			// the dialog
		}
	}
}

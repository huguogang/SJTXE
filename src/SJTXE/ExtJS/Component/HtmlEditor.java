package SJTXE.ExtJS.Component;

import org.openqa.selenium.WebDriver;

public class HtmlEditor extends Component {
	
	public HtmlEditor(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}
	
	
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		//TODO: IE, FF, Chrome may be different, use setValue for now if want consistent result
		//IE used IFrame here
		getInputEl().sendKeys(keysToSend);
	}	
	
	@Override
	public void clear() {
		//TODO: IE, FF, Chrome may be different, use setValue for now if want consistent result
		//IE used IFrame here
		getInputEl().clear();
	}
	
	public void reset() {
	    //use JS call
		this.callMethod("reset()");
	}
	/**
	 * Use JS call to setValue on ExtJS component.
	 * 
	 * @param val
	 */
	public void setValue(String val) {
		String escaped = val.replaceAll("\"", "\\\"");
		this.callMethod("setValue(\"" + escaped + "\")");
	}
}

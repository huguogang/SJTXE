package SJTXE.ExtJS.Component;

import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Grid extends Component {
	public Grid(WebDriver driver, String componentQuery, Component parent) {
		super(driver, componentQuery, parent);
	}

	public Map<String, Object> getSelectedData() {
		//wait for element exists
		getElement();
		String js = "return Ext.ComponentQuery.query('" + getFullQuery()
				+ "')[0].getSelectionModel().getSelection()[0].data";
		@SuppressWarnings(value="unchecked")
		Map<String, Object> row = (Map<String, Object>)((JavascriptExecutor) _driver).executeScript(js);
		return row;
	}
	
	public ArrayList<Object> getStoreData() {
		//TODO: not implement yet, .getStore().data?
		return null;
	}
	
	public void selectRow(int rowNumber) {
		//TODO: not implemented yet
	    throw new NotImplementedException();
	}
}

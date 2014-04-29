package test.ExtJS.examples.xmlform;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import SJTXE.ExtJS.PageObject;
import SJTXE.ExtJS.Component.Button;
import SJTXE.ExtJS.Component.ComboBox;
import SJTXE.ExtJS.Component.DateField;
import SJTXE.ExtJS.Component.TextField;

/**
 * Page model for the ExtJS example: XML Form.
 * 
 * @author huguogang
 *
 */
public class Page extends PageObject{
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField companyField;
    public TextField emailField;
    public ComboBox stateField;
    public DateField dobField;
    public Button loadButton;
    public Button submitButton;
    
    public Page(WebDriver driver, String url) throws IOException {
        super(driver, url);
        initChildren();
    }
    
    private void initChildren() {
        firstNameField = new TextField(_driver, "textfield[name=first", null);
        lastNameField = new TextField(_driver, "textfield[name=last", null);
        companyField = new TextField(_driver, "textfield[name=company", null);
        emailField = new TextField(_driver, "textfield[name=email", null);
        stateField = new ComboBox(_driver, "combobox[name=state]", null);
        dobField = new DateField(_driver, "datefield[name=dob]", null);
        loadButton = new Button(_driver, "button[text=Load]", null);
        submitButton = new Button(_driver, "button[text=Submit]", null);
        
    }
    
    //TODO: wrapper methods which should abstract away deep knowledge of page organization
    //so that tests can focus on higher level objectives
}

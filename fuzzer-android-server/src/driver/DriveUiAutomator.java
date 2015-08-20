package driver;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * This class actually calls the UI interactions using UiAutomator.
 * @author Kees Lampe
 *
 */
public class DriveUiAutomator extends UiAutomatorTestCase {
	
	private UiDevice device;
	
	public DriveUiAutomator(UiDevice uiDevice) {
		device = uiDevice;
	}

	/**
	 * Clicks on the element with the given id
	 * @param id The id of the element to click on
	 * @return Returns the id of the element that was clicked
	 * @throws UiObjectNotFoundException when the element could not be found
	 */
	public String click(String id) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().resourceId(id));
		object.clickAndWaitForNewWindow();
		return id;
	}
	
	/**
	 * Click on the UiObject with given id if it is enabled, else press the back button.
	 * @param id
	 * @return Whether the UiObject is pressed or the back button is return as a String.
	 * @throws UiObjectNotFoundException
	 */
	public String tryClickElseBack(String id) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().resourceId(id));
		if(object.isEnabled()){
			object.clickAndWaitForNewWindow();
			return id;
		}
		else {
			device.pressBack();
			return "Not Enabled back pressed";
		}	
	}
	
	/**
	 * Click on the parent of the element with the text text.
	 * @param text
	 * @return the set text.
	 * @throws UiObjectNotFoundException
	 */
	public String clickParent(String text) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().childSelector(new UiSelector().text(text)));
		object.clickAndWaitForNewWindow();
		return text;
	}
	
	/**
	 * Click on the UiObject that has the text text.
	 * @param text
	 * @return the set text.
	 * @throws UiObjectNotFoundException
	 */
	public String clickText(String text) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().text(text));
		object.click();
		return text;
	}
	
	/**
	 * Insert input in object with id id and press enter.
	 * @param id
	 * @param input
	 * @return the set text.
	 * @throws UiObjectNotFoundException
	 */
	public String insertEnter(String id, String input) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().resourceId(id));
		object.setText(input);
		device.pressEnter();		
		return object.getText();
	}
	
	/**
	 * Insert the given string in element with the given id.
	 * @param id
	 * @param input 
	 * @return The set text
	 * @throws UiObjectNotFoundException
	 */
	public String insert(String id, String input) throws UiObjectNotFoundException {
		UiObject object = new UiObject(new UiSelector().resourceId(id));
		object.setText(input);
		return object.getText();
	}
	
	public String enter() {
		device.pressEnter();
		return "enter pressed";
	}
	
	public String back() {
		device.pressBack();
		return "back pressed";
	}
	
	/**
	 * This is a stub method; if the UiAutomatorTestCase does not have any test methods,
	 * the setUp method will not be ran. This problem stems from the onorthodox use of the
	 * UiAutomator function.
	 */
	public void test() {
		System.out.println("Stub test method; else, the setUp refuses to run.");
		System.out.println("Now shutting down.");
	}
}

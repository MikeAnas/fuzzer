package driver;

import util.Property;

import com.android.uiautomator.testrunner.UiAutomatorTestCase; 

import communication.ActionInstruction;
import communication.ServerSocketWrapper;

/**
 * This class sets up UiAutomator and the SeverSocketWrapper.
 * It makes sure the Server keeps listing to the client.
 * @author Kees Lampe
 *
 */
public class SetUpUiAutomator extends UiAutomatorTestCase {
	
	private static Property property = Property.getInstance();
	private static final int CONNECTION_PORT = property.getInt("port");
	private ServerSocketWrapper ssw;
	private DriveUiAutomator driver;
	
	/**
	 * Setup the ServerSocketWrapper. And start listing to it.
	 */
	public void setUp() {		
		ssw = new ServerSocketWrapper(CONNECTION_PORT);
		driver = new DriveUiAutomator(getUiDevice());
		this.listenToClient();
		ssw.close();
	}
	
	/**
	 * Keep listing to the client.
	 * If the client sends something handle this action and return the response.
	 */
	public void listenToClient() {
		ActionInstruction act;
		while((act = ssw.receiveFromClient()) != null)
		{
			String response = handleAction(act);
			ssw.sendToClient(response);
		}
	}
	
	/**
	 * Sends the given actionInstruction to the driver
	 * @param act The actionInstruction to be dispatched
	 * @return Returns the results of the dispatch action
	 */
	public String handleAction(ActionInstruction act) {
		return act.dispatch(driver);
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

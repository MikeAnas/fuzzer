package communication;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import driver.DriveUiAutomator;

/**
 * Same class as the Client has to send these object. 
 * Only this has the extra method dispatch().
 * Dispatch sends invokes the method and parameters on DriveUiAutomator as set in this object.
 * @author Kees Lampe
 *
 */
public class ActionInstruction implements Serializable{
	
	static final long serialVersionUID = 123;
	
	private String methodName;
	private String[] parameters;
	
	public ActionInstruction(String methName, String[] params) {
		methodName = methName;
		parameters = params;
	}
	
	/**
	 * Dispatch sends invokes the method and parameters on DriveUiAutomator as
	 * set in this object.
	 * 
	 * @param driver
	 * @return A string response form the method invoked or null if an exception occured.
	 */
	public String dispatch(DriveUiAutomator driver) {
		try {
			if (parameters.length == 0) {
				Method method = driver.getClass().getMethod(methodName);
				return (String) method.invoke(driver);
			} else {
				Method method = driver.getClass().getMethod(methodName,
						getParameterTypes());
				return (String) method.invoke(driver, (Object[]) parameters);
			}
		} catch (NoSuchMethodException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * getParameterTypes is called to get the types of the parameters this is needed to invoke the right methods.
	 * @return String[] with all parameter Types of the parameters.
	 */
	@SuppressWarnings("rawtypes")
	private Class[] getParameterTypes(){
		Class[] parameterTypes;
		int size = parameters.length;
		parameterTypes = new Class[size];
		for(int i = 0; i < size; i++)
			parameterTypes[i] = (parameters[i]).getClass();
		return parameterTypes;
	}
	
}

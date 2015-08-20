package communication;

import java.io.Serializable;

/**
 * This class forms objects that can be send to the server.
 * The Server has the same class and is able to translate this object to a UI instruction.
 * @author Kees Lampe
 *
 */
public class ActionInstruction implements Serializable{
	
	//serialVersionUID needs to be set and the same as the ActionInstruction class from the server.
	static final long serialVersionUID = 123;
	
	//Not all variables are used locally, but are needed to reflect the actionInstruction
	//class as present in the fuzzer-android-server-project
	private String methodName;
	private String[] parameters;
	private boolean send;
	private String toString;
	
	public ActionInstruction(String methName, String[] params, boolean send, String toString) {
		this.methodName = methName;
		this.parameters = params;
		this.send = send;
		this.toString = toString;
	}
	
	/**
	 * Returns whether this action should be sent or not.
	 * This depends on the random generator that decides
	 * which actions should be actually executed
	 */
	public boolean getSend(){
		return send;
	}
	
	public String toString(){
		return toString;
	}
}

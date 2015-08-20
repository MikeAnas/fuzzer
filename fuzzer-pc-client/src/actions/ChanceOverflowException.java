package actions;

/**
 * This exception is thrown whenever something went wrong with the 
 * chances of an input.
 * @author Michel
 *
 */
@SuppressWarnings("serial")
public class ChanceOverflowException extends Exception {

	public ChanceOverflowException(String message) {
		super(message);
	}

}

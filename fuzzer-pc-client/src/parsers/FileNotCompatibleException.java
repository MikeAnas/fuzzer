package parsers;

/**
 * This error is thrown when a file is not formated in the right way.
 * @author Kees Lampe
 *
 */
@SuppressWarnings("serial")
public class FileNotCompatibleException extends Exception {

	public FileNotCompatibleException(String message){
		super(message);
	}
}

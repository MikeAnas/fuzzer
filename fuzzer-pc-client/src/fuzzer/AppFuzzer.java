package fuzzer;

import java.util.ArrayList;

import parsers.FileNotCompatibleException;
import parsers.ParseActionXML;
import communication.ActionInstruction;
import communication.ClientSocketWrapper;
import actions.ActionSet;

/**
 * This class contains only a main method that starts the fuzzing process
 * by parsing the root file, starting a socket with the server and run
 * the rootAction.
 * 
 * @author Kees Lampe
 *
 */
public class AppFuzzer {

	static ClientSocketWrapper csw;

	public static void main(String[] args) throws FileNotCompatibleException {
		ParseActionXML rootFile = new ParseActionXML("res/xml/test.xml");
		ActionSet RootAction = rootFile.getRootActionSet();
		System.out.println(RootAction);

		csw = new ClientSocketWrapper(11235);

		ArrayList<ActionInstruction> ActionInstructionList = RootAction.createActionInstructionList();
		for(ActionInstruction act: ActionInstructionList){
			System.out.println(csw.sendActionInstruction(act));
		}		
	}

}

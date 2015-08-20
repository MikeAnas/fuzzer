package actions;

import java.util.ArrayList;

import communication.ActionInstruction;

/**
 * Interface for Action an ActionSet. Which both should have a
 * createActionInstructionList and toString method.
 * 
 * @author Kees Lampe
 *
 */
public interface IAction {

	public ArrayList<ActionInstruction> createActionInstructionList();

	public String toString();

}

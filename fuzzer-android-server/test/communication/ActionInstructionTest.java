package communication;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.android.uiautomator.core.UiObjectNotFoundException;

import driver.DriveUiAutomator;

public class ActionInstructionTest {

	@Mock private DriveUiAutomator mockDriver;
	
	private ActionInstruction actionInstruction;

	@Before
	public void init(){
		mockDriver = Mockito.mock(DriveUiAutomator.class);
	}
	
	@Test
	public void createNewActionInstruct() {
		actionInstruction = new ActionInstruction("method", new String[]{"String", "int"});
		assertNotNull(actionInstruction);
	}
	
	@Test
	public void dispatchWithoutParameters() throws NoSuchMethodException, UiObjectNotFoundException{
		actionInstruction = new ActionInstruction("back", new String[0]);
		actionInstruction.dispatch(mockDriver);

		Mockito.verify(mockDriver).back();
		Mockito.verifyNoMoreInteractions(mockDriver);
	}

	@Test
	public void dispatchWithParameters() throws UiObjectNotFoundException{
		actionInstruction = new ActionInstruction("click", new String[]{"String"});
		actionInstruction.dispatch(mockDriver);
		
		ArgumentCaptor<String> idCapt = ArgumentCaptor.forClass(String.class);
		
		Mockito.verify(mockDriver, Mockito.times(1)).click(idCapt.capture());
		assertEquals("String", idCapt.getValue());
	}
	
	@Test
	public void dispatchWithNonExistingFunction(){
		actionInstruction = new ActionInstruction("nonExistingFunction", new String[]{"String"});
		actionInstruction.dispatch(mockDriver);
		
		Mockito.verifyZeroInteractions(mockDriver);
	}
	

}

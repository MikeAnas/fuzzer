package actions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author michel
 *
 */
public class InputTest {

	@Test
	public void createInput() {
		Input input = new Input("input");
		assertNotNull(input);
	}

	@Test
	public void createInputWithChance() {
		Input input = new Input("input", 0.5);
		assertNotNull(input);
		assertEquals(0.5, input.getChance(), 0.0);
	}

	@Test
	public void getChanceOfNewInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals(0.0, input.getChance(), 0.0);
	}

	@Test
	public void setValidChanceOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals(0.0, input.getChance(), 0.0);

		input.setChance(0.4);
		assertEquals(0.4, input.getChance(), 0.0);
	}

	@Test
	public void setInvalidChanceOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals(0.0, input.getChance(), 0.0);

		input.setChance(5.1);
		assertEquals(0.0, input.getChance(), 0.0);
	}

	@Test
	public void setNegativeChanceOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals(0.0, input.getChance(), 0.0);

		input.setChance(-0.3);
		assertEquals(0.0, input.getChance(), 0.0);
	}

	@Test
	public void setMinChanceOfInput() {
		Input input = new Input("input", 0.4);
		assertNotNull(input);

		assertEquals(0.4, input.getChance(), 0.0);

		input.setChance(0.0);
		assertEquals(0.0, input.getChance(), 0.0);
	}

	@Test
	public void setMaxChanceOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals(0.0, input.getChance(), 0.0);

		input.setChance(1.0);
		assertEquals(1.0, input.getChance(), 0.0);
	}

	@Test
	public void getValueOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals("input", input.getValue());
	}

	@Test
	public void setValueOfInput() {
		Input input = new Input("input");
		assertNotNull(input);

		assertEquals("input", input.getValue());

		input.setValue("input1234");
		assertEquals("input1234", input.getValue());
	}
}

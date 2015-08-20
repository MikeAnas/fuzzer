package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * 
 * @author michel
 *
 */

public class PropertyTest {
	
	private Properties props = new Properties();
	private final String configPath = "src/config/config.properties";
	private Property property;
	
	@Before
	public void init(){
		try {
			props.load(new FileInputStream(configPath));
		} catch (IOException e){
			System.out.println("Could not open the config file");
		}
		
		property = Property.getInstance();
		assertNotNull(property);		
	}
	
	@Test
	public void readValidPropertyFile() {
		assertEquals(props.getProperty("btnClass"), property.get("btnClass"));
	}
	
	@Test
	public void retrieveExistingPropertyInstance(){
		property = null;
		assertNull(property);
		property = Property.getInstance();
		assertNotNull(property);
		assertEquals(props.getProperty("btnClass"), property.get("btnClass"));
	}
	
	@Test
	public void readNonExistingProperty(){		
		assertNull(property.get("nonExistingProperty"));
	}
	
	@Test
	public void readIntegerProperty(){
		assertEquals(Integer.parseInt(props.getProperty("port")), property.getInt("port"));
	}
	
	@Test
	public void readNonIntegerPropertyAsInteger(){
		assertEquals(-1, property.getInt("btnClass"));
	}
	
	@Test
	public void readNonExistingPropertyAsInteger(){
		assertEquals(-1, property.getInt("nonExistingProperty"));
	}
	
}

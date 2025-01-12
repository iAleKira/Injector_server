package server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

	private Map<Integer, String> map;
	private Database database;
	
	@Before
	public void setup() {
		map = new HashMap<Integer, String>();
		database = new Database(map);
	}
	
	@Test
	public void testMapIsCorrectlyFilled() {
		database.fillMap();
		assertThat(map).hasSize(16);
		int ports[] = { 21, 22, 25, 80};
		for (int key : ports) {
			assertTrue(map.containsKey(key));
		}
		assertFalse(map.containsKey(999));
	}

	@Test
	public void testMapValueToName() {
		database.fillMap();
		assertEquals(map.get(21), "ftp");
		assertEquals(map.get(22), "ssh");
	}
	
	@Test
	public void testGetRandomPayloadFromFile() throws IOException {
        String path = "src//test//java//server//ssh.txt"; 
        BufferedReader file = new BufferedReader(new FileReader(path));
        String randomLine1 = database.getRandomLineFromFile(file, path);
        assertNotNull(randomLine1);
        assertThat(randomLine1.contains("match ssh"));
    }
	
	@Test
	public void testNullFileExceptionIsCaught() throws IOException {
		String path = "src//test//java//server//ssh.txt";
        BufferedReader file = new BufferedReader(new FileReader(path));
        boolean thrown = false;
        try{
        	database.getRandomLineFromFile(file, null);
        } catch (IllegalArgumentException e) {
        	thrown = true;
        	assertEquals("Input path is not valid. Path: null", e.getMessage());
        }
        assertTrue(thrown); 
	}
	
	@Test
	public void testNullBufferedReaderExceptionIsCaught() throws IOException {
		String path = "src//test//server//ssh.txt"; 
        boolean thrown = false;
        try{
        	database.getRandomLineFromFile(null, path);
        } catch (IllegalArgumentException e) {
        	thrown = true;
        	assertEquals("Input file is not valid. File: null", e.getMessage());
        }
        assertTrue(thrown); 
	}
}
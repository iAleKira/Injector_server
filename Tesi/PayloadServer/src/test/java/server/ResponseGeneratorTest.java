package server;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import injector.ExploitBuilder;

public class ResponseGeneratorTest {
	private ResponseGenerator generator;
	private Map<Integer, String> map;
	private Database database;
	private ExploitBuilder injector;
	private String payload;
	private String filePath;
	private BufferedReader fileToRead;
	private String response;

	@Before
	public void setup() throws IOException{
		generator = new ResponseGenerator();
		map = new TreeMap <Integer,String>();
		database = new Database(map);
		injector = new ExploitBuilder();
		filePath = "//home//kali//Desktop//Tesi//TextFiles//injectableProbes//toInject//http.txt";
		fileToRead = new BufferedReader(new FileReader(filePath));
		payload = "<script>alert(1)</script>";
	}
	
	@Test
	public void testGeneratedResponseContainsPayload() throws IOException{
		database.fillMap();
		response = generator.generateResponse(database, injector, payload, filePath, fileToRead);
		assertTrue(response.contains(payload));
	}	
	
	@Test
	public void testGeneratedResponseIsNotNull() throws IOException{
		database.fillMap();
		response = generator.generateResponse(database, injector, payload, filePath, fileToRead);
		assertNotNull(response);
	}
	
	@Test
	public void testGeneratedResponseIsNotEmpty() throws IOException{
		database.fillMap();
		response = generator.generateResponse(database, injector, payload, filePath, fileToRead);
		assertTrue(!response.isEmpty());
	}	
}

package server;

import java.io.BufferedReader;
import java.io.IOException;

import injector.ExploitBuilder;

/**
 * This class handles the generation of the response by the server.
 */
public final class ResponseGenerator {

	/**
	 * @param database - the database of the server.
	 * @param injector - the ExploitBuilder injector that will inject the payload into the string.
	 * @param payload  - the payload to inject. 
	 * @param filePath - the path of the fileToRead.
	 * @param fileToRead - the file from which the method reads.
	 * 
	 * @return injected - the string injected with the payload
	 */
	public String generateResponse(Database database, ExploitBuilder injector, String payload, String filePath,
			BufferedReader fileToRead) throws IOException {
		String toInject = database.getRandomLineFromFile(fileToRead, filePath);
		String restricted = injector.restrict(toInject);
		String injected = injector.inject(restricted, payload);
		injected = injected.replace("\\r", "\r").replace("\\n", "\n");
		return injected;
	}
}
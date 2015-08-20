package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class wraps a Socket which can send ActionInstruction objects to the
 * server.
 * 
 * @author Kees Lampe
 *
 */
public class ClientSocketWrapper {

	private Socket socket;
	private BufferedReader inputStream; 	
	private ObjectOutputStream outputStream; 

	public ClientSocketWrapper(int portNumber) {
		try {
			socket = new Socket("localhost", portNumber);
			socket.setTcpNoDelay(true);

			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));

			System.out.println("ClientSocketWrapper created.");
		} catch (IOException e) {
			System.err.println("PROBLEM: problem connecting with Server: "
					+ e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Send an actionIntruction to the server and return the response from the
	 * server. if its send boolean is set to true.
	 * 
	 * @param act The ActionInstruction to be sent
	 * @return Message of sending and response if send.
	 */
	public String sendActionInstruction(ActionInstruction act) {
		if (act.getSend()) {
			try {
				outputStream.writeObject(act);
				outputStream.flush();
			} catch (IOException e) {
				System.err.println("ActionInstruction could not be sent");
			}
			String response = receiveFromServer();
			return "SEND: " + act.toString() + " RESPONSE: " + response;
		} else {
			return "NOT SEND: " + act.toString();
		}
	}

	/**
	 * Wait for, and return, a response from the server.
	 * 
	 * @return Response from the server.
	 */
	private String receiveFromServer() {
		String outputStr;
		try {
			outputStr = inputStream.readLine();
			if (outputStr == null) {
				System.err.println("PROBLEM: problem reading output from Server: closed connection");
				System.exit(1);
			}
			return outputStr;
		} catch (IOException e) {
			System.err.println("PROBLEM: problem reading output from Server, but outputStr != null");
			return null;
		}
	}

	/**
	 * Close the socket and streams.
	 */
	public void close() {
		try {
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException ex) {
			System.err.println("PROBLEM: problem closing connections");
			System.exit(1);
		}
	}
}

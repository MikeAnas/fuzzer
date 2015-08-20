package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class wraps a server socket that receives ActionInstruction Object from a client.
 * And sends Strings back as a response.
 * @author Kees Lampe
 *
 */
public class ServerSocketWrapper {
	
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectInputStream inputStream;	
	private PrintWriter outputStream; 

	/**
	 * Creates the Server 
	 * @param port Which the server is created on.
	 */
	public ServerSocketWrapper(int port) {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("ServerSocketWrapper created.");
		} catch (IOException e) {
			System.err.println("PROBLEM: failed to open ServerSocket: "	+ e.getMessage());
		}
	}

	/**
	 * This function is called by the SetUp in a while loop to keep listing to the Client.
	 * @return ActionInstruction object to be executed.
	 */
	public ActionInstruction receiveFromClient() {
		try {
			return (ActionInstruction) inputStream.readObject();	
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Failed to receive from client");
		} 
		return null;
	}
	
	/**
	 * Makes sure that input that is ready to send is directly send to the Client.
	 * @param input
	 */
	public void sendToClient(String input) {
		this.outputStream.println(input);
		this.outputStream.flush();
	}
	
	/**
	 * Closes the socket.
	 */
	public void close() {
		try {
			this.inputStream.close();
			this.outputStream.close();
			this.socket.close();
		} catch (IOException e) {
			System.err.println("PROBLEM: failed to close connection: " + e.getMessage());		
		}
		System.out.println("Closed client.");
	}
}

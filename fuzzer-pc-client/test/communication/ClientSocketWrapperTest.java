package communication;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientSocketWrapperTest {

	private ClientSocketWrapper socketWrapper;
	private DummyServer dummy;
	private Thread serverThread;

	public class DummyServer implements Runnable {
		private ServerSocket server;
		public ConnectedSocket myConn;

		public DummyServer() throws IOException {
			server = new ServerSocket(12345);
		}

		@Override
		public void run() {
			try {
				Socket s = server.accept();
				myConn = new ConnectedSocket(s);
				myConn.run();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This socket does just send the input back that it receives.
	 * 
	 * @author tom
	 *
	 */
	public class ConnectedSocket {
		private Socket sock;
		private ObjectInputStream ois; // Object Input Stream
		private PrintWriter pos; // Print Output Stream

		public ConnectedSocket(Socket s) throws UnsupportedEncodingException,
				IOException {
			sock = s;
			sock.setTcpNoDelay(true);
			ois = new ObjectInputStream(sock.getInputStream());
			pos = new PrintWriter(
					new OutputStreamWriter(sock.getOutputStream()));
		}

		private String receive() {
			try {
				try {
					return (String) ois.readObject().toString();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.err
						.println("PROBLEM: problem reading output from SUL, but outputStr != null");
			}
			return null;
		}

		private void send(String input) {
			this.pos.println(input);
			this.pos.flush();
		}

		public void run() {
			while (!sock.isClosed()) {
				String received = receive();
				send(received);
			}
		}
	}

	@Before
	public void setUp() throws Exception {
		dummy = new DummyServer();
		serverThread = new Thread(dummy);
		serverThread.start();
		socketWrapper = new ClientSocketWrapper(12345);
	}

	@Test(timeout = 1000)
	public void testSendActionInstructionTrue() {
		ActionInstruction act = new ActionInstruction("Method", null, true,
				"expected");
		assertEquals("SEND: expected RESPONSE: expected",
				socketWrapper.sendActionInstruction(act));
	}

	@Test(timeout = 1000)
	public void testSendActionInstructionFalse() {
		ActionInstruction act = new ActionInstruction("Method", null, false,
				"expected");
		assertEquals("NOT SEND: expected",
				socketWrapper.sendActionInstruction(act));
	}

	@After
	public void tearDown() throws IOException {
		serverThread.stop();
		socketWrapper.close();
		dummy.server.close();
		dummy.myConn.sock.close();
	}

}

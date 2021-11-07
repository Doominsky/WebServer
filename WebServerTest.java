package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.FileNotFoundException;
import java.net.Socket;
import org.junit.Test;
import webserver.Main;
import webserver.MyConstants;
import webserver.WebServer;

public class WebServerTest implements MyConstants {
	@Test(expected = NullPointerException.class)
	public void testNullPath() throws FileNotFoundException, NullPointerException {
		WebServer.read(null);
		System.out.println("Test failed: Read Null");
	}
	@Test(expected = FileNotFoundException.class)
	public void testNotFound() throws FileNotFoundException, NullPointerException {
		WebServer.read("@1000");
		System.out.println("Test failed: Read Null");
	}

	
	@Test(expected=NullPointerException.class)
	public void getStatusCodeNull() {
		WebServer webServer = new WebServer(new Socket());
		Main.setServerIsRunning(true);
		webServer.getResource(null);
		System.out.println("Test failed: getStatusCodeNull");
	}
	@Test
	public void getStatusCodeEmpty() {
		WebServer webServer = new WebServer(new Socket());
		Main.setServerIsRunning(true);
		assertEquals("Test failed: getStatusCodeEmpty", OK_200, webServer.getResource("")[1]);
	}
	@Test
	public void getStatusCodeDefault() {
		WebServer webServer = new WebServer(new Socket());
		Main.setServerIsRunning(true);
		assertEquals("Test failed: getStatusCodeDefault", OK_200, webServer.getResource(DEFAULT_FILE)[1]);
	}
	@Test
	public void getStatusCodeNotFound() {
		WebServer webServer = new WebServer(new Socket());
		Main.setServerIsRunning(true);
		assertEquals("Test failed: getStatusCodeNotFound", OK_200, webServer.getResource("aeiou")[1]);
	}

	@Test(expected = NullPointerException.class)
	public void sendResponseNullFile() {
		WebServer webServer = new WebServer(new Socket());
		webServer.sendResponse(null,System.out);
		System.out.println("Test failed: sendResponseNullFile");
	}
	@Test(expected = NullPointerException.class)
	public void sendResponseNullOutputStream() {
		WebServer webServer = new WebServer(new Socket());
		webServer.sendResponse(new String[]{"",OK_200},null);
		System.out.println("Test failed: sendResponseNullOutputStream");
	}
	@Test(expected = IllegalArgumentException.class)
	public void sendResponseWrongSizeFile() {
		WebServer webServer = new WebServer(new Socket());
		webServer.sendResponse(new String[]{},System.out);
		System.out.println("Test failed: sendResponseWrongSizeFile");
	}
	@Test(expected = IllegalArgumentException.class)
	public void sendResponseWrongResponseStatus() {
		WebServer webServer = new WebServer(new Socket());
		webServer.sendResponse(new String[]{"","test"},System.out);
		System.out.println("Test failed: sendResponseWrongResponseStatus");
	}
}

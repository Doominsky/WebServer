package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import webserver.Main;

public class MainTest {
	@Test(expected=IllegalArgumentException.class)
	public void testSetServerSocketPortSmallerThan1024() {
		Main.setServerSocket(1023);
		System.out.println("Test failed: testSetServerSocketPortSmallerThan1024");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSetServerSocketPortBiggerThan65535() {
		Main.setServerSocket(65536);
		System.out.println("Test failed: testSetServerSocketPortBiggerThan65535");
	}

	@Test
	public void testGetServerSocket() {
		assertEquals("Test failed: getServerSocket",null,Main.getServerSocket());
	}
	@Test
	public void testGetServerSocketAfterSet() throws IOException {
		Main.setServerSocket(10007);
		assertEquals("Test failed: getServerSocketAfterSet",10007,Main.getServerSocket().getLocalPort());
	}
	
	@Test(expected=NullPointerException.class)
	public void testValidateNullDirPath() {
		Main.validateDirectoryPath(null);
		System.out.println("Test failed: testValidateNullDirPath");
	}
	@Test
	public void testValidateRelativeDirPath() {
		assertTrue("Test failed: testValidateRelativeDirPath",Main.validateDirectoryPath("src/"));
	}
	@Test
	public void testValidateaAbsoluteDirPath() {
		assertTrue("Test failed: testValidateaAbsoluteDirPath",Main.validateDirectoryPath("C:/Users/Contacts/Desktop/"));
	}
	@Test
	public void testValidateaNotADirPath() {
		assertFalse("Test failed: testValidateaNotADirPath",Main.validateDirectoryPath("src/empty/"));
	}
}

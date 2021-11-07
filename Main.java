package webserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main implements MyConstants {

	private static boolean serverOpen = true;
	private static boolean serverRunning = false;
	private static boolean serverInMaintenance = false;
	private static ServerSocket serverSocket = null;
	private static String rootDirectory = DEFAULT_ROOT_DIR;
	private static String maintenanceDirectory = DEFAULT_MAINTENANCE_DIR;

	public static boolean serverRunning() {
		return serverRunning;
	}

	public static boolean serverInMaintenance() {
		return serverInMaintenance;
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static String getRootDirectory() {
		return rootDirectory;
	}

	public static String getMaintenanceDirectory() {
		return maintenanceDirectory;
	}

 	public static void setserverRunning(boolean value) {
		serverRunning = value;
	}

	public static void setserverInMaintenance(boolean value) {
		serverRunning = value;
	}

	public static boolean validateDirectoryPath(String path) throws NullPointerException {
        File file = new File(path);
        return file.isDirectory();
	}

	public static boolean setRootDirectory(String path) {
		if (validateDirectoryPath(path)) {
			rootDirectory = path;
			return true;
		}
		return false;
	}

	public static boolean setMaintenanceDirectory(String path) {
		if (validateDirectoryPath(path)) {
			maintenanceDirectory = path;
			return true;
		}
		return false;
	}

	public static void setServerSocket(int port) throws IllegalArgumentException {
		if(port < MIN_PORT || port > MAX_PORT)
			throw new IllegalArgumentException();
		try {
			if(serverSocket!=null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			System.err.println("Could not close port: " + serverSocket.getLocalPort());
			System.exit(-1);
		}
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new GUI();
		setServerSocket(INITIAL_PORT);
		System.out.println("Connection Socket Created");
		while(serverOpen) {
			try {
					System.out.println("Waiting for Connection");
					new WebServer(serverSocket.accept());
			} catch (IOException e) {
				System.err.println("Accept failed.");
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Could not close port: " + serverSocket.getLocalPort());
			System.exit(1);
		}
	}
}

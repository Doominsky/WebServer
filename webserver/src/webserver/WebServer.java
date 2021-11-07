package webserver;

import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

public class WebServer extends Thread implements Runnable, MyConstants {
	private Socket clientSocket = null;
	
	public WebServer(Socket socket) {
		clientSocket = socket;
	}
	
	public static String read(String path) throws FileNotFoundException, NullPointerException {
		String s="";
		File file = new File(path);
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			s += scanner.nextLine();
		}
		scanner.close();
		return s;
	}
	public String[] getResource(String fileName) throws NullPointerException {
		if(fileName==null)throw new NullPointerException();
		String[] file = new String[2];
		if(Main.serverIsRunning()) {
			try {
				if(!Main.serverIsInMaintenance()) {
					if(fileName.equals("")) {
						file[0] = read(Main.getRootDirectory() + DEFAULT_FILE);	
					} else {
						file[0] = read(Main.getRootDirectory() + fileName);
					}
				} else {
					if(fileName.endsWith(".css")) {
						file[0] = read(Main.getMaintenanceDirectory() + fileName);
					} else {
						file[0] = read(Main.getMaintenanceDirectory() + MAINTENANCE_FILE);
					}
				}
				file[1] = OK_200;
			} catch (FileNotFoundException e) {
				try {
					file[0] = read(DEFAULT_ROOT_DIR + FILE_NOT_FOUND);
				} catch (FileNotFoundException e1) {
					System.out.println("404 error: page not found");
					System.exit(-1);
				}
				file[1] = OK_200;
		    }
		} else {
			file[0] = "";
			file[1] = TIMEOUT_408;
		}
		return file;
	}
	public void sendResponse(String[] file, OutputStream out) throws NullPointerException, IllegalArgumentException {
		if(file.length!=2) {
			throw new IllegalArgumentException();
		}
		if(file==null||file[0]==null||file[1]==null||out==null) {
			throw new NullPointerException();
		}
		if(!file[1].equals(OK_200)&&!file[1].equals(NOT_FOUND_404)&&!file[1].equals(TIMEOUT_408)) {
			throw new IllegalArgumentException();
		}
		
		String CRLF = "\n\r";
		String response = "HTTP/1.1 " + file[1] + CRLF + "Length: " + file[0].getBytes().length + CRLF + CRLF + file[0] + CRLF + CRLF;
		try {
			out.write(response.getBytes());
		} catch (IOException e) {
			System.err.println("Response Error");
			System.exit(-1);
		}
    }
	
	public void run() {
		System.out.println("Started New Communication Thread");
		try {
			InputStream is = clientSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			OutputStream out = clientSocket.getOutputStream();
			
			String inputLine;
			try {
				while ((inputLine = in.readLine()) != null) {
					if(inputLine.startsWith("GET")) {
						sendResponse(getResource(inputLine.split(" ")[1].substring(1)), out);
				    }
					if (inputLine.trim().equals("")) {
						break;
					}
				}
			} catch (IOException e1) {
				System.err.println("Error reading request");
			}
			
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(-1);
		}
	}
}
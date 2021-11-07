package webserver;

import java.awt.Color;

public interface MyConstants {

  	static final String TITLE_WHILE_STOPPED = "Stopped";
  	static final String TITLE_WHILE_RUNNING = "Running";
  	static final String TITLE_WHILE_MAINTAINING = "Maintenance";
  	static final String NOT_RUNNING = "Not Running";
  	static final String RUNNING = "Running";
  	static final String MAINTENANCE = "Maintenance";
  	static final String START_SERVER = "Start server";
  	static final String STOP_SERVER = "Stop server";
  	static final String START_MAINTENANCE = "Start maintenance";
  	static final String STOP_MAINTENANCE = "Stop maintenance";
  	static final String SERVER_LISTENING_ON_PORT = "Server listening on port:";
  	static final String WEB_ROOT_DIRECTORY = "Web root directory:";
  	static final String MAINTENANCE_DIRECTORY = "Maintenance directory:";

	static final Color BLACK = new Color(0,0,0);
	static final Color RED = new Color(255,0,0);
	static final Color GREEN = new Color(0,255,0);
	static final Color BLUE = new Color(0,0,255);

	static final int MIN_PORT = 1024;
	static final int MAX_PORT = 65535;
	static final int INITIAL_PORT = 10008;

	static final String DEFAULT_ROOT_DIR = "src/html/";
	static final String DEFAULT_MAINTENANCE_DIR = "src/html/maintenance/";
	static final String DEFAULT_FILE = "index.html";
	static final String MAINTENANCE_FILE = "maintenance.html";
	static final String FILE_NOT_FOUND = "404.html";

	static final String OK_200 = "200 OK";
	static final String NOT_FOUND_404 = "404 Not Found";
	static final String TIMEOUT_408 = "408 Timeout";
}

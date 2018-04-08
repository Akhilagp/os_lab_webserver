import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

public class server
extends Thread {
	public server(int listen_port, webserver_starter to_send_message_to) {
		message_to = to_send_message_to;
		port = listen_port;
		this.start();
	}

	private void s(String s2) { 
		message_to.send_message_to_window(s2);
	}

	private webserver_starter message_to; 
	private int port; 
	public void run() {
		ServerSocket serversocket = null;
		s("Web server\n\n");
		try {
			s("Trying to bind to localhost on port " + Integer.toString(port) + "...");
			serversocket = new ServerSocket(port);
		}

		catch (Exception e) { 
			s("\nFatal Error:" + e.getMessage());
			return;
		}

		s("OK!\n");
		while (true) {
			s("\nReady, Waiting for requests...\n");
			try {
				Socket connectionsocket = serversocket.accept();
				InetAddress client = connectionsocket.getInetAddress();
				s(client.getHostName() + " connected to server.\n");
				BufferedReader input =
					new BufferedReader(new InputStreamReader(connectionsocket.
								getInputStream()));
				DataOutputStream output =
					new DataOutputStream(connectionsocket.getOutputStream());
				http_handler(input, output);
			}

			catch (Exception e) { 
				s("\nError:" + e.getMessage());
			}

		} 
	}

	private void http_handler(BufferedReader input, DataOutputStream output) {
		int method = 0;
		String http = new String(); 
		String path = new String(); 
		String file = new String(); 
		String user_agent = new String();
		try {
			String tmp = input.readLine(); 
			String tmp2 = new String(tmp);
			tmp.toUpperCase(); 
			if (tmp.startsWith("GET")) { 
				method = 1;
			} 
			if (tmp.startsWith("HEAD")) {
				method = 2;
			} 
			if (method == 0) {
				try {
					output.writeBytes(construct_http_header(501, 0));
					output.close();
					return;
				}

				catch (Exception e3) {
					s("error:" + e3.getMessage());
				}
			}


			int start = 0;
			int end = 0;
			for (int a = 0; a < tmp2.length(); a++) {
				if (tmp2.charAt(a) == ' ' && start != 0) {
					end = a;
					break;
				}

				if (tmp2.charAt(a) == ' ' && start == 0) {
					start = a;
				}
			}
			path = tmp2.substring(start + 2, end); 
		}

		catch (Exception e) {
			s("errorr" + e.getMessage());
		} 

		s("\nClient requested:" + new File(path).getAbsolutePath() + "\n");
		FileInputStream requestedfile = null;

		try { 
			requestedfile = new FileInputStream(path);
		}

		catch (Exception e) {
			try {
				output.writeBytes(construct_http_header(404, 0));
				output.close();
			}

			catch (Exception e2) {}
			s("error" + e.getMessage());
		} 
		try {
			int type_is = 0;     
			if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
				type_is = 1;
			}
			if (path.endsWith(".gif")) {
				type_is = 2;      
			}
			if (path.endsWith(".zip") ) {
				type_is = 3;
			}
			if (path.endsWith(".ico")) {
				type_is = 4;
			}
			if (path.endsWith(".png")){
				type_is = 5;
			}
			if (path.endsWith(".txt")){
				type_is = 6;
			}	
			if (path.endsWith(".mp3") ) {
				type_is = 7;
			}
			if (path.endsWith(".mp4") ) {
				type_is = 8;
			}
			if (path.endsWith(".js") ) {
				type_is = 9;
			}	
			if (path.endsWith(".c") ) {
				type_is = 10;
			}	
			if (path.endsWith(".sh") ) {
				type_is = 11;
			}	
			if (path.endsWith(".py") ) {
				type_is = 12;
			}	
			if (path.endsWith(".css") ) {
				type_is = 13;
			}	
			output.writeBytes(construct_http_header(200, type_is));
			if (method == 1) { 
				byte [] buffer = new byte[1024];
				while (true) {    
					int b = requestedfile.read(buffer, 0,1024);
					if (b == -1) {
						break; 
					}
					output.write(buffer,0,b);
				}
			}
			output.close();
			requestedfile.close();
		}

		catch (Exception e) {}

	}

	private String construct_http_header(int return_code, int file_type) {

		String s = "HTTP/1.0 ";
		switch (return_code) {
			case 200:
				s = s + "200 OK";
				break;
			case 400:
				s = s + "400 Bad Request";
				break;
			case 403:
				s = s + "403 Forbidden";
				break;
			case 404:
				s = s + "404 Not Found";
				break;
			case 500:
				s = s + "500 Internal Server Error";
				break;
			case 501:
				s = s + "501 Not Implemented";
				break;
		}
		s = s + "\r\n"; 
		s = s + "Connection: close\r\n";
		s = s + "End of the server\r\n";  

		switch (file_type) {    
			case 0:
				break;
			case 1:
				s = s + "Content-Type: image/jpeg\r\n";
				break;
			case 2:
				s = s + "Content-Type: image/gif\r\n";
				break;
			case 3:
				s = s + "Content-Type: application/x-zip-compressed\r\n";
				break;
			case 4:
				s = s + "Content-Type: image/x-icon\r\n";
				break;
			case 5:
				s = s + "Content-Type: image/png\r\n";
				break;
			case 6:
				s = s + "Content-Type: text/plain\r\n";
				break;
			case 7:
				s = s + "Content-Type: audio/x-mpeg-3\r\n";
				break;
			case 8:
				s = s + "Content-Type: video/mp4\r\n";
				break;
			case 9:
				s = s + "Content-Type: text/javascript\r\n";
				break;
			case 10:
				s = s + "Content-Type: text/x-c\r\n";
				break;
			case 11:
				s = s + "Content-Type: text/x-bsh\r\n";
				break;
			case 12:
				s = s + "Content-Type: text/x-python\r\n";
				break;
			case 13:
				s = s + "Content-Type: text/css\r\n";
				break;
			default:
				s = s + "Content-Type: text/html\r\n";
				break;
		}
		s = s + "\r\n"; 
		return s;
	}
} 

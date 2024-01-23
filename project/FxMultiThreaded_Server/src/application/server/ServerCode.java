package application.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ServerCode extends Application{

	//for displaying contents
	public TextArea ta = new TextArea();
	
	//mapping of sockets to output streams
	private Hashtable<Socket, DataOutputStream> outputStreams = new Hashtable<>();
	
	private ServerSocket ss;
	@Override
	public void start(Stage ps) throws Exception {
		ta.setWrapText(true);
		
		Scene sc = new Scene(new ScrollPane(ta),400,400);
		ps.setTitle("Server");
		ps.setScene(sc);
		ps.show();
		
		new Thread(()->listener()).start();
		
	}
	
	private void listener() {
		try {
			ss = new ServerSocket(5000);
			
			Platform.runLater(()->ta.appendText("MultiThreaded Server started at " + new Date() + '\n'));
			
			while(true) {
				Socket clientSocket = ss.accept();
				Platform.runLater(()->ta.appendText("Connection from "+ clientSocket + new Date() + '\n'));
							
				//create output stream
				DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
				
				outputStreams.put(clientSocket, dout);
				
				new MultiThreadedCode(this,clientSocket);
				
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	Enumeration<DataOutputStream> getOutputStreams(){
		return outputStreams.elements();
	}
	
	void sendAll(String message) {
		for(Enumeration<DataOutputStream> e = getOutputStreams(); e.hasMoreElements();) {
			DataOutputStream dout = e.nextElement();
			try {
				dout.writeUTF(message);
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

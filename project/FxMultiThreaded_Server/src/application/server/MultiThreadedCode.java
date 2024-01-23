package application.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiThreadedCode extends Thread{

	private ServerCode sc;
	private Socket s;
	
	public MultiThreadedCode(ServerCode sc, Socket s) {
		this.sc = sc;
		this.s = s;
		start();
	}
	
	@Override
	public void run() {
		try {
		
			DataInputStream din = new DataInputStream(s.getInputStream());
			
			while(true) {
				String st = din.readUTF();
				sc.sendAll(st);
				sc.ta.appendText(st + '\n');
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
}

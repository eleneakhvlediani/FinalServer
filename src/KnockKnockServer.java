import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.imageio.IIOException;



public class KnockKnockServer {
	
	static HashMap<String, Socket> hashMap = new HashMap<String,Socket>();
	public static void main(String[] args) throws IIOException {

		int portNumber = 8099;
		boolean listening = true;
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("connected");
			 while (listening) {
				 Socket acc = serverSocket.accept();
				 String id = generateId();
				 hashMap.put(id, acc);
				 new KKMultiServerThread(id,acc).start();
			 }

		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());

		}
	}
	
	static int id = 0 ;
	private static String generateId() {
		String retId = "" +id;
		id++;
		return retId;
	}
	public static Socket findSocket(String id){
		return hashMap.get(id);
	}
	
	
	

}
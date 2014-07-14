import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class KKMultiServerThread extends Thread{
	 private Socket socket = null;
	 private String id = "";
	 private static final String CONNECTION_STRING = "connect:";
	
	 public KKMultiServerThread(String id, Socket acc) {
		 super("KKMultiServerThread");
	        this.socket = acc;
	        this.id = id;
	}
	public void run() {

	        try (
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        ) {
	        	String inputLine, outputLine;
	          
	            outputLine = id;
	            out.println("YourId:"+outputLine);

	            while ((inputLine = in.readLine()) != null) {
	            	//System.out.println(inputLine.substring(0,CONNECTION_STRING.length()-1));
	            	if((inputLine.length()>CONNECTION_STRING.length())&&inputLine.substring(0,CONNECTION_STRING.length()).equals(CONNECTION_STRING)){
	            		String wantToConnectId = inputLine.substring(CONNECTION_STRING.length());
	            		System.out.println(wantToConnectId);
	            	}
	                outputLine = "ok";
	                System.out.println(inputLine);
	                out.println(outputLine);
	                if (outputLine.equals("Bye"))
	                    break;
	            }
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}

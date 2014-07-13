import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.IIOException;

public class KnockKnockServer {
	public static void main(String[] args) throws IIOException {

		
		int portNumber = 8090;

		try (ServerSocket serverSocket = new ServerSocket(
				portNumber);
				Socket clientSocket = serverSocket.accept();
				
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));) {
			System.out.println(clientSocket.toString());
			String inputLine;
			System.out.println("connected");
			while ((inputLine = in.readLine()) != null) {
			
				System.out.println(inputLine);
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());

		}
	}
}
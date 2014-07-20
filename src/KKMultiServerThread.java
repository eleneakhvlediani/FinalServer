import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;
	private String id = "";
	private static final String CONNECTION_STRING = "connect:";
	private static final String WANTS_CONNECTION_STRING = "wantsConnection:";
	private static final String GAME_STARTED = "gameStarted:";
	private static final String SPEED_UP = "speedUp:";

	private static HashMap<String, String> players = new HashMap<String, String>();

	public KKMultiServerThread(String id, Socket acc) {
		super("KKMultiServerThread");
		this.socket = acc;
		this.id = id;
	}

	public void run() {

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {
			String inputLine, outputLine;

			outputLine = id;
			out.println("YourId:" + outputLine);

			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine.substring(0,CONNECTION_STRING.length()-1));
				checkConnectionRequest(inputLine, out);
				checkSpeedUpRequest(inputLine, out);
				System.out.println(inputLine);
//				out.println(outputLine);
				if (outputLine.equals("Finish"))
					break;
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void checkConnectionRequest(String inputLine,PrintWriter out)
	{
		if ((inputLine.length() > CONNECTION_STRING.length())
				&& inputLine.substring(0, CONNECTION_STRING.length())
						.equals(CONNECTION_STRING)) {
			String wantToConnectId = inputLine
					.substring(CONNECTION_STRING.length());
			System.out.println(wantToConnectId);

			SendConnectionRequest(wantToConnectId);
			out.println(GAME_STARTED + wantToConnectId);
			players.put(wantToConnectId, id);
			players.put(id, wantToConnectId);
		}
	}

	private void checkSpeedUpRequest(String inputLine,PrintWriter out)
	{
		if ((inputLine.length() > SPEED_UP.length())
				&& inputLine.substring(0, SPEED_UP.length())
						.equals(SPEED_UP)) {
			String speed = inputLine
					.substring(SPEED_UP.length());
			System.out.println(speed);

			SendSpeedUpNotification(speed);
		
		}
	}

	private void SendSpeedUpNotification(String speed) {
		String enemyId = players.get(id);
		Socket enemySocket = KnockKnockServer.findSocket(enemyId);
		try {
			PrintWriter out = new PrintWriter(enemySocket.getOutputStream(),
					true);
			out.println(SPEED_UP + speed);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void SendConnectionRequest(String wantToConnectId) {
		Socket enemySocket = KnockKnockServer.findSocket(wantToConnectId);
		try {
			PrintWriter out = new PrintWriter(enemySocket.getOutputStream(),
					true);
			out.println(WANTS_CONNECTION_STRING + id);
			out.println(GAME_STARTED + id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

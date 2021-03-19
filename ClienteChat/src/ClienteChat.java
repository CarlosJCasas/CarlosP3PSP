import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteChat {

	private final int NUMPUERTO = 8899;
	private static String direccionIp = "localhost";
	private String nick;
	private Socket socket;
	private ServerReceiver receiver;
	private ServerSender sender;
	private DataOutputStream dos;
	private DataInputStream dis;

	public ClienteChat(String nick) {
		this.nick = nick;
		try {
			this.socket = new Socket(direccionIp, NUMPUERTO);
			this.dos = new DataOutputStream(socket.getOutputStream());
			this.dis = new DataInputStream(socket.getInputStream());
			this.receiver = new ServerReceiver(dis);
			this.sender = new ServerSender(dos);
			// Probando si el sender y el receiver tienen que start aqui al mismo tiempo
			receiver.start();
			dos.writeUTF(nick);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String nickName = "";
		if (args.length < 2) {
			System.out.println("El comando para conectarse es: java ClienteChat <direccionIp> <nick>");
		} else {
			direccionIp = args[0];
			nickName = args[1];
			ClienteChat cliente = new ClienteChat(nickName);
			cliente.run();
		}
	}

	public void run() {
		sender.start();
		try {
			sender.join();
			receiver.parar();
			shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			dos.close();
			dis.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

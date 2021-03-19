import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {
	public static final int PUERTO = 8899;
	boolean activo = true;

	public ServidorChat() {
		boolean activo = true;

		try {
			ServerSocket ss = new ServerSocket(ConstantesServidor.PUERTO_ESCUCHA);
			System.out.println("Servidor iniciado\nEsperando por un cliente...\n");
			if (ListaClientes.conectados == null) {
				ListaClientes.iniciarList();
			}
			while (activo) {
				Socket socket = ss.accept();
				System.out.println("Cliente conectado\n");
				ClienteConectado cliente = new ClienteConectado(socket);
				cliente.start();
			}
		} catch (IOException e) {
			System.out.println(ConstantesServidor.CMD_DISCONECT_ERROR);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServidorChat servidorChat = new ServidorChat();
	}
}

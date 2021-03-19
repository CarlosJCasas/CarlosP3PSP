import java.util.ArrayList;
import java.util.List;

public class ListaClientes {
	public static List<ClienteConectado> conectados = null;

	public static void iniciarList() {
		conectados = new ArrayList<ClienteConectado>();
	}

	public static int getNumeroConectados() {
		return conectados.size();
	}
	
	public static void deleteClienteById(long id) {
		int position = getPosicionById(id);
		conectados.remove(position);
	}

	public static String getNombres() {
		String nombres = "";
		for (ClienteConectado cc : conectados) {
			nombres = nombres + cc.getNick() + "\n";
		}
		return nombres;

	}

	public static boolean enviarMensaje(long id, String mensaje) {
		// Envia el mensaje al cliente id
		String nickSender = "Usuario";
		for (ClienteConectado cc : conectados) {
			if (cc.getIdReceptor() == id) {
				nickSender = cc.getNick();
			}
		}
		for (ClienteConectado cc : conectados) {
			if (cc.getId() == id) {
				cc.enviarRespuesta(">" + nickSender + "-> " + mensaje +"\n");
				return true;
			}
		}
		return false;
	}

	public static int getPosicionById(long id) {
		int posicion = 0;
		for (int i = 0; i < conectados.size(); i++) {
			if (conectados.get(i).getId() == id) {
				posicion = i;
			}
		}
		return posicion;
	}

	public static long getIdByNick(String nick) {
		long id = 0;
		for (ClienteConectado cc : conectados) {
			if (cc.getNick().equalsIgnoreCase(nick)) {
				id = cc.getId();
				return id;
			}
		}
		return id;
	}
}

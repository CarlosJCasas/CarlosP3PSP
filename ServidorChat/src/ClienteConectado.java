import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClienteConectado extends Thread {
	private String nick;
	private long id;
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	private long idReceptor;
	private String mensaje = "";

	public ClienteConectado clienteConectado;
	public DataInputStream dis;
	public DataOutputStream dos;

	public ClienteConectado(String nick, long id, Socket socket, DataInputStream din, DataOutputStream dout,
			long idConversacion) {
		super();
		this.nick = nick;
		this.id = id;
		this.socket = socket;
		this.din = din;
		this.dout = dout;
		this.idReceptor = idConversacion;
	}

	public ClienteConectado(Socket socket) throws IOException {
		this.socket = socket;
	}

	@Override
	public void run() {
		boolean control = true;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			String nickname = dis.readUTF();
			dos.writeUTF("Te has conectado con el nick " + nickname);
			long id = System.currentTimeMillis();
			Socket socketo = socket;
			long idRecep = 0;
			clienteConectado = new ClienteConectado(nickname, id, socketo, dis, dos, idRecep);
			ListaClientes.conectados.add(clienteConectado);
			boolean recepcion;
			String recibido;
			dos.writeUTF(ConstantesServidor.CMD_AYUDA);
			while (control) {
				recibido = dis.readUTF();
				// if recibido empieza con # usar procesarComandos
				if (recibido.equalsIgnoreCase("#salir"))
					control = false;
				if (recibido.startsWith("#")) {
					procesarComandos(recibido);
				} else {
					// Si no tiene # comprobar si tiene una conexion con alguien y sino ERROR
					if (clienteConectado.getIdReceptor() != 0) {
						idRecep = clienteConectado.getIdReceptor();
						recepcion = ListaClientes.enviarMensaje(idRecep, recibido);
						if(recepcion == false) {
							dos.writeUTF(ConstantesServidor.NO_RECEPTOR);
						}
					} else {
						dos.writeUTF(ConstantesServidor.NO_RECEPTOR);
					}
				}
			}
		} catch (IOException e1) {
			System.out.println(ConstantesServidor.CMD_DISCONECT_ERROR);
		}

	}

	public void enviarRespuesta(String respuesta) {
		// Es de error y mensajes
		try {
			dout.writeUTF(respuesta);
		} catch (IOException e) {
			try {
				dos.writeUTF(ConstantesServidor.CMD_ERROR);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void procesarComandos(String comando) throws IOException {
		final String ayuda = "#ayuda";
		final String listar = "#listar";
		final String charlar = "#charlar";
		final String salir = "#salir";

		StringTokenizer st = new StringTokenizer(comando);
		String cmd = st.nextToken();
		switch (cmd.toLowerCase()) {
		case (ayuda):
			dos.writeUTF(ConstantesServidor.CMD_AYUDA);
			break;
		case (listar):
			dos.writeUTF(ListaClientes.getNombres());
			break;
		case (charlar):
			String nick = st.nextToken();
			// Buscar el id por el nombre
			long idReceptor = ListaClientes.getIdByNick(nick);
			// Comprobar si el cliente existe
			if (idReceptor != 0) {
				clienteConectado.setIdReceptor(idReceptor);
				dos.writeUTF(ConstantesServidor.CMD_CHARLAR_OK);
			} else {
				dos.writeUTF(ConstantesServidor.CMD_CHARLAR_ERROR);
			}
			break;
		case (salir):
			// salir tiene que eliminar de la lista el cliente que se va***
			dos.writeUTF(ConstantesServidor.BYE_MSG);
			ListaClientes.deleteClienteById(id);
			break;
		default:
			dos.writeUTF(ConstantesServidor.CMD_ERROR);
			break;
		}
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getDin() {
		return din;
	}

	public void setDin(DataInputStream din) {
		this.din = din;
	}

	public DataOutputStream getDout() {
		return dout;
	}

	public void setDout(DataOutputStream dout) {
		this.dout = dout;
	}

	public long getIdReceptor() {
		return idReceptor;
	}

	public void setIdReceptor(long idReceptor) {
		this.idReceptor = idReceptor;
	}
}

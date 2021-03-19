import java.io.DataInputStream;
import java.io.IOException;

public class ServerReceiver extends Thread {
	private DataInputStream dis;
	private boolean activo = true;

	public ServerReceiver(DataInputStream dis) {
		this.dis = dis;
	}

	@Override
	public void run() {
		try {
			
			while (activo) {
				String mensaje = dis.readUTF();
				System.out.println(mensaje);
				
			}
		} catch (IOException e) {
			System.out.println("Se ha desconectado del servidor");
		}
	}

	public void parar() {
		activo = false;
	}
}

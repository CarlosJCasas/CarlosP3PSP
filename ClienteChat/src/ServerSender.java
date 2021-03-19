import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerSender extends Thread {
	private DataOutputStream dos;
	private boolean activo = true;
	public BufferedReader teclado;

	public ServerSender(DataOutputStream dos) {
		this.dos = dos;
	}

	@Override
	public void run() {
		teclado = new BufferedReader(new InputStreamReader(System.in));
		while (activo) {
			try {
				String leer = teclado.readLine();
				dos.writeUTF(leer);
				if (leer.equalsIgnoreCase("#salir")) {
					sleep(500);
					parar();
				}
			} catch (IOException | InterruptedException | NullPointerException e) {
				System.out.println("La conexión se ha interrumpido.");
			}
		}
	}

	public void parar() {
		activo = false;
		try {
			teclado.close();
			dos.close();
		} catch (IOException e) {
			System.out.println("La conexión se ha interrumpido.");
			e.printStackTrace();
		}
	}
}

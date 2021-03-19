
public class ConstantesServidor {
	// Numero del puerto de escucha
	public static final int PUERTO_ESCUCHA = 8899;
	// Informacion del comando ayuda
	public static final String CMD_AYUDA = "Los comandos posibles son:\n\t#ayuda : Muestra los comandos.\n"
			+ "\t#listar : Muestra los usuarios conectados.\n" + "\t#charlar <nick> : Te conecta con el usuario.\n"
			+ "\t#salir : Se desconecta del chat.\n";
	// Error al usar el comando charlar
	public static final String CMD_CHARLAR_ERROR = "[ERROR] No existe el usuario con ese nick, utiliza el comando #listar para obtener los usuarios conectados.\n";
	// Charlar funciona
	public static final String CMD_CHARLAR_OK = "Te has conectado, escribe para hablar.\n";
	// Mensaje al desconectarse
	public static final String CMD_DISCONECT_MSG = "Te has desconectado del servidor.\n";
	// Error al desconectarse
	public static final String CMD_DISCONECT_ERROR = "[ERROR] Un cliente ha cerrado la conexión de forma abrupta.\n";
	// Mensajecon el comando salir
	public static final String BYE_MSG = "Has abandonado el chat. Bye!\n";
	// Error al introducir algun comando
	public static final String CMD_ERROR = "[ERROR] No es un comando válido, utiliza #ayuda para obtener una lista de comandos.\n";
	//Error cuando escribes pero aun no te conectaste con nadie ni nada
	public static final String NO_RECEPTOR = "No estás conectado con ningún usuario o el usuario se ha desconectado.\nUtiliza el comando #listar para obtener los usuarios conectados y el comando #charlar <nick> para abrir un chat.\n";
}

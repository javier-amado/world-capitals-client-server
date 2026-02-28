package practica5.mensaje;

import java.util.HashMap;


public class MensajeConexion extends Mensaje {
	
	private static final long serialVersionUID = 1L;
	
	private String nombreUsuario;
	private HashMap<String, String> mapa;

	public MensajeConexion(String origen, String destino, String nombreUsuario, HashMap<String, String> mapa) {
		super(KindM.M_CONEXION, origen, destino);
		this.nombreUsuario = nombreUsuario;
		this.mapa = mapa;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public HashMap<String, String> getMapa(){
		return mapa;
	}
}
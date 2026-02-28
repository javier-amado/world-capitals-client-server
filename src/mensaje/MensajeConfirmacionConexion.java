package practica5.mensaje;

public class MensajeConfirmacionConexion extends Mensaje{
	
	private static final long serialVersionUID = 1L;
	
	private boolean conectado;

	public MensajeConfirmacionConexion(String origen, String destino, boolean conectado) {
		super(KindM.M_CONFIRMACION_CONEXION, origen, destino);
		this.conectado = conectado;
	}
	
	public boolean getConectado() {
		return conectado;
	}
}

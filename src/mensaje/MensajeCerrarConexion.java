package practica5.mensaje;

public class MensajeCerrarConexion extends Mensaje{
	public MensajeCerrarConexion(String origen, String destino) {
		super(KindM.M_CERRAR_CONEXION, origen, destino);
	}

	private static final long serialVersionUID = 1L;

}

package practica5.mensaje;

public class MensajeCerrarConexionConfirmacion extends Mensaje{
	public MensajeCerrarConexionConfirmacion(String origen, String destino) {
		super(KindM.M_CONFIRMACION_CERRAR_CONEXION, origen, destino);
	}

	private static final long serialVersionUID = 1L;

}

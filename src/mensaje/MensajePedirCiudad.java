package practica5.mensaje;

public class MensajePedirCiudad extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	private String ciudad;
	private String nombreUsuario;

	public MensajePedirCiudad(String origen, String destino, String ciudad, String nombreUsuario) {
		super(KindM.M_PEDIR_CIUDAD, origen, destino);
		this.ciudad = ciudad;
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}

}

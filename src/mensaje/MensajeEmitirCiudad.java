package practica5.mensaje;

public class MensajeEmitirCiudad extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	private String ciudad;
	private String receptor;

	public MensajeEmitirCiudad(String origen, String destino, String ciudad, String receptor) {
		super(KindM.M_EMITIR_CIUDAD, origen, destino);
		this.ciudad = ciudad;
		this.receptor = receptor;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public String getNombreReceptor() {
		return receptor;
	}

}

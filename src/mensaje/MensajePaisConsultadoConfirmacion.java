package practica5.mensaje;

public class MensajePaisConsultadoConfirmacion extends Mensaje {
	
	private String ciudad;
	private String pais;

	public MensajePaisConsultadoConfirmacion(KindM t, String origen, String destino, String ciudad, String pais) {
		super(t, origen, destino);
		this.ciudad = ciudad;
		this.pais = pais;
	}
	
	public String getCiudad(){
		return ciudad;
	}
	
	public String getPais() {
		return pais;
	}

}

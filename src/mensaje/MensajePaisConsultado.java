package practica5.mensaje;

public class MensajePaisConsultado extends Mensaje{
	
	private static final long serialVersionUID = 1L;
	private String p;
	private String c;

	public MensajePaisConsultado(KindM t, String origen, String destino, String c, String p) {
		super(t, origen, destino);
		this.c = c;
		this.p = p;
	}
	
	public String getPais() {
		return p;
	}
	
	public String getCiudad() {
		return c;
	}

}

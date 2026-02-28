package practica5.mensaje;

public class MensajePreparadoCS extends Mensaje {
	
	private String receptor;
	private int puerto;

	public MensajePreparadoCS(String origen, String destino, String receptor, int puerto) {
		super(KindM.M_PREPARADO_CS, origen, destino);
		this.receptor = receptor;
		this.puerto = puerto;
	}

	private static final long serialVersionUID = 1L;
	
	public String getReceptor() {
		return receptor;
	}
	
	public int getPuerto() {
		return puerto;
	}

}

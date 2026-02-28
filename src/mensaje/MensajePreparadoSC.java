package practica5.mensaje;

public class MensajePreparadoSC extends Mensaje {
	
	private int puerto;
	private String IPEmisor;
	
	public MensajePreparadoSC(String origen, String destino, int puerto, String IPEmisor) {
		super(KindM.M_PREPARADO_SC, origen, destino);
		this.puerto = puerto;
		this.IPEmisor = IPEmisor;
	}

	private static final long serialVersionUID = 1L;
	
	public int getPuerto() {
		return puerto;
	}
	
	public String getIPEmisor() {
		return IPEmisor;
	}

}

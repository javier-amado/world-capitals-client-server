package practica5.mensaje;

import java.io.Serializable;

public abstract class Mensaje implements Serializable { //serializables para poder mandarlos
	private static final long serialVersionUID = 1L;
	private KindM tipo;
	private String o;
	private String d;
	
	public Mensaje(KindM t, String origen, String destino) {
		this.tipo = t;
		this.d = destino;
		this.o = origen;
	}
	
	public KindM getTipoM() {
		return tipo;
	}
	
	public String getOrigen() {
		return o;
	}
	
	public String getDestino() {
		return d;
	}
}

package practica5.estructuras;

public class Entero {

	private volatile int entero;
	
	public Entero(int value) {
		entero = value;
	}
	
	public void incrementar() {
		entero++;
	}
	
	public void decrementar() {
		entero--;
	}
	
	public int getEntero() {
		return entero;
	}
	
	public void setEntero(int e) {
		entero = e;
	}
}
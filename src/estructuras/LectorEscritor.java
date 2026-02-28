package practica5.estructuras;

public abstract class LectorEscritor {

	public abstract void request_read() throws InterruptedException;
	
	public abstract void release_read();
	
	public abstract void request_write() throws InterruptedException;
	
	public abstract void release_write();

}

package practica5.estructuras;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import practica5.mensaje.Mensaje;

public class ObjectOutputStreamConcurrent {
	
	private ObjectOutputStream fout;
	private LectorEscritor proteccion;
	
	
	public ObjectOutputStreamConcurrent(OutputStream s) throws IOException {
		fout = new ObjectOutputStream(s);
		this.proteccion = new Semaforo();
	}
	
	public void writeObjectConcurrent(Mensaje m) throws IOException, InterruptedException {
		proteccion.request_write();
		fout.writeObject(m);
		fout.flush();
		proteccion.release_write();
	}
	
	public void closeConcurrent() throws IOException, InterruptedException {
		proteccion.request_write();
		fout.close();
		proteccion.release_write();
	}

}

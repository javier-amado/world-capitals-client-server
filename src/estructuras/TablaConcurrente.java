package practica5.estructuras;

import java.util.*;

public class TablaConcurrente<T>{
	
	private HashMap<String, T> tabla;
	protected LectorEscritor proteccion;
	
	public TablaConcurrente() {
		tabla = new HashMap<String, T>();
		proteccion = new Monitor();
	}
	
	public HashMap<String,T> getTabla() {
		return tabla;
	}
	
	public void put(String clave, T valor) throws InterruptedException  {
		proteccion.request_write();
		tabla.put(clave,valor);
		proteccion.release_write();
	}
	
	public T get(String clave) throws InterruptedException {
		proteccion.request_read();
		T valor = tabla.get(clave);
		proteccion.release_read();
		
		return valor;
	}
	
	public void remove(String clave) throws InterruptedException{
		proteccion.request_write();
		tabla.remove(clave);
		proteccion.release_write();
	}
	
	public int tam() throws InterruptedException {
		proteccion.request_read();
		int tam = tabla.size();
		proteccion.request_write();
		return tam;
	}
	
}

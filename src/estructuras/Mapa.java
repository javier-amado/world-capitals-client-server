package practica5.estructuras;

import java.util.HashMap;
import java.util.Map;


public class Mapa {
	
	private HashMap<String, String> ciudad_pais; //lo primero del mapa es la ciudad y lo segundo el pais
	private LectorEscritor proteccion;

	public Mapa(HashMap<String, String> mapa) {
		ciudad_pais = mapa;
		proteccion = new Semaforo();
	}	

	public boolean find(String c) throws InterruptedException {
		proteccion.request_read();
		for(Map.Entry<String, String> m: ciudad_pais.entrySet()) {
			if(m.getKey().equals(c)) {
				proteccion.release_read();
				return true;
			}
		}
		proteccion.release_read();
		return false;
	}
	
	public void put(String c, String p) throws InterruptedException {
		proteccion.request_write();
		ciudad_pais.put(c, p);
		proteccion.release_write();
	}
	
	public String getPais(String c) throws InterruptedException {
		String pais;
		proteccion.request_read();
		pais = ciudad_pais.get(c);
		proteccion.release_read();
		return pais; //devuelve el pais de esa ciudad
	}
	
	public HashMap<String, String> getMapa() throws InterruptedException{
		HashMap<String, String> mapa;
		proteccion.request_read();
		mapa = ciudad_pais;
		proteccion.release_read();
		return mapa;
	}
	
	public String toString() {
		String toString;
		try {
			proteccion.request_read();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		toString = ciudad_pais.toString();
		proteccion.release_read();
		return toString;
	}
}

package practica5.client;

import java.util.HashMap;

import practica5.estructuras.Mapa;

public class Usuario{
	
	private String IP;
	private String nombre;
	private Mapa mapaUsuario; //cada usuario tiene su propio mapa
	
	public Usuario(String nombre, HashMap<String, String> ciudades, String IP) {
		this.IP = IP;
		this.nombre = nombre;
		this.mapaUsuario = new Mapa(ciudades);
	}
	
	public String getIP() {
		return IP;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Mapa getMapa() {
		return mapaUsuario;
	}
	
	public void addCiudadPaisMapa(String c, String p) throws InterruptedException {
		mapaUsuario.put(c, p);
	}
	
	public boolean find(String ciudad) throws InterruptedException {
		return mapaUsuario.find(ciudad);
	}

}

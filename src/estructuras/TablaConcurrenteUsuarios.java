package practica5.estructuras;

import java.util.Map;

import practica5.client.Usuario;

public class TablaConcurrenteUsuarios extends TablaConcurrente<Usuario>{
	
	public TablaConcurrenteUsuarios() {
		super();
	}
	
	public String encuentraCiudad(String c) throws InterruptedException {
		proteccion.request_read();
		for(Map.Entry<String, Usuario> u: this.getTabla().entrySet()) {
			if(u.getValue().find(c)) {
				proteccion.release_read();
				return u.getKey(); //este es el nombre del usuario que tiene la ciudad
			}
		}
		proteccion.release_read();
		return "";
	}
	
	public String getUsuarios() throws InterruptedException{
		String s = "Lista de usuarios: " + "\n";
		proteccion.request_read();
		for(Map.Entry<String, Usuario> u: this.getTabla().entrySet()) {
			s = s + (u.getValue().getNombre() + "\n");
		}
		proteccion.release_read();
		return s.toString();
	}
	
	public void addCiudadPais(String nombreUsuario, String ciudad, String pais) throws InterruptedException {
		proteccion.request_write();
		this.getTabla().get(nombreUsuario).addCiudadPaisMapa(ciudad, pais);
		proteccion.release_write();
	}
	
	
}

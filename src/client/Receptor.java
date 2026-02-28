package practica5.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import practica5.estructuras.ObjectOutputStreamConcurrent;
import practica5.mensaje.KindM;
import practica5.mensaje.MensajePaisConsultado;


public class Receptor extends Thread{

	private Socket sc;
	private ObjectInputStream finR;
	private ObjectOutputStreamConcurrent foutC;
	private int port;
	private String IPEmisor;
	private Usuario usr;
	private String ciudadPreguntada;
	
	public Receptor(int puerto, String ipEmisor, String ciudadPreguntada, Usuario usr, ObjectOutputStreamConcurrent foutC) throws IOException {
		this.port = puerto;
		this.IPEmisor = ipEmisor;
		this.usr = usr;
		this.ciudadPreguntada = ciudadPreguntada;
		this.foutC = foutC;
	}
	
	public void run() {
		try {
			sc = new Socket(IPEmisor, port); //creamos la conexion con el emisor a traves del puerto correspondiente
			finR = new ObjectInputStream(sc.getInputStream());
			String p = (String) finR.readObject();
			usr.addCiudadPaisMapa(ciudadPreguntada, p);
			
			foutC.writeObjectConcurrent(new MensajePaisConsultado(KindM.M_PAIS_CONSULTADO, usr.getIP(), "localhost", ciudadPreguntada, p));
			sc.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

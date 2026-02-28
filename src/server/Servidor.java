package practica5.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import practica5.estructuras.TablaConcurrenteInputs;
import practica5.estructuras.TablaConcurrenteOutputs;
import practica5.estructuras.TablaConcurrenteUsuarios;

public class Servidor {
	
	public static void main(String args[]) {
		try {
			TablaConcurrenteUsuarios tablaUsuarios = new TablaConcurrenteUsuarios();
			TablaConcurrenteInputs tablafins = new TablaConcurrenteInputs();
			TablaConcurrenteOutputs tablafouts = new TablaConcurrenteOutputs();
			System.out.println("Hemos creado el servidor.");
			ServerSocket listen = new ServerSocket(999);
			
			while(true) {
				Socket sc = listen.accept();
				System.out.println("Hemos creado el socket.");
				
				OyenteCliente oc = new OyenteCliente(sc, tablaUsuarios, tablafins, tablafouts); //le pasamos el Socket 
				oc.start();
			}
			// listen.close(); esto no lo podemos poner por el while true. Podriamos limitarlo a un numero maximo de conexiones, pero así es mas sencillo.
			// el servidor debe pararse manualmente
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

package practica5.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Emisor extends Thread{

	private ServerSocket listen;
	private Socket sc;
	private String paisEmitir;
	private int port;
	private ObjectOutputStream foutE;
	
	public Emisor(String p) throws IOException {
		listen = new ServerSocket(0); //pone un puerto aleatoriamente
		this.port = listen.getLocalPort(); //para coger el puerto que ha puesto antes
		this.paisEmitir = p;
	}
	
	public void run() {
		try {
				// si queremos imprimir aqui hay, deberiamos controlarlo con el lock
				sc = listen.accept();
				foutE = new ObjectOutputStream(sc.getOutputStream());
				foutE.writeObject(paisEmitir);
				foutE.flush();
				sc.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public int getPuerto() {
		return port;
	}

}

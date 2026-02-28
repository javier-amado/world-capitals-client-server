package practica5.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import practica5.estructuras.Lock;
import practica5.estructuras.LockRompeEmpate;

public class Cliente {
	
	private Lock l;
	private Scanner s;
	private Usuario usuario;
	private Socket sc;
	private OyenteServidor os;
	
	public static void main(String args[]) {
		try {
			Cliente c = new Cliente();
			c.inicializar();
			c.menuUsuario();
			c.cerrar();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void inicializar() throws IOException {
		
		l = new LockRompeEmpate(2);//lo inicializamos a dos porque se controlará entre cliente y oyenteServidor
		
		
		System.out.print("Introduzca su nombre de usuario: ");
		s = new Scanner(System.in); 
		String nombreUsuario = s.nextLine(); //leemos el nombre de usuario
		usuario = new Usuario(nombreUsuario, solicitudCiudadesPaises(), "localhost"); //localhost por conectarse a nuestro ordenador
		
		sc = new Socket("localhost", 999); //el host es localhost
		
		os = new OyenteServidor(sc, usuario, l);
		os.start(); //empezamos el oyente servidor
		
		// añadimos esto para que lo mensajes queden más bonitos. No por ningún otro motivo.
		
		l.takeLock(1);
		System.out.println("Introduzca enter para mostrar el menú...");
		l.releaseLock(1);
		
		s.nextLine();
		
	}
	
	private HashMap<String, String> solicitudCiudadesPaises(){ //no hace falta proteger la entrada por consola todavia porque no se ha iniciado el oyente servidor

		HashMap<String, String> mapa = new HashMap<String, String>();
		System.out.println("Introduzca la ciudad y su correspondiente pais. Para terminar, introduzca 'f' en la ciudad.");
		System.out.print("Nombre ciudad:");
		String aux = s.nextLine();
		String p = "";
		if(!aux.equals("f")) {
			System.out.print("Nombre país:");
			p = s.nextLine();
		}
		while(!aux.equals("f")) {
			mapa.put(aux, p);
			System.out.print("Nombre ciudad:");
			aux = s.nextLine();
			if(!aux.equals("f")) {
				System.out.print("Nombre país:");
				p = s.nextLine();
			}
		}
		return mapa;
	}
	
	private void cerrar() throws InterruptedException, IOException {
		os.join(); //esperamos a que el hilo termine
		sc.close();
		System.out.println("Socket del cliente cerrado."); //no hay que protegerlo porque ya se ha cerrado el oyente servidor
	}
	
	private void menuUsuario() throws IOException, InterruptedException {
		// tenemos un menú que sea ver la lista de usuarios, dado una ciudad te de el pais al que pertenece.
		int opcion = -1;
		while(opcion != 0) {
			
			l.takeLock(1);
			System.out.println("-------------------------------");
			System.out.println("MENU USUARIO");
			System.out.println("0. SALIR.");
			System.out.println("1. DIME EL PAIS AL QUE PERTENECE LA CIUDAD.");
			System.out.println("2. DAME LA LISTA DE USUARIOS");
			System.out.println("-------------------------------");
			l.releaseLock(1);
			
			l.takeLock(1);
			System.out.print("Opción:");
			l.releaseLock(1);

			opcion = s.nextInt();
			s.nextLine(); //para salto de linea
			l.takeLock(1);
			System.out.println("-------------------------------");
			l.releaseLock(1);
			
			switch(opcion) {
			case 0:
				os.mandarMensajeCierreConexion();
				break;
			case 1:
				l.takeLock(1);
				System.out.print("Introduzca la ciudad:");
				l.releaseLock(1);
				String ciudad = s.nextLine();
				os.mandarMensajePedirCiudad(ciudad, usuario.getNombre());
				break;
			case 2:
				os.mandarMensajeListaUsuarios();
				break;
			}
			
			l.takeLock(1);
			System.out.println("Introduzca enter para volver a mostrar el menú...");
			l.releaseLock(1);
			
			s.nextLine();	
			
		}
	}

}

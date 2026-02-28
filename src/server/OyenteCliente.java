package practica5.server;

import java.io.IOException;
import java.io.ObjectInputStream;

import practica5.client.Usuario;
import practica5.estructuras.ObjectOutputStreamConcurrent;
import practica5.estructuras.TablaConcurrenteInputs;
import practica5.estructuras.TablaConcurrenteOutputs;
import practica5.estructuras.TablaConcurrenteUsuarios;
import practica5.mensaje.*;

import java.net.Socket;

public class OyenteCliente extends Thread {
	
	private Socket sc;
	private TablaConcurrenteUsuarios tablaUsuarios;
	private TablaConcurrenteInputs tablaFins;
	private TablaConcurrenteOutputs tablaFouts;
	private ObjectInputStream fins;
	private ObjectOutputStreamConcurrent fouts;
	private boolean listening;
	private String nombreUsuario;
	
	public OyenteCliente(Socket sc, TablaConcurrenteUsuarios tablaUsuarios, TablaConcurrenteInputs tablaFins, TablaConcurrenteOutputs tablaFouts) {
		this.sc = sc;
		this.tablaUsuarios = tablaUsuarios;
		this.tablaFins = tablaFins;
		this.tablaFouts = tablaFouts;
		this.listening = true;
	}
	
	public void run() {
		
		try {
			fins = new ObjectInputStream(sc.getInputStream());
			fouts = new ObjectOutputStreamConcurrent(sc.getOutputStream());
			
			while(listening) {
				Mensaje m = (Mensaje) fins.readObject(); //escuchamos el canal y leemos el mensaje.
				// solo ponemos los mensajes que el servidor va a leer.
				switch(m.getTipoM()) {
					case M_CONEXION:
						gestionarMensajeConexion((MensajeConexion) m);
						break;
					case M_LISTA_USUARIOS:
						gestionarMensajeListaUsuarios((MensajeListaUsuarios) m);
						break;
						
					case M_PEDIR_CIUDAD:
						gestionarMensajePedirCiudad((MensajePedirCiudad) m);
						break;
						
					case M_PREPARADO_CS:
						gestionarMensajePreparadoCS((MensajePreparadoCS) m);
						break;
					case M_PAIS_CONSULTADO:
						gestionarMensajePaisConsultado((MensajePaisConsultado) m);
						break;
					case M_CERRAR_CONEXION:
						gestionarMensajeCerrarConexion((MensajeCerrarConexion) m);
						break;
					default:
						break;
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gestionarMensajeConexion(MensajeConexion m) throws InterruptedException, IOException {
		nombreUsuario = m.getNombreUsuario();
		Usuario usuarioEncontrado = tablaUsuarios.get(nombreUsuario); //buscamos si este usuario existe
		
		if(usuarioEncontrado == null) { //el usuario no está en la tabla todavía
			tablaUsuarios.put(nombreUsuario, new Usuario(nombreUsuario, m.getMapa(), m.getOrigen()));
			fouts.writeObjectConcurrent(new MensajeConfirmacionConexion("localhost", m.getOrigen(), true)); //el origen es la IP del usuario
		}
		else { //si no, falla la conexión
			fouts.writeObjectConcurrent(new MensajeConfirmacionConexion("localhost", m.getOrigen(), false)); //la conexión no ha funcionado;
			listening = false;
			return;
		}
		
		// añadimos las referencias a los canales de entrada y de salida
		tablaFins.put(nombreUsuario, fins);
		tablaFouts.put(nombreUsuario, fouts);
		
	}

	private void gestionarMensajeListaUsuarios(MensajeListaUsuarios m) throws IOException, InterruptedException {
		fouts.writeObjectConcurrent(new MensajeConfirmacionListaUsuarios("localhost", m.getOrigen(), tablaUsuarios.getUsuarios()));
	}
	
	private void gestionarMensajePedirCiudad(MensajePedirCiudad m) throws IOException, InterruptedException {
		
		String ciudadPedida = m.getCiudad();
		String usuarioPais = tablaUsuarios.encuentraCiudad(ciudadPedida);
		Usuario u = tablaUsuarios.get(usuarioPais);
		if(usuarioPais.equals("")) {
			System.out.println("No se ha encontrado el país de esa ciudad.");
			// aqui podríamos mandar algo de vuelta que falle????
			return ;
		}
		
		ObjectOutputStreamConcurrent foutE = tablaFouts.get(usuarioPais);
		foutE.writeObjectConcurrent(new MensajeEmitirCiudad("localhost", u.getIP(), ciudadPedida, m.getNombreUsuario()));

	}
	
	private void gestionarMensajePreparadoCS(MensajePreparadoCS m) throws IOException, InterruptedException {
		
		ObjectOutputStreamConcurrent foutReceptor = tablaFouts.get(m.getReceptor()); // para mandarle el mensaje al receptor del pais de la ciudad pedida	
		foutReceptor.writeObjectConcurrent(new MensajePreparadoSC("localhost", tablaUsuarios.get(m.getReceptor()).getIP(), m.getPuerto(), m.getOrigen()));
	}

	private void gestionarMensajePaisConsultado(MensajePaisConsultado m) throws IOException, InterruptedException {
		
		tablaUsuarios.addCiudadPais(nombreUsuario, m.getCiudad(), m.getPais());
		fouts.writeObjectConcurrent(new MensajePaisConsultadoConfirmacion(KindM.M_PAIS_CONSULTADO_CONFIRMACION, "localhost", m.getOrigen(), m.getCiudad(), m.getPais()));
		
	}
	
	private void gestionarMensajeCerrarConexion(MensajeCerrarConexion m) throws IOException, InterruptedException {
		
		tablaUsuarios.remove(nombreUsuario); //lo borramos de la tabla
		tablaFouts.remove(nombreUsuario);
		tablaFins.remove(nombreUsuario);
		fouts.writeObjectConcurrent(new MensajeCerrarConexionConfirmacion("localhost", m.getOrigen()));
		listening = false; //se deja de escuchar
		
	}
}

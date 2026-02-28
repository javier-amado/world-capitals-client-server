package practica5.mensaje;


public class MensajeConfirmacionListaUsuarios extends Mensaje{

	private static final long serialVersionUID = 1L;
	
	private String usuarios;

	public MensajeConfirmacionListaUsuarios(String origen, String destino, String usuarios) {
		super(KindM.M_CONFIRMACION_LISTA_USUARIOS, origen, destino);
		this.usuarios = usuarios;
	}
	
	public String getListaUsuariosString() {
		return usuarios;
	}

}

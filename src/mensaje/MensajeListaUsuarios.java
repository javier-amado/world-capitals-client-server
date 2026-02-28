package practica5.mensaje;

public class MensajeListaUsuarios extends Mensaje{

	public MensajeListaUsuarios(String origen, String destino) {
		super(KindM.M_LISTA_USUARIOS, origen, destino);
	}

	private static final long serialVersionUID = 1L;

}

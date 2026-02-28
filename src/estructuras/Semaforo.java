package practica5.estructuras;

import java.util.concurrent.Semaphore;

public class Semaforo extends LectorEscritor {

	/*
	 * Esta clase recoge la funcionalidad asociada a la lectura y escritura con
	 * semáforos
	 */
	// Está implementada con paso de testigo y prioridad para los escritores.
	private Semaphore e;
	private Semaphore r;
	private Semaphore w;

	private Entero nr;
	private Entero nw;
	private Entero dr;
	private Entero dw; // para hacerlo justo

	public Semaforo() {
		e = new Semaphore(1);
		r = new Semaphore(0);
		w = new Semaphore(0);
		nr = new Entero(0);
		nw = new Entero(0);
		dr = new Entero(0);
		dw = new Entero(0);
	}

	@Override
	public void request_read() throws InterruptedException {
		try {
			e.acquire();
			if (nw.getEntero() > 0 || dw.getEntero() > 0) {/* Si hay algun escritor escribiendo o esperando, espero */
				dr.setEntero(dr.getEntero() + 1);
				e.release();
				r.acquire();
			}

			nr.setEntero(nr.getEntero() + 1);

			if (dr.getEntero() > 0) {/* Despierto al resto de lectores */
				dr.setEntero(dr.getEntero() - 1);
				r.release();
			}

			else
				e.release();
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void release_read() {
		try {
			e.acquire();
			nr.setEntero(nr.getEntero() - 1);
			if (nr.getEntero() == 0 && dw.getEntero() > 0) { /* Despertamos solo a uno */
				dw.setEntero(dw.getEntero() - 1);
				w.release();
			} else
				e.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void request_write() throws InterruptedException {
		e.acquire();
		if (nr.getEntero() > 0 || nw.getEntero() > 0) {
			dw.setEntero(dw.getEntero() + 1);/* Aumenta el contador de escritores en espera */
			e.release();/* Soltamos el semaforo de entrada */

			// Aqui esperan los escritores
			w.acquire(); /* Esperamos a que los escritores puedan entrar */
		}

		nw.setEntero(nw.getEntero() + 1);

		e.release(); /* Suelta el semaforo */

	}

	@Override
	public void release_write() {
		try {
			e.acquire();

			nw.setEntero(nw.getEntero() - 1);

			if (dr.getEntero() > 0) {/* Si no hay ningun escritor esperando pero si algun lector */
				dr.setEntero(dr.getEntero() - 1);
				r.release(); /* Despertamos al reader */
			}

			else if (dw.getEntero() > 0) {/* Si hay algun escritor esperando */
				dw.setEntero(dw.getEntero() - 1);
				w.release();/* Despertamos al escritor porque puede entrar */
			} else {
				e.release(); /* Si soy el ultimo suelto el mutex */
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}

package practica5.estructuras;

import java.util.concurrent.atomic.AtomicInteger;

public class LockAlgoritmoTicket extends Lock {
	
	// Todas las variables que sean compartidas o del lock deben ser volatiles
	private Entero next; 
	private AtomicInteger number;
	private Entero[] turn;
	
	public LockAlgoritmoTicket(int N) {
		super(N);
		number = new AtomicInteger(1);
		next = new Entero(1);
		turn = new Entero[N];
		inicializar(turn);
		
	}
	private void inicializar(Entero[] arr) {
		for(int i = 0; i < N; i++) {
			arr[i] = new Entero(0);
		}
	}
	@Override
	public void takeLock(int i){
		turn[i].setEntero(number.getAndAdd(1));
		while(turn[i].getEntero() != next.getEntero()) {}
		
	}
	
	@Override
	public void releaseLock(int i){
		next.incrementar();
		System.out.println("Voy a soltar el lock");
	}

}
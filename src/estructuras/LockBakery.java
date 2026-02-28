package practica5.estructuras;


public class LockBakery extends Lock {
	
	private Entero[] turn;
	
	public LockBakery(int N) {
		super(N);
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
		turn[i].setEntero(1);
		turn[i].setEntero(maximo(turn) +1);
		
		for(int j = 0; j < N; j++) {
			if(j == i) continue;
			while(turn[j].getEntero() != 0 && operator(turn[i], i, turn[j], j)) {}
		}
	}
	
	@Override
	public void releaseLock(int i){
		turn[i].setEntero(0);
	}
	
	private int maximo(Entero[] arr) {
		int max = -1;
		for(int i = 0; i < N; i++) {
			if(arr[i].getEntero() > max) {
				max = arr[i].getEntero();
			}
		}
		return max;
	}
	
	private boolean operator(Entero turn1, int i, Entero turn2, int j) {
		return (turn1.getEntero() > turn2.getEntero() || (turn1.getEntero() == turn2.getEntero() && i > j));
	}


}
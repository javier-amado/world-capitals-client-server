package practica5.estructuras;

public class LockRompeEmpate extends Lock {
	
	private Entero[] in, last;
	
	public LockRompeEmpate(int N) {
		super(N);
		
		last = new Entero[N]; //last[i] representa el ultimo que ha llegado al estado i
		inicializar(last);
		
		in = new Entero[N]; //in[i] representa en qué estado está el proceso i
		inicializar(in);
	}
	private void inicializar(Entero[] arr) {
		for(int i = 0; i < N; i++) {
			arr[i] = new Entero(0);
		}
	}
	@Override
	public void takeLock(int i){ //CS[1] waits if there is some other process in a higher or equal numbered stage and cs[1] was the last process to enter stage j. 
		
		for(int j = 1; j <= N; j++) {
			in[i-1].setEntero(j);
			last[j-1].setEntero(i);
			
			for(int k = 1; k <= N; k++) {
				if(k == i) continue;
				// mientras la etapa del proceso k >= la etapa del proceso i && el id del ultimo proceso que llego a la etapa j sea i
				while(in[k-1].getEntero() >= in[i-1].getEntero() && last[j-1].getEntero() == i);
			}
		}
	}
	
	@Override
	public void releaseLock(int i){
		in[i-1].setEntero(0);
	}

}
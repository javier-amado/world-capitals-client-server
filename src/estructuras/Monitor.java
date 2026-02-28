package practica5.estructuras;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends LectorEscritor{

	private Entero nr, nw;
	private final Lock lock;
	private final Condition oktoread;
	private final Condition oktowrite;
	
	public Monitor() {
		nr = new Entero(0);
		nw = new Entero(0);
		lock = new ReentrantLock();
		oktoread = lock.newCondition();
		oktowrite = lock.newCondition();
	}
	
	public void request_read() {
		lock.lock();
		while(nw.getEntero() > 0) {
			try {
				oktoread.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nr.setEntero(nr.getEntero() + 1);
		lock.unlock();
		
	}
	public void request_write() {
		lock.lock();
		while(nw.getEntero() > 0 || nr.getEntero() > 0) {
			try {
				oktowrite.await();
			}catch(Exception e) {
				
			}
		}
		nw.setEntero(nw.getEntero() + 1);
		lock.unlock();
	}
	
	public void release_read() {	
		lock.lock();
		nr.setEntero(nr.getEntero()-1);
		if(nr.getEntero() == 0) {
			oktowrite.signal();
		}
		lock.unlock();
	}
	public void release_write() {
		lock.lock();
		nw.setEntero(nw.getEntero()-1);
		oktowrite.signal();
		oktoread.signalAll();
		lock.unlock();
	}
	
	
}

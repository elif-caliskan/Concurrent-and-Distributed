//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment2
public class BinarySemaphore { // used for mutual exclusion
	boolean value;
	public BinarySemaphore(boolean initValue) {
		value = initValue;
	}
	public synchronized void P() { // atomic operation // blocking
		while (value == false) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		value = false;
	}
	public synchronized void V() { // atomic operation // non-blocking
		value = true;
		notify(); // wake up a process from the queue
	}
} 
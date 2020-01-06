//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment2
//in countRunnable, the thread increments the same i value
class CountRunnable  implements Runnable{
    public int c = 0;
    //this method increments c field
    public void increment() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c++;
    }
    
    @Override
    public void run() {
    	// to see the result of increment, the result is printed
        this.increment();
        System.out.println("Value of c after " + Thread.currentThread().getName() + " increment " + c);   
    }
}

public class Race{
    public static void main(String[] args) {
    	//a new runnable is created and the field inside is incremented in three different threads
        CountRunnable counter = new CountRunnable();
        Thread t1 = new Thread(counter, "\'First thread\'");
        Thread t2 = new Thread(counter, "\'Second Thread\'");
        Thread t3 = new Thread(counter, "\'Third Thread\'");
        t1.start();
        t2.start();
        t3.start();
        try {
        	//every thread is waited to finish
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //after three increment functions, the value should be 3 but we can see that the value changes after each iteration
        System.out.println("Value of c after three increments: "+ counter.c);  
    }    
}
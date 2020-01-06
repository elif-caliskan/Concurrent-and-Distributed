//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment2
public class Deadlock {
 
    public static void main(String[] args) {
    	
        Deadlock deadlock = new Deadlock();
        
        //creates three different resources
        final A a = deadlock.new A();
        final B b = deadlock.new B();
        final C c = deadlock.new C();
 
        // this runnable needs source a and b to run. First it gets a resource and then b resource
        Runnable block1 = new Runnable() {
            public void run() {
                synchronized (a) {
                    try {
                        // Adding delay so that both threads can start trying to
                        // lock resources
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (b) {
                    	System.out.println("In block 1, has resources "+a.name+ " and "+b.name);                    }
                }
            }
        };
 
        //this runnable needs source b and c to run. First it gets b resource and then c resource
        Runnable block2 = new Runnable() {
            public void run() {
                synchronized (b) {
                	try {
                        // Adding delay so that both threads can start trying to
                        // lock resources
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (c) {
                    	System.out.println("In block 2, has resources "+b.name+ " and "+c.name);                    }
                }
            }
        };
        //this runnable needs source c and a to run. First it gets c resource and then a resource
        Runnable block3 = new Runnable() {
            public void run() {
                synchronized (c) {
                    synchronized (a) {
                        System.out.println("In block 3, has resources "+c.name+ " and "+a.name);
                    }
                }
            }
        };
        //since every thread needs two resources and one is held by other resource,
        //there happens a deadlock and no print statement happens
        new Thread(block1).start();
        new Thread(block2).start();
        new Thread(block3).start();
    }
 
    //there are three resources A B and C
    // Resource A
    private class A {
        public String name = "A";
    }
 
    // Resource B
    private class B {
    	 public String name = "B";    
    }
    
 // Resource C
    private class C {
    	 public String name = "C";    
    }
}
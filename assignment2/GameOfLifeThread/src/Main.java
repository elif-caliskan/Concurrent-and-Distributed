//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment2
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

class CellThread extends Thread {
	private int[][] matrix;
	private int row,col;
	private int M;
	private int N;
	private int maxGenerations;
	CountingSemaphore writing;
	CountingSemaphore reading;
	BinarySemaphore mutex;	
	
	//cellthread has fields of the matrix to change, its row and column indexes, maxGenerations and writing
	//writing semaphores and a mutex
	public CellThread(int[][] matrix,int row, int col,int maxGenerations, CountingSemaphore writing,
	CountingSemaphore reading, BinarySemaphore mutex)
	{
		this.matrix=matrix;    
		this.row=row;    
		this.col=col; 
		M = matrix.length;
		N = matrix[0].length;
		this.maxGenerations = maxGenerations;
		this.writing = writing;
		this.reading = reading;
		this.mutex = mutex;
	}
	//this run method calculates new value for each cell
	public void run()
	{
		//for every generation, the neighbors are counted
		//counting neighbors means a read operation and start read function is called
		for(int i= 0; i<maxGenerations;i++) {
			startRead();
			int neighborTotal = 0;
			// it checks every possible neighbor and adds its value if it exists
			if(row>0) {
				neighborTotal+=matrix[row-1][col];
			}
			if(row<M-1) {
				neighborTotal+=matrix[row+1][col];
			}
			if(col>0) {
				neighborTotal+=matrix[row][col-1];
			}
			if(col<N-1) {
				neighborTotal+=matrix[row][col+1];
			}
			if(row>0&&col>0) {
				neighborTotal+=matrix[row-1][col-1];
			}
			if(row>0&&col<N-1) {
				neighborTotal+=matrix[row-1][col+1];
			}
			if(row<M-1&&col>0) {
				neighborTotal+=matrix[row+1][col-1];
			}
			if(row<M-1&&col<N-1) {
				neighborTotal+=matrix[row+1][col+1];
			}
			//after the calculation of neighbortotal, read ends
			endRead();
			//for writing to start, every read operation should end. Then start write is called
			//changing the corresponding value of matrix is writing operation
			startWrite();
			//the following  part is given in description it updates the value by checking these statements
			if(matrix[row][col]==1 && (neighborTotal==2 || neighborTotal==3)) {
				matrix[row][col] = 1;
			}
			else if(matrix[row][col]==1 && (neighborTotal<2 || neighborTotal>3)) {
				matrix[row][col] = 0;
			}
			else if(matrix[row][col]==0 && neighborTotal==3) {
				matrix[row][col] = 1;
			}
			else if(matrix[row][col]==0 && (neighborTotal<3 || neighborTotal>3)) {
				matrix[row][col] = 0;
			}
			//after update, write is done
			endWrite();
		}
		
	}
	//in startRead, first one reading is acquired. Then it checks if this is the first reading.
	//If it is, then every write is also acquired in a critical section.
	public void startRead() {		
		reading.P();
		mutex.P();
		if(reading.value == M*N-1) {
			for (int i=0; i<M*N; i++)
				writing.P();
		}
		mutex.V();
	}
	//In endRead, if this is the last read, then every write is released
	public void endRead() {
		if(reading.value==0) {
			for (int i=0; i<M*N; i++) {
				writing.V();
			}	
		}
	}
	//in startWrite, first one write is acquired, then if this is the first write,
	//every read is acquired so that no cell can get to other generation
	public void startWrite() {
		writing.P();
		mutex.P();
		if(writing.value == M*N-1) {
			for (int i=0; i<M*N; i++)
				reading.P();
		}
		mutex.V();
	}
	//in end write, if this is the last write, every read is released
	public void endWrite() {
		if(writing.value == 0) {
			for (int i=0; i<M*N; i++) {
				reading.V();
			}		
		}
	}
}

public class Main {
	public static void main(String[] args){ 
		//first the arguments are read and they are checked if they have a proper value
		int M = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		int maxGenerations = Integer.parseInt(args[2]);
		if(M<1 || N<1 || maxGenerations<0) {
			System.out.println("Please run the code again with positive arguments");
			return;
		}
		Random rand = new Random();
		//the raw matrix is created 
		int[][] matrix = new int[M][N];
		//if the argument has file path then matrix is filled according to the file
		if(args.length>3) {
			File file = new File(args[3]);
			Scanner sc;
			try {
				sc = new Scanner(file);
				for(int i= 0;i<M;i++) {
					for(int j = 0;j<N;j++) {
						int res = sc.nextInt();
						//checks if matrix consists of only 0s and 1s
						if(res !=0 && res!=1) {
							System.out.print("The matrix doesn't consist of 0s and 1s.");
							return;
						}
						matrix[i][j] = res;
					}
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
				}
				sc.close();
				//if the file is not found then it prints the exception message
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		//if there isn't a path in arguments, the matrix is filled automatically by random 0s and 1s
		else {
			for(int i = 0; i< M; i++) {
				for(int j = 0; j < N; j++) {
					matrix[i][j] = rand.nextInt(2);
				}
			}
		}
		//it prints the matrix to console for comparison
		System.out.println("Initial matrix :");
		for(int a = 0;a<M;a++) {
			for(int b =0;b<N;b++) {
				System.out.print(matrix[a][b]);
			}
			System.out.println();
		}  
		//semaphores are created with the number of M*N total cell count
		CountingSemaphore reading = new CountingSemaphore(M*N);
		CountingSemaphore writing = new CountingSemaphore(M*N);
		CellThread threads[][] = new CellThread[M][N];
		BinarySemaphore mutex= new BinarySemaphore(true);
		//for every cell, a new thread is created
		for(int a=0;a<M;a++) {
			for(int b=0;b<N;b++) {
				threads[a][b] = new CellThread(matrix,a,b,maxGenerations, writing, reading, mutex);
			}
		}
		
		//every threads are started
		for(int a=0;a<M;a++) {
			for(int b=0;b<N;b++) {
				threads[a][b].start();
			}
		}
		//every thread is waited to end
		for(int a=0;a<M;a++) {
			for(int b=0;b<N;b++) {
				try {
					threads[a][b].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//it prints the generated matrix to console 
		System.out.println("After Generation : "+ maxGenerations );
		for(int a = 0;a<M;a++) {
			for(int b =0;b<N;b++) {
				System.out.print(matrix[a][b]);
			}
			System.out.println();
		}
	}
}

//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment1
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class GameofLife {
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
        //for every generation, it creates a new generationMatrix and finds total neighbor count
        for(int i= 0; i<maxGenerations;i++) {
        	int[][] generationMatrix = new int[M][N];
        	for(int a = 0; a<M ;a++) {
        		for(int b = 0; b<N; b++) {
                	int neighborTotal = 0;
                	// it checks every possible neighbor and adds its value if it exists
                	if(a>0) {
                		neighborTotal+=matrix[a-1][b];
                	}
                	if(a<M-1) {
                		neighborTotal+=matrix[a+1][b];
                	}
                	if(b>0) {
                		neighborTotal+=matrix[a][b-1];
                	}
                	if(b<N-1) {
                		neighborTotal+=matrix[a][b+1];
                	}
                	if(a>0&&b>0) {
                		neighborTotal+=matrix[a-1][b-1];
                	}
                	if(a>0&&b<N-1) {
                		neighborTotal+=matrix[a-1][b+1];
                	}
                	if(a<M-1&&b>0) {
                		neighborTotal+=matrix[a+1][b-1];
                	}
                	if(a<M-1&&b<N-1) {
                		neighborTotal+=matrix[a+1][b+1];
                	}
                	//the following  part is given in description it updates the value by checking these statements
                	if(matrix[a][b]==1 && (neighborTotal==2 || neighborTotal==3)) {
                		generationMatrix[a][b] = 1;
                	}
                	else if(matrix[a][b]==1 && (neighborTotal<2 || neighborTotal>3)) {
                		generationMatrix[a][b] = 0;
                	}
                	else if(matrix[a][b]==0 && neighborTotal==3) {
                		generationMatrix[a][b] = 1;
                	}
                	else if(matrix[a][b]==0 && (neighborTotal<3 || neighborTotal>3)) {
                		generationMatrix[a][b] = 0;
                	}
        		}
        	}
        	matrix = generationMatrix;
        }
        //it prints the generated matrix to console 
        System.out.println("After Generation : "+maxGenerations);
    	for(int a = 0;a<M;a++) {
    		for(int b =0;b<N;b++) {
    			System.out.print(matrix[a][b]);
    		}
    		System.out.println();
    	}        
	}
}

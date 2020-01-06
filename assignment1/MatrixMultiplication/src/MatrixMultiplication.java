//Elif Caliskan // 2016400183 // elif.caliskan330@gmail.com
// CMPE436-Assignment1
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MatrixMultiplication {
	public static void main(String[] args) { 
		//some variables are defined and initialized here
		int row1 = 0;
		int column1 = 0;
		int[][] matrix1 = null;
		int row2 = 0;
		int column2 = 0;
		int[][] matrix2 = null;
		//the first matrix is read and filled from the first file
		File file = new File(args[0]); 
		Scanner sc;
		try {
			sc = new Scanner(file);
			row1 = sc.nextInt();
			column1 = sc.nextInt();
			sc.nextLine();
			//the dimensions are checked
			if(row1<1 || column1<1) {
				System.out.println("Please run the code again with proper dimensions");
				return;
			}
			//matrix1 is filled according to the file
			matrix1 = new int[row1][column1];
		  	for(int a = 0; a < row1; a++) {
	    		for(int b=0 ; b<column1; b++) {
	    			//checks if the file and dimensions are consistent
	    			if(sc.hasNextInt()) {
	    				matrix1[a][b] = sc.nextInt();
	    			}
	    			else {
	    				System.out.println("Error in given dimesions please check the file1");
		    			return;
	    			}
	    		}
	    		if(sc.hasNextLine()) {
		    		sc.nextLine();
	    		}
	    		//checks if the file and dimensions are consistent
	    		else if(a<row1-1) {
	    			System.out.println("Error in given dimesions please check the file1");
	    			return;
	    		}
			}
    		//checks if the file and dimensions are consistent
		  	if(sc.hasNextInt()) {
		  		System.out.println("Error in given dimesions please check the file1");
    			return;
		  	}
	    	sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//the second matrix is read and filled from the second file
		File file2 = new File(args[1]); 
		Scanner sc2;
		try {
			sc2 = new Scanner(file2);
			row2 = sc2.nextInt();
			column2 = sc2.nextInt();
			sc2.nextLine();
			if(row2<1 || column2<1) {
				System.out.println("Please run the code again with proper dimensions");
				return;
			}
			//matrix2 is filled according to the file
			matrix2 = new int[row2][column2];
	    	for(int a = 0; a < row2; a++) {
	    		for(int b=0 ; b<column2; b++) {
	    			//checks if the file and dimensions are consistent
	    			if(sc2.hasNextInt()) {
	    				matrix2[a][b] = sc2.nextInt();
	    			}
	    			else {
	    				System.out.println("Error in given dimesions please check the file2");
		    			return;
	    			}
	    		}
	    		if(sc2.hasNextLine()) {
	    			sc2.nextLine();
	    		}
	    		//checks if the file and dimensions are consistent
	    		else if(a < row2-1) {
	    			System.out.println("Error in given dimesions please check the file2");
	    			return;
	    		}
	    	}
    		//checks if the file and dimensions are consistent
	    	if(sc2.hasNextInt()) {
	    		System.out.println("Error in given dimesions please check the file2");
    			return;
	    	}
	    	sc2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File outFile = new File(args[2]);
		PrintStream ps;
		try {
			ps = new PrintStream(outFile);
			//checks if the matrices can be multiplied
			if(row2!=column1) {
	    		System.out.println("These matrices cannot be multiplied");
	    		return;
	    	}
			System.out.println(row1 + " "+ column2);
	    	ps.println(row1 + " "+ column2);
	    	int[][] resultMatrix = new int[row1][column2];
	    	for(int i=0;i<row1;i++){    
	    		for(int j=0;j<column2;j++){    
	    			resultMatrix[i][j]=0;      
	    		for(int k=0;k<3;k++)      
	    		{   
	    			//every element of the row is multiplied with the corresponding element 
	    			//of the column in other matrix and these results are added together
	    			resultMatrix[i][j]+=matrix1[i][k]*matrix2[k][j];      
	    		}  
	    		ps.print(resultMatrix[i][j]+" ");
	    		System.out.print(resultMatrix[i][j]+" ");
	    		}
	    		ps.println();
	    		System.out.println();
	    	} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}

}

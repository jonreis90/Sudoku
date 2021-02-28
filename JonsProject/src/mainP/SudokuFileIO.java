package mainP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A file IO for the creating and reading of sudoku data 
 * @author jonre
 *
 */
public class SudokuFileIO {
	String fileName;
	BufferedWriter writer;
	BufferedReader reader;
	
	/**
	 * Creates a new file and initialises the IO read/write buffers
	 * @param fileName - the name of the file to create
	 */
	SudokuFileIO(String fileName) {
			try {
				writer=new BufferedWriter(new FileWriter(fileName,true));
				reader = new BufferedReader(new FileReader(fileName));
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	/**
	 * Adds a new line of data to the sudoku file 
	 * @param string - the string of data 
	 */
	public void addNewSudoku(String string) {
		try {
			writer.write(string+" \n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Gets a random line of sudoku data from file
	 * @return - returns the line of data converted to an integer array
	 */
	public ArrayList<Integer> getRandomSudoku(){
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> numbers= new ArrayList<Integer>();
		
		    try {
		    	String line;
		    	
		    	
				while ((line = reader.readLine()) != null) {
				
				   line = line.replaceAll("\\D+","");
		   
				   list.add(line);
					   
				   }
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    int random=(int)(Math.random()*list.size());
			char[] ch=list.get(random).toCharArray();
			for(char n:ch) {
				numbers.add(Character.getNumericValue(n));
			}
			
			return numbers;
}
			
		
	
}

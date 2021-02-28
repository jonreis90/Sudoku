package mainP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SudokuFileIO {
	String fileName;
	BufferedWriter writer;
	BufferedReader reader;
	
	SudokuFileIO(String fileName) {
			try {
				writer=new BufferedWriter(new FileWriter(fileName,true));
				reader = new BufferedReader(new FileReader(fileName));
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	public void addNewSudoku(String string) {
		try {
			writer.write(string+" \n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
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

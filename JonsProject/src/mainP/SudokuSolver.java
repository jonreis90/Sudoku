package mainP;

/**
 * Solves a Sudoku using human techniques to determine difficulty and training purposes 
 * @author jonre
 *
 */
public class SudokuSolver implements Runnable{

	//checks sudoku for empty cells
	//cycles through techniques starting from easy to hard techniques 
	//trys to appy techniques to empty cells 
	//option for user to set solver to hint/display/auto solve/calculate sudoku 
	//add buttons to sudoku jpanel to change things
	
	Sudoku sudoku;
	
	
	void SudokuSolver() {
		
	}
	/**
	 * cycles through all techniques
	 */
	public void cycleStart() {
		
		
		/**
		 * techniques
		 * Last Number in box,row,column
		 * Scan directional 
		 * 
		 */
	}
	public void techniqueLastNumber() {
		for(NumberCell n:sudoku.getAllNumberCells()) {
			
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

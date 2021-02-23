package mainP;

public class SudokuAI {
	
	
	private Sudoku sudoku;
	
	public SudokuAI(Sudoku sudoku) {
		this.sudoku = sudoku;
		
	}
	public void checkRulesOfGame(NumberCell selectedCell) {
		
		//scanForPairInBox(selectedCell);
		
		
	}
	/**
	 * scans the box for a pair
	 * @param box - all the number
	 * @return - returns first pair found or null if non are found
	 */
	private void scanForPairInBox(NumberCell selectedCell) {
		if(checkIfDuplicatesExists(sudoku.getCellsInBox(selectedCell))!=null) {
			
		
		for(NumberCell n:checkIfDuplicatesExists(sudoku.getCellsInBox(selectedCell))){
			n.setMainNumberToWrong();
		}
		}
		
	}
	/**
	 * Checks if a duplicates exist in the number cells given
	 * @param numberCell - The number cell array to test
	 * @return - returns all the numbers with duplicates
	 */
	private NumberCell[]checkIfDuplicatesExists(NumberCell[] numberCell) {
		
		NumberCell[] duplicateCells= new NumberCell[1];
		
		NumberCell[] copy= numberCell;
		for(int i=0;i<numberCell.length;i++) {
			for(int j=0;j<numberCell.length;j++) {
				
				if(numberCell[i].getMainNumber()==copy[j].getMainNumber()&& i != j
						&&numberCell[i].getMainNumber()!=0) {
				
					duplicateCells=(NumberCell[]) Utility.addToArray(duplicateCells, numberCell[i]);
					 
					
				}
			}
		}
		
		return duplicateCells;
	}
	
	
}

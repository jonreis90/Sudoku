package mainP;

public class SudokuAI {
	
	
	/**
	 * Scans the row, column and box for a duplicate number
	 * @param row - number cell array that represent the row
	 * @param column - number cell array that represent the column
	 * @param box - number cell array that represent the box
	 * @return - returns the first pair scanned
	 */
	public NumberCellPair scanForPair(NumberCell[] row,NumberCell[] column, NumberCell[] box) {
		return null;
	}
	/**
	 * Checks if a pair exist in the number cells given
	 * @param numberCell - The number cell array to test
	 * @return - returns the first pair of number cells found
	 */
	public NumberCellPair checkIfPairExists(NumberCell[] numberCell) {
		
		NumberCell[] copy= numberCell;
		for(int i=0;i<numberCell.length;i++) {
			for(int j=0;j<numberCell.length;j++) {
				if(numberCell[i]==copy[j]&& i != j) {
					return new NumberCellPair(numberCell[i],copy[j]);
				}
			}
		}
		
		return null;
	}
	
}

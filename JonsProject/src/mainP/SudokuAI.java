package mainP;


import java.util.ArrayList;
import java.util.Arrays;


import mainP.NumberCell.Status;


public class SudokuAI implements Runnable{
	
	
	public Sudoku sudoku;
	
	private Thread t;
	private SudokuGenerator sudokuGenerator;
	
	
	public SudokuAI(Sudoku sudoku) {
		this.sudoku = sudoku;
		this.sudokuGenerator= new SudokuGenerator(this);
		t=new Thread(this);
		t.start();
		
		
		
	}
		
	public void checkRulesOfGame(NumberCell selectedCell) {
	
		
			if(isSelectedCellWrong(selectedCell)) {
				selectedCell.setMainNumberToWrong();
			}
			clearErrorFromOtherCells(selectedCell);		
		
		
	}
	public void generateBruteForceSudoku() {
		
		
		sudokuGenerator.run();
		
				
				
			
		
	}



	
	/**
	 * Gets the all the wrong cells connected to selected cell by box,row,column 
	 * and removes the error if applicable 
	 * @param selectedCell - The cell to scan other cells from 
	 */
	private void clearErrorFromOtherCells(NumberCell selectedCell) {
		ArrayList<NumberCell> listOfWrongCells= new ArrayList<NumberCell>();
		
		for(NumberCell boxc:sudoku.getCellsInBox(selectedCell)) {
			if(boxc.getCurrentStatus()==Status.WRONG_MAIN_NUMBER) {
				listOfWrongCells.add(boxc);
			}
		}
		for(NumberCell rowc:sudoku.getRow(selectedCell)) {
			if(rowc.getCurrentStatus()==Status.WRONG_MAIN_NUMBER) {
				listOfWrongCells.add(rowc);
			}
		}
		for(NumberCell columnc:sudoku.getColumn(selectedCell)) {
			if(columnc.getCurrentStatus()==Status.WRONG_MAIN_NUMBER) {
				listOfWrongCells.add(columnc);
			}
		}
		
		for(NumberCell list:listOfWrongCells) {
			if(isSelectedCellWrong(list)) {
				
			}
			else {
				list.setMainNumberToRight();
			}
		}
		
		
		
		
	}
	/**
	 * Scans the selected cells box,row and column for duplicates and returns boolean result
	 * @param selectedCell - The cell to scan
	 */
	public boolean isSelectedCellWrong(NumberCell selectedCell) {
		if(selectedCell.getMainNumber()!=0 && scanForDuplicateInBox(selectedCell).size()>1 ||
		   selectedCell.getMainNumber()!=0 && scanForDuplicateInRow(selectedCell).size()>1 ||
		   selectedCell.getMainNumber()!=0 && scanForDuplicateInColumn(selectedCell).size()>1) {
			
			return true;
			
		}
		return false;
	}

	private ArrayList<NumberCell> scanForDuplicateInBox(NumberCell selectedCell) {
	     ArrayList<NumberCell> boxCells= new ArrayList<NumberCell>(Arrays.asList(sudoku.getCellsInBox(selectedCell)));
		return checkForSameNumbers(boxCells, selectedCell);
		
	}

	private ArrayList<NumberCell> scanForDuplicateInRow(NumberCell selectedCell) {
	     ArrayList<NumberCell> boxCells= new ArrayList<NumberCell>(Arrays.asList(sudoku.getRow(selectedCell)));
		return checkForSameNumbers(boxCells, selectedCell);
		
	}

	private ArrayList<NumberCell> scanForDuplicateInColumn(NumberCell selectedCell) {
	     ArrayList<NumberCell> boxCells= new ArrayList<NumberCell>(Arrays.asList(sudoku.getColumn(selectedCell)));
		return checkForSameNumbers(boxCells, selectedCell);
		
	}
	
	/**
	 * Checks if the cell number exist in a list of number cells
	 * @param listOfCells - list of cells to check
	 * @param numberCell - the number to check with 
	 * @return - Returns a list of the resulting cells with the same number
	 */
	private ArrayList<NumberCell> checkForSameNumbers(ArrayList<NumberCell> listOfCells, NumberCell numberCell){
		ArrayList<NumberCell> result= new ArrayList<NumberCell>();
		
		for(NumberCell n:listOfCells) {
			if(n.getMainNumber()==numberCell.getMainNumber()){
				result.add(n);
			}
		}
		
		
		return result;
		
	}

	@Override
	public void run() {
		System.out.println("running");
		generateBruteForceSudoku();
		
	}


	
}


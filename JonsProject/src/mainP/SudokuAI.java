package mainP;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mainP.NumberCell.Status;


public class SudokuAI implements Runnable{
	
	
	private Sudoku sudoku;
	Thread t;
	
	public SudokuAI(Sudoku sudoku) {
		this.sudoku = sudoku;
		
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
		
		
		
		//loops through numbers
		//set number to random 
		//if error, cycle through all numbers 
		//if number equals original
		
		//start backtracking 
		//when backtracking -1 number, holds the number that was used in an array
		//loops through all the possible numbers left
		//if a number is found that lets the number continue past number 0 then backtracking stops
		//if a number is not found set backtracking -2 and continue process
		NumberCell[] allCells=sudoku.getAllNumberCells();
		
		boolean isBackTracking=false;
		int backTrackingNumber=0;
		
		
		for(int i=0;i<allCells.length;i++) {
			
			
			
			int rand=(int)(Math.random()*9+1);
			int original=rand;
			
			
			allCells[i].setMainNumber(rand);
			
			if(i>backTrackingNumber) {
				isBackTracking=false;
				
			}
			
			while(isSelectedCellWrong(allCells[i])) {
				
				try {
					t.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				rand++;
				
				
				
				if(rand>9) {
					rand=1;
				}
				
				allCells[i].setMainNumber(rand);
				
				if(rand==original) {
					//start backtracking 
					if(isBackTracking) {
						i=i-2;
						//add list of used numbers
					}
					else {
						backTrackingNumber=i;
						i=i-2;
						isBackTracking=true;
					}
					
					
					break;
				}
				
			}
			
		}
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
	private boolean isSelectedCellWrong(NumberCell selectedCell) {
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


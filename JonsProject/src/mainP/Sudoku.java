package mainP;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Arrays;


import mainP.NumberCell.Status;


//is a 9x9 grid of numbers with rules

public class Sudoku {
	
	
	
	//public properties
	public boolean miniNumberMode=false;
	public int boxSize = 50;
	public int gridStartLocationOffset = 100;

	//private properties
	private int gridSize = 9;
	private NumberCell[][] numberCell = new NumberCell[gridSize][gridSize];
	private NumberCell selectedCell;
	private enum Direction{UP,DOWN,LEFT,RIGHT};
	
	

	public Sudoku() {
		createGrid();
		
	}

	/**
	 * Creates a sudoku grid based on classes properties
	 */
	public void createGrid() {
		for (int y = 0; y < numberCell.length; y++) {
			for (int x = 0; x < numberCell[y].length; x++) {

				numberCell[x][y] = new NumberCell(new Rectangle(gridStartLocationOffset + x * boxSize,
						gridStartLocationOffset + y * boxSize, boxSize, boxSize),new Point(x,y));

			}
		}
		//test area
		for(NumberCell n: getCellsInBox(getBoxPosition(getNumberCell(4,7)))) {
			n.setMainNumber(2);
			
		}
		
		
		
		
		
	
	}
	/**
	 * Gets the box position the number cell is contained in 
	 * @param numberCell - the number cell to get the box position 
	 * @return -  returns x,y point of the box position
	 */
	public Point getBoxPosition(NumberCell numberCell) {
		
		int xBox=Utility.roundUp(numberCell.getPosition().x+1, gridSize/3);
		int yBox=Utility.roundUp(numberCell.getPosition().y+1, gridSize/3);
		return new Point(xBox,yBox);
	}
	/**
	 * Gets the cells in a sudoku box given the box position
	 * @param boxPosition - The x,y box position in a point
	 * @return - Returns number cell array for all cells contained in box
	 */
	public NumberCell[] getCellsInBox(Point boxPosition) {
		
		NumberCell[] cells=new NumberCell[gridSize];
		
		int xBox=boxPosition.x*3;
		int yBox=boxPosition.y*3;
		
		int[] xRange={xBox-3,xBox-2,xBox-1};
		int[] yRange= {yBox-3,yBox-2,yBox-1};
		
		int index=0;
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
		
				cells[index] = this.numberCell[xRange[i]][yRange[j]];
				index++;
				
			}
		}
		
		
		return cells;
	}
	/**
     * Gets an array of number cells in a given column
	 * @param numberCell - the number cell to choose the row 
	 * @return -  an array of number cells in the column
	 */
	public NumberCell[] getColumn(NumberCell numberCell) {
		
		NumberCell [] column = new NumberCell[gridSize];
		
		for(int i=0;i<gridSize;i++) {
			
			column[i]= this.numberCell[numberCell.getPosition().x][i];
			
		}

		return column; 
	}
	/**
     * Gets an array of number cells in a given row 
	 * @param numberCell - the number cell to choose the row 
	 * @return -  an array of number cells in a row
	 */
	public NumberCell[] getRow(NumberCell numberCell) {
		
		NumberCell [] row = new NumberCell[gridSize];
		
		for(int i=0;i<gridSize;i++) {
			
			row[i]= this.numberCell[i][numberCell.getPosition().y];
			
		}

		return row; 
	}
	
	/**
	 * Used to get a number box based on parameters 
	 * @param x -  x position of grid
	 * @param y - y position of grid
	 * @return - returns the number box
	 * @exception - gives array out of bound if x or y is <1 or >then grid size
	 */
	public NumberCell getNumberCell(int x,int y) {
		return numberCell[x-1][y-1];
	}
	/**
	 * Gets all number boxes in an array
	 * @return -  returns a number box array containing all number box in properties 
	 */
	private NumberCell[] getAllNumberCells() {
		
		NumberCell[] index= new NumberCell[0];
		
		for(NumberCell[] n:numberCell) {
			for(NumberCell n2:n) {
				
				NumberCell[] numberBoxArray= new NumberCell[index.length+1];
				numberBoxArray = Arrays.copyOf(index, index.length +1);
				numberBoxArray[index.length] = n2;
				index = numberBoxArray;
			}
		}
		return index;
	}
	
	
	
	/**
	 * Used to select boxes with mouse input
	 * @param x - x position of mouse coordinate 
	 * @param y - y position of mouse coordinate
	 */
	public void mousePressed(int x, int y) {
		
		for(NumberCell n : getAllNumberCells()) {
			if(n.getBox().contains(x, y)) {
				setSelectedCell(n);
			}
		}
		
		
	}
	/**
	 * Selects the number box passed in, the selected box can then be manipulated with key presses to play Sodoku
	 * @param numberCell - The number box to be selected
	 */
	private void setSelectedCell(NumberCell numberCell) {
		
		if(this.selectedCell  == null) {
			this.selectedCell = numberCell;
			this.selectedCell.setSelected(true);
		}
		else {
			this.selectedCell.setSelected(false);
			this.selectedCell = numberCell ;
			this.selectedCell.setSelected(true);
		}
		
		setHighlightedBoxes();
	
	}
	/**
	 * Sets the boxes to be highlighted based on the selected box
	 * @throws - Null pointer if no box is selected
	 */
	private void setHighlightedBoxes() {
		// TODO add the highlighting of boxes
		
	}
/**
 * TODO
 * @param direction
 */
	public void moveSelectedCell(Direction direction) {
		switch(direction) {
		case DOWN:
			if(this.selectedCell.getPosition().y!=gridSize-1) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x][this.selectedCell.getPosition().y+1]);
			}
			break;
		case LEFT:
			if(this.selectedCell.getPosition().x!=0) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x-1][this.selectedCell.getPosition().y]);
			}
			break;
		case RIGHT:
			if(this.selectedCell.getPosition().x!=gridSize-1) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x+1][this.selectedCell.getPosition().y]);
			}
			break;
		case UP:
			if(this.selectedCell.getPosition().y!=0) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x][this.selectedCell.getPosition().y-1]);
			}
			break;
		
			
		}
			
	}
	
	/**
	 * TODO finish this
	 * Handles all the keyboard activity for the sudoku
	 * @param e - key event for this sudoku
	 */
	public void keyPressed(KeyEvent e) {
		char i = e.getKeyChar();
		int k = e.getKeyCode();

		if (selectedCell == null) {
			return;
		}

	
		if (i >= '1' && i <= '9') {
				int number = Character.getNumericValue(i);
			
			 	if(selectedCell.getCurrentStatus()== Status.MINI_NUMBERS) {	
			 			selectedCell.setMiniNumber(number, !selectedCell.isMiniNumberSet(number));

			 	}else if(selectedCell.getCurrentStatus()== Status.BLANK||selectedCell.getCurrentStatus()==Status.MAIN_NUMBER){
			
			 		selectedCell.setMainNumber(number);
			 	}			
		}
	
		

		switch (k) {
		case KeyEvent.VK_DOWN:
			moveSelectedCell(Direction.DOWN);
			
			break;
		case KeyEvent.VK_UP:
			moveSelectedCell(Direction.UP);
			break;
		case KeyEvent.VK_LEFT:
			moveSelectedCell(Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			moveSelectedCell(Direction.RIGHT);
			break;
			
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
		case KeyEvent.VK_ESCAPE:
			
			
			break;
		case KeyEvent.VK_SHIFT:
			
			
		}
		

	}

	/**
	 * Draws the sodoku grid 
	 * @param g2 - graphics to draw on the display
	 */
	public void drawGrid(Graphics2D g2) {
		
		for(NumberCell n:getAllNumberCells()) {
			n.drawGraphics(g2);
		}
		if(this.selectedCell !=null) {
			this.selectedCell.drawGraphics(g2);
		}
				
				//TODO draw the outside boarders and the 3x3 lines
		 //draw a thicker grid around all boxes
		//draw a thicker grid for each 3x3 lines
		
	}

	

}

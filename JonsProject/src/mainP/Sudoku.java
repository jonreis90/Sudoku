package mainP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


import mainP.NumberCell.Status;


//is a 9x9 grid of numbers with rules

public class Sudoku {
	
	
	
	//public properties
	public boolean miniNumberMode=false;
	public static final int CELL_SIZE = 50;
	public static final int GRID_START_LOCATION_OFFSET = 100;
	/**
	 * The grid size of the sudoku
	 * @implNote Default value =9 , Code currently does not support different grid sizes
	 */
	public static final int GRID_SIZE = 9;

	//private properties
	private NumberCell[][] numberCell = new NumberCell[GRID_SIZE][GRID_SIZE];
	private NumberCell selectedCell;
	private enum Direction{UP,DOWN,LEFT,RIGHT};
	private static final int BOX_SIZE=CELL_SIZE*(GRID_SIZE/3);
	
	private final SudokuAI sudokuAI;
	
	
	public Sudoku(){
		createGrid();
		//must create grid first or null exceptions will happen due to multiple treads 
		sudokuAI=new SudokuAI(this);
		
		
	}

	/**
	 * Creates a sudoku grid based on classes properties
	 */
	public void createGrid() {
		for (int y = 0; y < numberCell.length; y++) {
			for (int x = 0; x < numberCell[y].length; x++) {

				numberCell[x][y] = new NumberCell(new Rectangle(GRID_START_LOCATION_OFFSET + x * CELL_SIZE,
						GRID_START_LOCATION_OFFSET + y * CELL_SIZE, CELL_SIZE, CELL_SIZE),new Point(x,y));
			}
		}
		
	}
	
	/**
	 * toggles the mini number mode property
	 */
	public void toggleMiniNumberMode() {
		this.miniNumberMode=!this.miniNumberMode;
	}
	/**
	 * Gets the box position the number cell is contained in 
	 * @param numberCell - the number cell to get the box position 
	 * @return -  returns x,y point of the box position
	 */
	public Point getBoxPosition(NumberCell numberCell) {
		
		int xBox=Utility.roundUp(numberCell.getPosition().x+1, GRID_SIZE/3);
		int yBox=Utility.roundUp(numberCell.getPosition().y+1, GRID_SIZE/3);
		return new Point(xBox,yBox);
	}
	/**
	 * Gets the cells in a sudoku box given the cell position
	 * @param numberCell - The number cell to get the box containing all cells
	 * @return - Returns number cell array for all cells contained in box
	 */
	public NumberCell[] getCellsInBox(NumberCell numberCell) {
		return getCellsInBox(getBoxPosition(numberCell));
	}
	/**
	 * Gets the cells in a sudoku box given the box position
	 * @param boxPosition - The x,y box position in a point
	 * @return - Returns number cell array for all cells contained in box
	 */
	public NumberCell[] getCellsInBox(Point boxPosition) {
		
		NumberCell[] cells=new NumberCell[GRID_SIZE];
		
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
		
		NumberCell [] column = new NumberCell[GRID_SIZE];
		
		for(int i=0;i<GRID_SIZE;i++) {
			
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
		
		NumberCell [] row = new NumberCell[GRID_SIZE];
		
		for(int i=0;i<GRID_SIZE;i++) {
			
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
	 * Gets all number cells in an array
	 * @return -  returns a number cell array containing all number cells in properties 
	 */
	public NumberCell[] getAllNumberCells() {
		
		NumberCell[] numberCells= new NumberCell[GRID_SIZE*GRID_SIZE];
		
		int index=0;
		for(int i=0;i<GRID_SIZE;i++) {
			for(int j=0;j<GRID_SIZE;j++) {
				
				numberCells[index]=numberCell[j][i];
				index++;
			}
		}
		return numberCells;
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
	 */
	private void setHighlightedBoxes() {
		for(NumberCell n:getAllNumberCells()) {
			n.setHighlighted(false);
		}
		
		for(NumberCell n:getRow(this.selectedCell)) {
			n.setHighlighted(true);
		}
		for(NumberCell n:getColumn(this.selectedCell)) {
			n.setHighlighted(true);
		}
		for(NumberCell n:getCellsInBox(this.selectedCell)) {
			n.setHighlighted(true);
		}
		
	}
/**
 * Moves the selected cell in a direction
 * @param direction - A direction up,down,left,right
 */
	public void moveSelectedCell(Direction direction) {
		switch(direction) {
		case DOWN:
			if(this.selectedCell.getPosition().y!=GRID_SIZE-1) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x][this.selectedCell.getPosition().y+1]);
			}
			break;
		case LEFT:
			if(this.selectedCell.getPosition().x!=0) {
				setSelectedCell(this.numberCell[this.selectedCell.getPosition().x-1][this.selectedCell.getPosition().y]);
			}
			break;
		case RIGHT:
			if(this.selectedCell.getPosition().x!=GRID_SIZE-1) {
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
	 * Sets the main number of the selected cell 
	 * If 0 clears the number
	 * @param number - 0-9 integer numbers 
	 */
	public void setMainNumber(int number) {
		if(number<0||number>9) {
			throw new IllegalArgumentException("setMainNumber parameter must be 0-9");
			
		}
		if(number==0) {
			selectedCell.clearMainNumber();
		}
		else {
			selectedCell.setMainNumber(number);
		}
		
		this.sudokuAI.checkRulesOfGame(selectedCell);
		
	}
	/**
	 * 
	 * Handles all the keyboard activity for the sudoku
	 * @param e - key event for this sudoku
	 */
	
	public void keyPressed(KeyEvent e) {
		char i = e.getKeyChar();
		int k = e.getKeyCode();

		if (selectedCell == null) {
			return;
		}

		//when number keys are pressed
		if (i >= '1' && i <= '9') {
				int number = Character.getNumericValue(i);
				
			 	if(selectedCell.getCurrentStatus()!= Status.LOCKED) {	
			 		if(this.miniNumberMode&&selectedCell.getCurrentStatus()!=Status.MAIN_NUMBER) {
			 			
			 			selectedCell.setMiniNumber(number, !selectedCell.isMiniNumberSet(number));
			 			selectedCell.displayMiniNumbers();
			 		}
			 		else if(this.miniNumberMode&&selectedCell.getCurrentStatus()==Status.MAIN_NUMBER) {
			 			
			 		}
			 		else{
			 			setMainNumber(number);
			 		}
						
			 	}
		}
	
		
		//when arrow keys are pressed, moves selection
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
			setMainNumber(0);
			
			break;
		case KeyEvent.VK_SHIFT:
				toggleMiniNumberMode();
				
			break;
			
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
		
		drawBoxes(g2);
		
		if(this.selectedCell !=null) {
			this.selectedCell.drawGraphics(g2);
		}
		
		
		/**
		 * TODO this here for testing, needs to be cleaned up eventually
		 */
		if(this.selectedCell!=null){
			g2.setColor(Color.BLACK);
			g2.setFont(new Font(null, Font.PLAIN, 20));
			g2.drawString(this.selectedCell.getCurrentStatus().name(), 50, 50);
			g2.drawString(String.valueOf(miniNumberMode),50,20);
		}
		
	}
	/**
	 * draws the boxes in the sudoku
	 * @param g2 - graphics to draw to 
	 */
	public void drawBoxes(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		
		g2.draw(new Rectangle(GRID_START_LOCATION_OFFSET,GRID_START_LOCATION_OFFSET,GRID_SIZE*CELL_SIZE,GRID_SIZE*CELL_SIZE));
		
		for(int i=0;i<GRID_SIZE/3;i++) {
			for(int j=0;j<GRID_SIZE/3;j++) {
				g2.draw(new Rectangle(GRID_START_LOCATION_OFFSET+i*(BOX_SIZE),GRID_START_LOCATION_OFFSET+j*(BOX_SIZE),
						BOX_SIZE,BOX_SIZE));
			}
		}
		
	}
	

	

}

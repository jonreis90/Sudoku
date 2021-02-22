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
	private NumberCell selectedBox;

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
		for(NumberCell n: getCellsInBox(new Point(1,3))) {
			n.setMainNumber(2);
			
		}
		
		
		
		
		
	
	}
	public void getBoxPosition(NumberCell numberCell) {
		
	}
	public NumberCell[] getCellsInBox(Point boxPosition) {
		
		NumberCell[] cells=new NumberCell[gridSize];
		
		int xBox=boxPosition.x*3;
		int yBox=boxPosition.y*3;
		
		int[] xRange={xBox-2,xBox-1,xBox};
		int[] yRange= {yBox-2,yBox-1,yBox};
		
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
	public NumberCell getNumberBox(int x,int y) {
		return numberCell[x-1][y-1];
	}
	/**
	 * Gets all number boxes in an array
	 * @return -  returns a number box array containing all number box in properties 
	 */
	private NumberCell[] getAllNumberBoxes() {
		
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
	/**TODO Delete this after using it for ref
	
	//returns the int position x of the given rectangle
	public int getIndexX(NumberRectangle x) {
		
		int numb=x.getRect().x;
		numb= (numb-startOffset)/size;
		return numb;
		
	}
	//returns the int position y of the given rectangle
	public int getIndexY(NumberRectangle y) {
		
		int numb=y.getRect().y;
		numb= (numb-startOffset)/size;
		return numb;
		
	}
	//returns boolean if parameters row, col contains a rectangle
	private boolean isNumberRecInBounds(int row,int col) {
		
		for (int y = 0; y < nRec.length; y++) {
			for (int x = 0; x < nRec[y].length; x++) {
				
				if(x==row && y==col) {
					return true;
				}
			}	
		}
		return false;
	}
	**/
	
	
	/**
	 * Used to select boxes with mouse input
	 * @param x - x position of mouse coordinate 
	 * @param y - y position of mouse coordinate
	 */
	public void mousePressed(int x, int y) {
		
		for(NumberCell n : getAllNumberBoxes()) {
			if(n.getBox().contains(x, y)) {
				setSelectedBox(n);
			}
		}
		
		
	}
	/**
	 * Selects the number box passed in, the selected box can then be manipulated with key presses to play Sodoku
	 * @param numberCell - The number box to be selected
	 */
	private void setSelectedBox(NumberCell numberCell) {
		
		if(this.selectedBox  == null) {
			this.selectedBox = numberCell;
			this.selectedBox.setSelected(true);
		}
		else {
			this.selectedBox.setSelected(false);
			this.selectedBox = numberCell ;
			this.selectedBox.setSelected(true);
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
	 * Handles all the keyboard activity for the sudoku
	 * @param e - key event for this sudoku
	 */
	public void keyPressed(KeyEvent e) {
		char i = e.getKeyChar();
		int k = e.getKeyCode();

		if (selectedBox == null) {
			return;
		}

	
		if (i >= '1' && i <= '9') {
				int number = Character.getNumericValue(i);
			
			 	if(selectedBox.getCurrentStatus()== Status.MINI_NUMBERS) {	
			 			selectedBox.setMiniNumber(number, !selectedBox.isMiniNumberSet(number));

			 	}else if(selectedBox.getCurrentStatus()== Status.BLANK||selectedBox.getCurrentStatus()==Status.MAIN_NUMBER){
			
			 		selectedBox.setMainNumber(number);
			 	}			
		}
	}
		
/**
 * TODO
 * implement the keyboard presses
 * need to get a row/col and check if movement is contained 
		switch (k) {
		case KeyEvent.VK_DOWN:

			if (isNumberRecInBounds(getIndexX(selectedRec), getIndexY(selectedRec) + 1)) {
				setSelectedRectangle(nRec[getIndexX(selectedRec)][getIndexY(selectedRec) + 1]);
			}
			break;
		case KeyEvent.VK_UP:
			if (isNumberRecInBounds(getIndexX(selectedRec), getIndexY(selectedRec) - 1)) {
				setSelectedRectangle(nRec[getIndexX(selectedRec)][getIndexY(selectedRec) - 1]);
			}

			break;
		case KeyEvent.VK_LEFT:
			if (isNumberRecInBounds(getIndexX(selectedRec) - 1, getIndexY(selectedRec))) {
				setSelectedRectangle( nRec[getIndexX(selectedRec) - 1][getIndexY(selectedRec)]);
			}

			break;
		case KeyEvent.VK_RIGHT:

			if (isNumberRecInBounds(getIndexX(selectedRec) + 1, getIndexY(selectedRec))) {
				setSelectedRectangle(nRec[getIndexX(selectedRec) + 1][getIndexY(selectedRec)]);
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
		case KeyEvent.VK_ESCAPE:
			selectedRec.clearRect();
			break;
		case KeyEvent.VK_SHIFT:
			miniNumberMode=!miniNumberMode;
		}
		

	}
	**/

	
	/** TODO delete this after using it for reference
	private void setSelectedRectangle(NumberRectangle selected) {

		// this sets the selected rectangle back to default colors when another is selected
		if (!(selectedRec == null)) {
			selectedRec.setColor(Color.BLACK);
			for (NumberRectangle nr : getSelectedRow()) {
				nr.setBgColor(Color.WHITE);
			}
			for (NumberRectangle nr : getSelectedCol()) {
				nr.setBgColor(Color.WHITE);
			}
			for(NumberRectangle nr:getSelectedBox()) {
				nr.setBgColor(Color.WHITE);
			}

		}

		//changes selected colors
		selectedRec = selected;
		selectedRec.setColor(Color.BLUE);
		
		for(NumberRectangle nr:getSelectedBox()) {
			nr.setBgColor(new Color(235,235,235));
		}

		for (NumberRectangle nr : getSelectedRow()) {
			nr.setBgColor(new Color(220, 220, 220));
		}
		for (NumberRectangle nr : getSelectedCol()) {
			nr.setBgColor(new Color(220, 220, 220));
		}

		
		
		
	}



	// TODO 
	 * need to get a row of boxes 
	 * need to get a col of boxes 
	 * need to get the 3x3 areas of boxes
	 * 
	 * 
//returns the row of selected rectangle
private NumberRectangle[] getSelectedRow() {
	NumberRectangle[] nRow= new NumberRectangle[nRec[selectedRec.getCol()].length];
	
	for(int x=0;x < nRec[selectedRec.getCol()].length;x++) {
		nRow[x]= nRec[x][selectedRec.getCol()];
	}
	return nRow;
}
//returns the col of selected rectangle
private NumberRectangle[] getSelectedCol() {

	NumberRectangle[] nCol= new NumberRectangle[nRec.length];
	
	for(int x=0;x < nRec.length;x++) {
		nCol[x]= nRec[selectedRec.getRow()][x];
	}
	return nCol;
}
//returns the box values of the selected rectangle
private NumberRectangle[] getSelectedBox() {
	NumberRectangle[] nBox= new NumberRectangle[9];
	
	int boxRow=(selectedRec.getRow()/3)*3;
	int boxCol=(selectedRec.getCol()/3)*3;
	int i=0;
	for(int x=boxRow;x<boxRow+3;x++) {
		for(int y=boxCol;y<boxCol+3;y++) {
			nBox[i]=nRec[x][y];
			i++;
		}
	}
	return nBox;
}
	**/

	/**
	 * Draws the sodoku grid 
	 * @param g2 - graphics to draw on the display
	 */
	public void drawGrid(Graphics2D g2) {
		
		for(NumberCell n:getAllNumberBoxes()) {
			n.drawGraphics(g2);
		}
		if(this.selectedBox !=null) {
			this.selectedBox.drawGraphics(g2);
		}
				
				//TODO draw the outside boarders and the 3x3 lines
		 //draw a thicker grid around all boxes
		//draw a thicker grid for each 3x3 lines
		
	}

	

}

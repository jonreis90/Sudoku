package mainP;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Arrays;


import mainP.NumberBox.Status;





//is a 9x9 grid of numbers with rules

public class Sudoku {
	
	
	
	//public properties
	public boolean miniNumberMode=false;
	public int boxSize = 50;
	public int gridStartLocationOffset = 100;

	//private properties
	private int gridSize = 9;
	private NumberBox[][] numberBox = new NumberBox[gridSize][gridSize];
	private NumberBox selectedBox;

	public Sudoku() {
		createGrid();
		
	}

	public void createGrid() {
		for (int y = 0; y < numberBox.length; y++) {
			for (int x = 0; x < numberBox[y].length; x++) {

				numberBox[x][y] = new NumberBox(new Rectangle(gridStartLocationOffset + x * boxSize,
						gridStartLocationOffset + y * boxSize, boxSize, boxSize));

			}
		}
		for(NumberBox n: getAllNumberBoxes()) {
			n.setMainNumber(2);
			n.clearMainNumber();
			n.setMiniNumber(2, true);
			n.displayMiniNumbers();
			//n.setMainNumber(2);
			//n.lockMainNumber();
		}
		//testing
		//getRow(getNumberBox(4,9));
		NumberBox[] nb1=new NumberBox[1];
		nb1[0]= new NumberBox(new Rectangle(100,100,100,100));
		NumberBox nb2 = new NumberBox(new Rectangle(200,200,200,200));
		
		nb1=(NumberBox[])Utility.addToArray(nb1, nb2);
		
		
		
		

	}
	/**
	 * TODO 
	 * check make sure this is working
	 *  finish utility class for adding to array 
	 * 
	 * Returns an entire row of number boxes from the grid 
	 * @param numberBox - the number box to choose the row 
	 * @return -  an array of number boxes in the row
	 */
	public NumberBox[] getRow(NumberBox numberBox) {
		NumberBox [] row = new NumberBox[this.gridSize];
		
		for(NumberBox[] n:this.numberBox) {
			
			int index=Arrays.asList(n).indexOf(numberBox);
			System.out.println(index);
			
			if(index!=-1) {
				for(NumberBox n2:this.numberBox[index]) {
					NumberBox[] numberBoxArray= new NumberBox[row.length+1];
					numberBoxArray = Arrays.copyOf(row, row.length +1);
					numberBoxArray[row.length] = n2;
					row = numberBoxArray;
				}
			}
		}
		
	
		
		for(int i=0;i<gridSize;i++) {
			
		}
		
		
		return null; 
	}
	
	/**
	 * Used to get a number box based on parameters 
	 * @param x -  x position of grid
	 * @param y - y position of grid
	 * @return - returns the number box
	 * @exception - gives array out of bound if x or y is <1 or >then grid size
	 */
	public NumberBox getNumberBox(int x,int y) {
		return numberBox[x-1][y-1];
	}
	/**
	 * Gets all number boxes in an array
	 * @return -  returns a number box array containing all number box in properties 
	 */
	private NumberBox[] getAllNumberBoxes() {
		
		NumberBox[] index= new NumberBox[0];
		
		for(NumberBox[] n:numberBox) {
			for(NumberBox n2:n) {
				
				NumberBox[] numberBoxArray= new NumberBox[index.length+1];
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
		
		for(NumberBox n : getAllNumberBoxes()) {
			if(n.getBox().contains(x, y)) {
				setSelectedBox(n);
			}
		}
		
		
	}
	/**
	 * Selects the number box passed in, the selected box can then be manipulated with key presses to play Sodoku
	 * @param numberBox - The number box to be selected
	 */
	private void setSelectedBox(NumberBox numberBox) {
		
		if(this.selectedBox  == null) {
			this.selectedBox = numberBox;
			this.selectedBox.setSelected(true);
		}
		else {
			this.selectedBox.setSelected(false);
			this.selectedBox = numberBox ;
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
		
		for(NumberBox n:getAllNumberBoxes()) {
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

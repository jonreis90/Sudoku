package mainP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;




//A box that holds numbers and position to be used in sudoku
public class NumberCell {

	//Appearance properties
	public Color defaultColor=Color.BLACK;
	public Color bgColor=Color.WHITE;
	public Color selectedColor=Color.BLUE;
	public Color highlightedColor=Color.LIGHT_GRAY;
	public BasicStroke boxLines= new BasicStroke(1.0f);
	private BasicStroke boxLinesThick= new BasicStroke(2.0f);
	
	//public enumerator
	public enum Status{ BLANK,LOCKED,MINI_NUMBERS,MAIN_NUMBER,WRONG_MAIN_NUMBER};
	
	
	//internal properties
	private Rectangle box;
	private int possibleNumberOptions=9;
	private boolean[] miniNumbersDisplayed= new boolean[possibleNumberOptions];
	private int mainNumberValue;
	private Status currentStatus=Status.BLANK;
	private boolean selected,highlighted;
	private Point position;
	

	
	
	/**
	 * Creates a box that can display number for use with a sudoku
	 * @param box - the box to be created
	 * @exception -  width and height must be the same or Illegal argument exception occurs 
	 * @param position - the x,y point position in the sudoku grid 
	 */
	public NumberCell(Rectangle box, Point position) {
		if(box.height != box.width) { 
			throw new IllegalArgumentException("Rectangle's width and height must be the same") ;
		}
		else {
		this.box = box;
		this.position=position;
		}
	}
	/**
	 * Sets the main number to display in red for errors in sudoku rules
	 */
	public void setMainNumberToWrong() {
		currentStatus = Status.WRONG_MAIN_NUMBER;
	}
	/**
	 * Removes main number error 
	 */
	public void setMainNumberToRight() {
		if(currentStatus == Status.WRONG_MAIN_NUMBER) {
			currentStatus= Status.MAIN_NUMBER;
		}
	}
	
	/**
	 * Gets the x,y position of the cell in the sudoku grid
	 * @return - x,y point position
	 */
	public Point getPosition() {
		return this.position;
	}
	/**
	 * Inserts the number into the box
	 * @param number - an integer within the range of possible values
	 */
	public void setMainNumber(int number) {
		if(number<0||number>possibleNumberOptions) {
			throw new IllegalArgumentException("Number is not in the range of possible number options") ;
		}
		else if(number==0) {
			this.mainNumberValue = 0;
			currentStatus = Status.BLANK;
		}
		else {
			this.mainNumberValue = number;
			currentStatus = Status.MAIN_NUMBER;
		}
			
	}
	/**
	 * Gets the status of this box 
	 * @return - The Status enumerator for the number box 
	 */
	public Status getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * Returns the main number in the box
	 * @return - returns the main number in the box
	 */
	public int getMainNumber() {
		return this.mainNumberValue;
	}
	/**
	 * Gets the box in a rectangle shape 
	 * @return - returns the box rectangle shape 
	 */
	public Rectangle getBox() {
		return box;
	}
	/**
	 * Displays the mini numbers that are currently set, can only be performed when box is blank  
	 */
	public void displayMiniNumbers() {
		if(this.currentStatus == Status.BLANK) {
			this.currentStatus = Status.MINI_NUMBERS;
		}
		else {
			
		}
		
	}
	/**
	 * Clears the main number out of the box,
	 * Displays the mini numbers if any are set or blank if not
	 */
	public void clearMainNumber() {
		if(this.currentStatus == Status.MAIN_NUMBER||this.currentStatus == Status.WRONG_MAIN_NUMBER) {
				mainNumberValue = 0;
				if(isAnyMiniNumbersSet()) {
					this.currentStatus = Status.MINI_NUMBERS;
				}
				else {
					this.currentStatus = Status.BLANK;
				}
		}
	}
	/**
	 * Locks the box from being changed
	 * @exception - thows IllegalAccessError if main number is blank
	 */
	public void lockMainNumber() {
		if(mainNumberValue != 0) {
			this.currentStatus = Status.LOCKED;
		}
		else {
			throw new IllegalAccessError("The lockMainNumber method can't be accessed if main number is blank");
		}
	}
	/**
	 *  unlocks main number if status is set to locked 
	 */
	public void unLockMainNumber() {
		if(currentStatus == Status.LOCKED) {
			this.currentStatus = Status.MAIN_NUMBER;
		}
	}
	/**
	 * Sets is the box selected, if true always overrides highlighted to false 
	 * @param bool -  boolean for setting is box selected
	 */
	public void setSelected(boolean bool) {
		if(isSelected()) {
			setHighlighted(false);
		}
		this.selected = bool;
		
	}
	/**
	 * Used to return the boolean of the box being selected
	 * @return - returns boolean if box is selected 
	 */
	public boolean isSelected() {
		return this.selected;
	}
	/**
	 * Sets the box highlighted based on boolean 
	 * @param bool - set the boolean of the box being highlighted
	 */
	public void setHighlighted(boolean bool) {
		this.highlighted =bool;
		
	}
	/**
	 * Used to get if box is highlighted
	 * @return - return boolean of box highlighted result
	 */
	public boolean isHighlighted() {
		return this.highlighted;
	}
	
	
	/**
	 * Sets mini numbers to be displayed, use method display mini numbers to display them 
	 * @param number -  the number you want toggled
	 * @param value - the boolean value to toggle too 
	 */
	public void setMiniNumber(int number,boolean value){
		if(number<1||number>possibleNumberOptions) {
			throw new IllegalArgumentException("Number is not in the range of possible number options") ;
		}
		else {
			this.miniNumbersDisplayed[number-1]= value;
		}
	}
	/**
	 * Checks if any mini numbers are currently set to true
	 * @return -  Returns boolean result of check
	 */
	public boolean isAnyMiniNumbersSet(){
		
		boolean isAnyNumbersDisplayed=false;
		for(boolean b: miniNumbersDisplayed) {
			if(b) {
				isAnyNumbersDisplayed=true;
			}
		}
		return isAnyNumbersDisplayed;
		
	}
	/**
	 * Gets the boolean result for the mini number displayed field 
	 * @param number -  the mini number boolean you want to receive
	 * @exception - throws illegal argument if number is not >0 and less then possible number options
	 */
	public boolean isMiniNumberSet(int number) {
		if(number<1||number>possibleNumberOptions) {
			throw new IllegalArgumentException("Number is not in the range of possible number options") ;
		}
		else {
			return miniNumbersDisplayed[number-1];
		}
	}
	/**
	 * Draws the box for the number box 
	 * @param g2 - the graphics to draw onto
	 */
	private void drawBox(Graphics2D g2) {
		if(isSelected()) {
			g2.setColor(bgColor);
			g2.fill(box);
			g2.setColor(this.selectedColor);
			g2.setStroke(this.boxLinesThick);
			g2.draw(box);
		}
		else if(isHighlighted()) {
			g2.setColor(this.highlightedColor);
			g2.fill(box);
			g2.setColor(Color.BLACK);
			g2.setStroke(boxLines);
			g2.draw(box);
		}
		else {
			g2.setColor(bgColor);
			g2.fill(box);
			g2.setColor(Color.BLACK);
			g2.setStroke(boxLines);
			g2.draw(box);
		}
		
	}
	
	/**
	 * Draws the number box on the given display
	 * @param g -  graphics needed to draw on display
	 */
	public void drawGraphics(Graphics2D g2) {

		drawBox(g2);
		
		switch(currentStatus) {
		case BLANK:
			
			break;
		case LOCKED:
			    
				Utility.drawCenteredString(g2, mainNumberValue+"", box, new Font(null, Font.BOLD, box.height));
			break;
		case MAIN_NUMBER:
			
					Utility.drawCenteredString(g2, mainNumberValue+"", box, new Font(null, Font.PLAIN, box.height));
				
			break;
		case MINI_NUMBERS:
				drawMiniNumbers(g2);
			break;
		case WRONG_MAIN_NUMBER:
			    g2.setColor(Color.RED);
				Utility.drawCenteredString(g2, mainNumberValue+"", box, new Font(null, Font.PLAIN, box.height));
			break;	
		
		}
			
	
	}
	/**
	 * Used to draw the mini numbers in the number box
	 * @param g2 - the graphics the needed to draw on display
	 */
	private void drawMiniNumbers(Graphics2D g2) {
		int rowlength=(int)Math.sqrt(possibleNumberOptions);
		int boxSize= box.width/3;
		
			for(int i=0;i<possibleNumberOptions;i++) {
				
				
				Rectangle miniRectangle=new Rectangle(box.x+(i%rowlength*boxSize),box.y+(i/rowlength*boxSize),boxSize,boxSize);
				if(miniNumbersDisplayed[i]) {
					Utility.drawCenteredString(g2, i+1+"", miniRectangle, new Font(null, Font.PLAIN, boxSize));
				}
				
			}
	}
	
	

}
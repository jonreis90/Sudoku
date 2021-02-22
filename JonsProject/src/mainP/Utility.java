package mainP;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;

public final class Utility {
	
	
	private Utility() {
		
	}
	/**
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g    The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java
		// 2d 0 is top of the screen)
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
	}
	
	/**
	 * Divides number by divisor and rounds up
	 * Example 5/3 = 2
	 * @param number - the number to divide
	 * @param divisor - the divider
	 * @return an integer of the rounded up number
	 */
	public static int roundUp(double number, double divisor) {
		
		return (int) Math.ceil(number/divisor);
	}
	
	/**
	 * Adds an object to the end of an array of objects of the same class
	 * 
	 * @param arrayObject - an array of an object
	 * @param objectAdding - the item to add to that array 
	 * @exception - Throws illegal argument exception if objects are not the same class 
	 */
	public static Object[] addToArray(Object[] arrayObject,Object objectAdding) {
			
		if(arrayObject[0].getClass()!=objectAdding.getClass()) {
			
			throw new IllegalArgumentException("Array object and objecting adding have to be the same class");
			
		}
		
			Object[] object = new Object[arrayObject.length];
			object = Arrays.copyOf(arrayObject, arrayObject.length+1);
			object[object.length-1] = objectAdding;
			
			return object;
	
			}
}

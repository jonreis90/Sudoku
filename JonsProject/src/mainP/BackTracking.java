package mainP;

public interface BackTracking {

	/**
	 * Sets the choice of solutions available for backtracking 
	 * @param problemIndex - the current index of the problems
	 * @param choice - the int options 
	 */
	void setChoice(int problemIndex, int choice);
	/**
	 * Sets the contraints to check for the choices
	 * @param problemIndex - the current index of the problem
	 * @return - the resulting boolean if constraints are met 
	 */
	boolean setConstraints(int problemIndex);
	/**
	 * Discards the choice that failed to pass constraints
	 * @param problemIndex - the current index of the problem 
	 */
	void discardChoice(int problemIndex);
	/**
	 * Will call method done when the generator solves the problem
	 */
	void done();
	
}

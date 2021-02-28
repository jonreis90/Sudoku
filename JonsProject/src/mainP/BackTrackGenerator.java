package mainP;

import java.util.ArrayList;

/**
 * A class to create backtrack generators from
 * @author jonreis
 */
public abstract class BackTrackGenerator implements BackTracking {
	
	

	
	private int problemSize=0;
	private int problemIndex=0;
	private ArrayList<Integer> choices=new ArrayList<Integer>();
	private boolean isBackTracking=false;
	private int backtrackNumber=0;
	private int backTrackLevels=0;
	private ArrayList<Integer> usedSolution= new ArrayList<Integer>();

	
	/**
	 * creates a new back track generator
	 * @param problemSize - int of all the problems
	 * @param choices - the number of different solutions to the problems
	 */
	BackTrackGenerator(int problemSize,int[] choices){
		this.problemSize=problemSize;
		for(int i:choices) {
			this.choices.add(i);
		}
	} 
	/**
	 * starts running the generator, will randomly pick a choice and cycle through and
	 * compare against constraints till the generation is complete and calls done() method
	 */
	public void run() {
		for(problemIndex=0;problemIndex<problemSize;problemIndex++) {
			
			int rand=(int)(Math.random()*choices.size());
			int choice = choices.get(rand);
		
			setChoice(problemIndex,choice);
			
			checkConstraints(choice,setConstraints(problemIndex));
			
			if(backtrackNumber<problemIndex) {
				isBackTracking=false;
				backtrackNumber=0;
			}
		}
		done();
	}		

	/**
	 * A recursive method that checks the constraints of the generator, uses the choice integer and 
	 * compares it with the constraints, adds the item of choice if no constraints exist,
	 * otherwise backtracks through all the choices and constraints till one is found,
	 * then the backtracking interface method set choice is ran  
	 * @param choice - a int the represents a number of choices of items to compare against constraints
	 * @param constraints - the constraints to compare against, set with backtrack interface method set constraints
	 */
	public void checkConstraints(int choice,boolean constraints) {
		
		
		if(!constraints&&!usedSolution.contains(choice)) {
			
		}
		else {
			usedSolution.add(choice);
			
			
			if(usedSolution.containsAll(choices)) {
				allChoicesUsed();
			}
			else {
				choice++;
				if(choice>choices.size()) {
					choice=1;
				}
				
					setChoice(problemIndex,choice);
					checkConstraints(choice,setConstraints(problemIndex));
			}
		}
		
		
	}
	/**
	 * When all choices are used for a single problem, backtracks and cycles through different possibilities
	 * till the constraints are solved
	 */
	private void allChoicesUsed() {
		if(isBackTracking) {
		
			backTrackLevels++;
			
			if(backTrackLevels>10) {
				backTrackLevels=0;
				discardChoice(problemIndex);
				discardChoice(problemIndex-1);
				discardChoice(problemIndex-2);
				discardChoice(problemIndex-3);
				discardChoice(problemIndex-4);
				discardChoice(problemIndex-5);
				discardChoice(problemIndex-6);
				problemIndex=problemIndex-6;
			}
			
			
			
		}
		else {
			isBackTracking=true;
			this.backtrackNumber=this.problemIndex;
			
			
		}
		usedSolution.clear();
		discardChoice(problemIndex);
		problemIndex=problemIndex-2;
	}
}





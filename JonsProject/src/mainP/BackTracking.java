package mainP;

public interface BackTracking {

	void setChoice(int problemIndex, int choice);
	boolean setConstraints(int problemIndex);
	void discardChoice(int problemIndex);
	void done();
	
}

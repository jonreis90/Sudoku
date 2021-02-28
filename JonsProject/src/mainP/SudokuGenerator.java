package mainP;

public class SudokuGenerator extends BackTrackGenerator{

	SudokuAI sudokuAI;
	private static final int PROBLEM_SIZE=81;
	private static final int[] CHOICES= {1,2,3,4,5,6,7,8,9};
	private static final int SLEEP_TIME=0;
	NumberCell[] allCells;
	
	
	
	SudokuGenerator(SudokuAI sudokuAI) {
		super(PROBLEM_SIZE, CHOICES);
		this.sudokuAI=sudokuAI;
		this.allCells=sudokuAI.sudoku.getAllNumberCells();
		
	}

	@Override
	public void setChoice(int problemIndex,int choice) {
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		allCells[problemIndex].setMainNumber(choice);
		
	}

	@Override
	public boolean setConstraints(int problemIndex) {
		return sudokuAI.isSelectedCellWrong(allCells[problemIndex]);
	}

	@Override
	public void discardChoice(int problemIndex) {
		allCells[problemIndex].clearMainNumber();
		
	}

	@Override
	public void done() {
		System.out.println("DONE GENERATING SUDOKU!!!!!!!!!!!!!!");
		String string="{";
		
		for(NumberCell cell:sudokuAI.sudoku.getAllNumberCells()) {
			int num=cell.getMainNumber();
			string=string+num+",";
		}
		string=string+"}";
		
		SudokuFileIO writer=new SudokuFileIO("SudokuSolutions.txt");
		writer.addNewSudoku(string);
		
		for(NumberCell cell:sudokuAI.sudoku.getAllNumberCells()) {
			cell.clearMainNumber();
		}
		new SudokuGenerator(sudokuAI).run();
		
	}

	
}

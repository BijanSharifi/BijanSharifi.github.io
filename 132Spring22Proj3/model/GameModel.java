package model;



public abstract class GameModel {
	protected BoardCell[][] board;
	private int rows;
	private int cols;

	
	public GameModel(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
		board=new BoardCell[rows][cols];
		for(int row=0; row<board.length; row++) {
			for(int col=0; col<board[row].length; col++) {
				board[row][col]=BoardCell.EMPTY;
			}
		}
		
		
	}

	
	public int getRows() {
		return this.rows;
	}


	public int getCols() {
		return this.cols;
	}
	
	
	public BoardCell[][] getBoard() {
		return board;   
	}

	
	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		board[rowIndex][colIndex]=boardCell;
	}
	

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	
	}
	
	/** Provides a string representation of the board 
	 * We have implemented this method for you.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Board(Rows: " + board.length + ", Cols: " + board[0].length + ")\n");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				buffer.append(board[row][col].getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public abstract boolean isGameOver();
	
	public abstract int getScore();

	
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board
	 * state and the selected cell.
	 * @param rowIndex row that user has clicked
	 * @param colIndex column that user has clicked
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}
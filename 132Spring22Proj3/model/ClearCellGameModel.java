package model;

import java.util.Random;



public class ClearCellGameModel extends GameModel {
	private Random random;
	private int currentScore;
	private int up;
	private int down;
	private int right;
	private int left;
	
	
	
	
	
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random=random;
		currentScore=0;
		
		
		
		
		
	}

	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	
	
	/*
	 * The game is over when the last row contains a cell that is not empty. This method uses a 
	 * for loop to go through every cell in the last row. If the number of empty cells is the 
	 * same as the number of columns, then the game is still not over. But if there is any variation
	 * the game is considered over.
	 */
	public boolean isGameOver() {
		int empty=0;
		for(int col=0; col<getCols(); col++) {
			if(getBoardCell(board.length-1, col)==BoardCell.EMPTY) {
				empty++;
			}
		}
		if(empty==getCols()) {
			return false;
		}else {
			return true;
		}
		
		
		
		
		
	}

	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 */
	public int getScore() {
		return this.currentScore;

	}

	
	
	

	/*
	 * If the game is over, this method does nothing and just returns. If the game is still going,
	 * there is nested for loops which start at the row second to the bottom. The column starts at zero.
	 * If the boardCell at that row and column is not empty the boardCell of the row above and the same
	 * column and sets it equal to the previous row and column. And when the row equals zero, that is when
	 * it gets set to the random board cell passing in the random instance.
	 */
	public void nextAnimationStep() {
		if(isGameOver()==true) {
			return;
		}else {
			for(int row=getRows()-2; row>-1; row-- ) {
				for(int col=0; col<getCols(); col++) {
					if(getBoardCell(row, col)!=BoardCell.EMPTY) {
						board[row+1][col]=board[row][col];
					}
					if(row==0) {
						board[row][col]=BoardCell.getNonEmptyRandomBoardCell(random);
					}
					
				}
			}
			
			
		}
	}
		


	
	
	/*
	 * Initialize the instance variables up, down, left, and right. This makes it very easy to 
	 * plug these variables in to the board 2-D array. If the rows are greater than the number 
	 * of rows or columns are greater than the number of columns, or they are less than zero then
	 * IllegalArgumentException is thrown. Then if the rowIndex and colIndex are not empty the method
	 * proceeds. As long as up is greater than -1, so that it stays in bounds, we check if it is the same
	 * as the indexes in the argument. If it is they are set to empty and the score increases. Then
	 * this checks the cell diagonally to the top right. If right is less than the columns we check
	 * if this is the same as the rowIndex and colIndex. If they are we set it equal to empty and increases score.
	 * This is also done for the left direction and check the square to the left and up. We also do the same
	 * thing in the down direction. Then we check left and right. And then we also set the target cell
	 * equal to BoardCell.Empty. Then we have to collapse the row. This is done by checking if that row 
	 * is empty. If it is we proceed because it can be collapsed. Then we have another for-loop which sets
	 * the counter equal to the current row that we are on because that is what we are collapsing. Then the 
	 * board gets set to the square below it and the square below it gets set to empty.
	 */
	public void processCell(int rowIndex, int colIndex) {
		this.up=rowIndex-1;
		this.down=rowIndex+1;
		this.right=colIndex+1;
		this.left=colIndex-1;
		
		
	
	
		
		if(rowIndex>getRows() || rowIndex<0) {
			throw new IllegalArgumentException("Invalid row index");
			
			
		}
		if(colIndex>getCols()|| colIndex<0) {
			throw new IllegalArgumentException("Ivalid column index");
		}
		if(board[rowIndex][colIndex]!=BoardCell.EMPTY) {
			
			if(up>-1) {
				if(board[up][colIndex]==board[rowIndex][colIndex]) {
					board[up][colIndex]=BoardCell.EMPTY;
					currentScore++;
					
				}
				if(right<getCols()) {
					if(board[up][right]==board[rowIndex][colIndex]) {
						board[up][right]=BoardCell.EMPTY;
						currentScore++;
					}
					
				}
				if(left>-1) {
					if(board[up][left]==board[rowIndex][colIndex]) {
						board[up][left]=BoardCell.EMPTY;
						currentScore++;
						
						
					}
					
				}
				
			}
			if(down<getRows()) {
				if(board[down][colIndex]==board[rowIndex][colIndex]) {
					board[down][colIndex]=BoardCell.EMPTY;
					currentScore++;
				}
				if(right<getCols()) {
					if(board[down][right]==board[rowIndex][colIndex]) {
						board[down][right]=BoardCell.EMPTY;
						currentScore++;
					}
				}
				if(left>-1) {
					if(board[down][left]==board[rowIndex][colIndex]) {
						board[down][left]=BoardCell.EMPTY;
						currentScore++;
					}
				}
			}
			if(left>-1) {  
				if(board[rowIndex][left]==board[rowIndex][colIndex]) {
					board[rowIndex][left]=BoardCell.EMPTY;
					currentScore++;
				}
			}
			if(right<getCols()) {
				if(board[rowIndex][right]==board[rowIndex][colIndex]) {
					board[rowIndex][right]=BoardCell.EMPTY;
					currentScore++;
				}
			}
			if(board[rowIndex][colIndex]==board[rowIndex][colIndex]) {
				board[rowIndex][colIndex]=BoardCell.EMPTY;
				currentScore++;
			}
			
			
		}
		for(int currentRow=getRows()-2; currentRow>0; currentRow--) {
			int empty=0;
			boolean isRowEmpty=false;
			for(int currentCol=0; currentCol<getCols(); currentCol++) {
				if(getBoardCell(currentRow, currentCol)==BoardCell.EMPTY) {
				empty++;
			}
		}
		if(empty==getCols()) {
			isRowEmpty=true;
		
		}
		if(isRowEmpty==true) {
			for(int stop=currentRow; stop<getRows()-1; stop++) {
				for(int newCol=0; newCol<getCols(); newCol++) {
					board[stop][newCol]=board[stop+1][newCol];
					board[stop+1][newCol]=BoardCell.EMPTY;
				}
			}
		}
		
		
		
		}
	
	}

}
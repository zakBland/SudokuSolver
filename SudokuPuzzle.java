import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SudokuPuzzle extends StackOverflowError{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException {
				
		Integer[][] unique = createPuzzleBoard("080009743050008010010000000800005000000804000000300006000000070030500080972400050");
		
		Board.printPuzzle(unique);

		Integer[][] s = solve(unique, 0, 0);
		
		Board.printPuzzle(s);

	}	
	
	public static Integer[][] solve(Integer[][] puzzle, int row, int col){
		int r = row;
		int c = col;
		boolean valid = false;
		while(true) {
			if(r == puzzle.length - 1 && c == puzzle.length - 1 && puzzle[r][c] != 0) {
				return puzzle;
			} else if(puzzle[r][c] == 0) {
				
				Integer[][] answer = null;
				for(int i = 1; i <= puzzle.length; i++) {
					
					if(Board.columnCheck(c, Math.abs(puzzle[r][c]) + (-1* i), puzzle) && Board.rowCheck(r, Math.abs(puzzle[r][c]) + (-1 * i), puzzle) &&
						Board.cubeCheck(r, c, Math.abs(puzzle[r][c]) + (-1 * i), puzzle)) {
						valid = true;
						puzzle[r][c] = -i;
					
						answer = solve(puzzle, r, c);
						
						if(answer == null) {
							puzzle[r][c] = 0;
							valid = false;
						}
					}
				}
				
				if(!valid) {
					return null;
				}
			}else {
				if(c == puzzle.length - 1 && r < puzzle.length) {
					c = 0;
					r++;
				}else {
					c++;
				}
			}
		
		}
	}

	public static Integer[][] createPuzzleBoard(String numList){
		if(numList.length() != 81) return null;
		
		Integer[][] puzzle = new Integer[9][9];
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				puzzle[i][j] = Integer.parseInt(numList.charAt(i * 9 + j) + "");
			}
		}
		
		return puzzle;
	}
	
	public static void testing() {
		File file = new File("");

		if(!file.exists()) return;
		
		BufferedReader buffer = null;
		try {
			FileReader reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			
			String line;
			int lineCount = 0;
			long solved = 0;
			long puzzles = 0;
			long overflow = 0;
			int maxBlanks = -1;
			Integer[][] puzzle = null;
			Integer[][] solution = null;
			

			while((line = buffer.readLine()) != null) {
						
				if(lineCount == 0) {
					puzzle = createPuzzleBoard(line.trim());
					Integer[] left = Board.numbersLeft(puzzle);
					maxBlanks = Math.max(left[0], maxBlanks);
					
					puzzles++;
					lineCount++;
				}else {
					//solution
					solution = createPuzzleBoard(line.trim());
					
					Integer[][] solvedSolution = null;
					
					try {
						 solvedSolution = solve(puzzle, 0, 0);
						 if(Board.same(solution, solvedSolution)) {
							 solved++;
						 }else {
							 Board.printPuzzle(solvedSolution);
							 Board.printPuzzle(solution);
							 System.out.println("This puzzle is still valid: " + Board.isValid(solvedSolution));

						 }
					}catch(StackOverflowError e) {
						overflow++;
						System.out.println("Overflow!");
					}
					
					
					lineCount--;
				}
			}
			
			System.out.println(solved + " puzzles were solved out of " + puzzles + ". Accuracy is " + (solved/(puzzles * 1.0) * 100.0) + "%");
			System.out.println("Overflowed: " + overflow);
			System.out.println("Max blanks are " + maxBlanks);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(buffer != null) buffer.close();
			}catch(IOException e) {
				
			}
		}
	}
}

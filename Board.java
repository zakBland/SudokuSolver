import java.util.ArrayList;
import java.util.Arrays;


public class Board {

	public static boolean cubeCheck(int r, int c, int num, Integer[][] newSudoku) {

		/*int maxValue = (int) Math.sqrt(newSudoku.length);
		
		for(int i = ((r/maxValue) * maxValue); i < ((r/maxValue) * maxValue) + maxValue; i++){
			for(int j = ((c/maxValue) * maxValue); j < ((c/maxValue) * maxValue) + maxValue; j++) {
				if(newSudoku[i][j] == num) {
					return false;
				}
			}
		}
		
		
		return true;*/
		boolean notRepeated = true;
		int cellNum = r * 9 + c;
		int a; 
		int b;
		if(cellNum >= 0 && cellNum <= 26)
			a = 0;
		else if(cellNum >= 27 && cellNum <= 53)
			a = 3;
		else
			a = 6;
		Integer[] one = {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47, 54, 55, 56, 63, 64, 65, 72, 73, 74};
		Integer[] two = {3, 4, 5, 12, 13, 14, 21, 22, 23, 30, 31, 32, 39, 40, 41, 48, 49, 50, 57, 58, 59, 66, 67, 68, 75, 76, 77};
 		
		if(Arrays.asList(one).contains(cellNum))
			b = 0;
		else if(Arrays.asList(two).contains(cellNum)) 
			b = 3;
		else
			b = 6;
		
		for(int i = a; i < a + 3; i++) {
			for(int j = b; j < b + 3; j++) {
				if(Math.abs(num) == Math.abs(newSudoku[i][j])) 
					notRepeated = false;
			}
		}
		return notRepeated;
	}
	
	public static boolean columnCheck(int c, int num, Integer[][] newSudoku) {
		boolean noRepeated = true;
		for(int i = 0; i < newSudoku.length; i++) {
			if(Math.abs(num) == Math.abs(newSudoku[i][c])) {
				noRepeated = false;
			}		
		}
			return noRepeated;	
	}
	
	public static boolean rowCheck(int r, int num, Integer[][] newSudoku) {
		boolean noRepeated = true;
		for(int j = 0; j < newSudoku.length; j++) {
			if(Math.abs(num) == Math.abs(newSudoku[r][j])) {
				noRepeated = false;
			}
		}				
			return noRepeated;
	}
	
	static Integer[][] generateSudokuAnswer() {
		Integer[][] newSudoku = {
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				
		};
	
		boolean correct = false;
		int num = 0; 
		int index = 0 ; 
		int m = 9; 
		
		ArrayList<Integer> numberList = new ArrayList<Integer> (Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
		ArrayList <Integer> wrong = new ArrayList <Integer> ();
	
		for(int i = 0; i < 9; i++) {	
			for(int j = 0; j < 9; j++) {
				  while(correct == false) {				 
					index = (int)(Math.random() * m);
					num = numberList.get(index);

					if(columnCheck(j, num, newSudoku) && cubeCheck(i, j, num, newSudoku) && (newSudoku[i][j] == 0 ) && rowCheck(i, num, newSudoku)) {
						newSudoku[i][j] = num;	
						int add = 0;
						numberList.remove(index);
						if(wrong.size() != 0) {
							for(int x = 0; x < wrong.size(); x++) {
								numberList.add(wrong.get(x));
							add++;
						}
						wrong.clear();
						m += add;
					}
					m--;
					correct = true;					
				}	

				else {
					wrong.add(numberList.get(index));
					numberList.remove(index);
					m--;
					if(numberList.size() == 0) {
						for(int x = 0; x < 9; x++)
							newSudoku[i][x] = 0;
						if(i != 0 )
							i--;
						else
							i = 0;
						j = 0;
						m = 9;
						numberList = new ArrayList<Integer> (Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
						wrong.clear();
						
					} 
				}
			}
			  	correct = false;
		}
				m = 9;
				numberList = new ArrayList<Integer> (Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
	}
		return newSudoku;
}	
		
	public static boolean isValid(Integer[][] sudoku) {

		ArrayList <Integer> numbers = new ArrayList <> (Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));
		ArrayList <Integer> storeR = new ArrayList <> ();
		ArrayList <Integer> storeC = new ArrayList <> ();
		ArrayList <Integer> storeB = new ArrayList <> ();
	
		
		int rowCount = 0;
		int columnCount = 0;
		int boxCount = 0;
		int divide = 0;
		
		//check rows and columns
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				storeR.add(Math.abs(sudoku[i][j]));
				storeC.add(Math.abs(sudoku[j][i]));
			}
	
			java.util.Collections.sort(storeR);
			java.util.Collections.sort(storeC);
			
			if(storeR.equals(numbers)) {
				rowCount++;	
				storeR.clear();
			}
			if(storeC.equals(numbers)) {
				columnCount++;
				storeC.clear();
			}
		}
	
			
		//check squares
		int k = 0; int l = 0;
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				storeB.add(Math.abs(sudoku[(j/3) + k][(j%3) + l]));
				divide++;
				}
			java.util.Collections.sort(storeB);

			if(storeB.equals(numbers)) {
				boxCount++;	
				storeB.clear();
			}
			if(divide % 9 == 0 && divide != 27 && divide != 54)	
				k += 3;
			if(divide == 27 || divide == 54) {
				k = 0;
				l += 3;
			}
		}
		

		//return value
		if(rowCount == 9 && columnCount == 9 && boxCount == 9) 
			return true;
		else
			return false;	
	}
	
	public static Integer[] numbersLeft(Integer[][] sudoku) {
		
		Integer[] count = {0,0,0,0,0,0,0,0,0,0};
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				count[sudoku[i][j]]++;			
				}
		    }

		return count;
	}
	
public static Object[] spacesLeft(Integer[][] sudoku, String[] strings, String s) {
		Integer[] cList = new Integer[81];
		
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				if(s.equals("s"))
					strings[i * sudoku.length + j] = "";
				else {
					if(sudoku[i][j] == 0) {	
						cList[i * sudoku.length + j] = (validList(i, sudoku, (new ArrayList<Integer>(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9})))).size());
					}
				}
				}
		    }
		
		if(s == "s")
			return strings;
		else
			return cList;
	}
	
	public static Integer[][] createPuzzle(Integer[][] sudokuAnswer, String difficulty){
		Integer[][] newPuzzle = new Integer[9][9];
		int blanks = 0;
		int sum = 0;
		int cell = 0;
		int index = 0;
		int m = 81;
		
		Integer[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ,16, 17, 18,19,20, 21, 22, 23, 24, 25,
				26, 27, 28, 29, 30 ,31, 32 , 33, 34, 35, 36, 37, 38, 39, 40 , 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
				51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 
				76, 77, 78, 79, 80};
		ArrayList <Integer> numbers = new ArrayList <Integer>();
		
		ArrayList <Integer> numArray = new ArrayList <Integer> (Arrays.asList(num));
		if(new ArrayList <String> (Arrays.asList(new String[] {"easy", "Easy", "E", "e", "EASY"})).contains(difficulty)) {
			blanks = 40;
		}
		else if(new ArrayList <String> (Arrays.asList(new String[] {"medium", "Medium", "M", "m", "MEDIUM"})).contains(difficulty)) {
			blanks = 50;
		}
		else if(new ArrayList <String> (Arrays.asList(new String[] {"hard", "Hard", "H", "h", "HARD"})).contains(difficulty)) {
			blanks = 66;
		}
		
		for(int i = 0; i < blanks; i++) {
			index = (int)(Math.random() * m);
			cell = numArray.get(index);
			numbers.add(cell);
			numArray.remove(index);				
			m--;


		}
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				sum = i * 9 + j;
				if(numbers.contains(sum)) {
					newPuzzle[i][j] = 0;			
				}
				else
					newPuzzle[i][j] = sudokuAnswer[i][j];
					
			}
		};
		return newPuzzle;
	}

	public static void printPuzzle(Integer[][] sudoku) {
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				if(sudoku[i][j] == 0)
					System.out.print(" " + " ");
				else
					System.out.print(Math.abs(sudoku[i][j]) + " ");
				
			}
			System.out.print("\n");
		}
	}

public static boolean same (Integer[][] puzzle, Integer[][] sudokuAnswer) {
	boolean solved = true;

	for(int i = 0; i < puzzle.length; i++) {
		for(int j = 0; j < puzzle.length; j++) {
			if(Math.abs(puzzle[i][j]) != Math.abs(sudokuAnswer[i][j])) {
				solved = false;
			}
		}
	}
		
		return solved;
	}

public static ArrayList<Integer> validList(int r, Integer[][] puzzle, ArrayList<Integer> list){
	for(int i = 0; i < 9; i++) {
		if(list.contains(puzzle[r][i]))
			list.remove(puzzle[r][i]);
	}
	
	return list;
}



}
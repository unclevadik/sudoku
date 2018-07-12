package com.hlushko.job.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Sudoku 
{
	//argument example: /app/sudoku/input_sudoku[2].txt
	private static int SUDOKU_SIZE = 9; //assuming that Sudoku size is 9x9
	private static String REGEX_SUDOKU_NUMBER = "\\d{" + SUDOKU_SIZE + "}";
	private static final String VALID = "valid";
	private static final String INVALID = "invalid";
	private static final String ERROR_MESSAGE="Invalid results file format. File should contain " 
										+ SUDOKU_SIZE + " lines of " + SUDOKU_SIZE  + " numbers"; 

	public static void main( String[] args ) throws SudokuValidationException
    {
	if (args != null && args.length >0) {
		new Sudoku(args[0]).solve();
	} else {
		System.out.println("Please provide full path to Sudoku solution file as an argument:\njava -cp Sudoku-1.0-SNAPSHOT.jar com.hlushko.job.sudoku.Sudoku /app/sudoku/<FILE_NAME>");
	}
    }
    
	private String fileName;
	
	public Sudoku(String fileName) {
		this.fileName = fileName;
	}
	
	public void solve() {
		try {
			System.out.println("Loaded Sudoku results are " + (validateSudokuResults(Files.readAllLines(Paths.get(fileName))) ? VALID : INVALID)); 
		} catch (NoSuchFileException e) {
			System.out.println("File not found.:\nPlease provide full path to Sudoku solution file as an argument:\n" + 
				"java -cp Sudoku-1.0-SNAPSHOT.jar com.hlushko.job.sudoku.Sudoku <FULL_FILE_NAME>");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File loading EXCEPTION handling:\n");
			e.printStackTrace();
		} catch (SudokuValidationException e) {
			System.out.println("Custom Sudoku EXCEPTION handling:\n");
			e.printStackTrace();
		}
	}

    public boolean  validateSudokuResults(List<String> load) throws SudokuValidationException {
    	int[][] results = loadSudokuResults(load);
    	for (int i = 0; i < SUDOKU_SIZE;) {
            int[] row = new int[SUDOKU_SIZE];
            int[] square = new int[SUDOKU_SIZE];
            int[] column = results[i].clone();

            for (int j = 0; j < SUDOKU_SIZE; j ++) {
                row[j] = results[j][i];
                square[j] = results[(i / 3) * 3 + j / 3][i * 3 % SUDOKU_SIZE + j % 3];
            }
            return validateSudokuLine(column) && validateSudokuLine(row) && validateSudokuLine(square);
        }
        return true;
	}

	public int[][] loadSudokuResults(List<String> load) throws SudokuValidationException {
    	int[][] results = new int[SUDOKU_SIZE][SUDOKU_SIZE]; 
    	if (load == null || load.size() < SUDOKU_SIZE) {
    		throw new SudokuValidationException(ERROR_MESSAGE);
    	}
    	int i = 0;
    	for(String line: load) {
    		if (line != null && line.trim().length() >0) { 
    			if ( !line.matches(REGEX_SUDOKU_NUMBER)) {
    				throw new SudokuValidationException(ERROR_MESSAGE + "\n Exception line : " + line);
    			}
    			for(int j = 0; j < line.length(); j++) {
    				results[i][j] = Integer.parseInt(line.split("")[j]);
					}		
    			i++; 
			}
    	}
		return results;
	}

    public boolean validateSudokuLine(int[] variant) {
        int i = 0;
        Arrays.sort(variant);
        for (int number : variant) {
            if (number != ++i)
                return false;
        }
        return true;
    }
}

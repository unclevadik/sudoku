package com.hlushko.job.sudoku;

import java.util.Arrays;

import junit.framework.TestCase;

public class SudokuTest extends TestCase {
	private static final String[] VALID_SUDOKU_RESULT = {"534678912","672195348","198342567","","859761423",
										"426853791","713924856","961537284","287419635","345286179"}; 
	private static final String[] INVALID_SUDOKU_RESULT = {"672195348","198342567","859761423",
			"426853791","713924856","961537284","287419635","345286179","534678912"}; 
	private static final String[] INVALID_SUDOKU_RESULT_FORMAT = {"78912","672195348","198342567","","859761423",
			"426853791","713924856","961537284","287419635","345286179"};
	private static final int[] VALID_SUDOKU_LINE = new int[] {3,4,5,2,8,6,1,7,9}; 
	private static final int[] INVALID_SUDOKU_LINE = new int[] {1,4,5,2,8,6,1,7,9}; 

	private Sudoku sudoku;

	@Override
	protected void setUp() throws Exception {
		sudoku = new Sudoku("");
	}
	
    public void testValidateSudokuPositive()
    {
        assertTrue(sudoku.validateSudokuLine(VALID_SUDOKU_LINE));
    }
    
    public void testValidateSudokuNegative()
    {
        assertFalse(sudoku.validateSudokuLine(INVALID_SUDOKU_LINE));
    }

    public void testValidSudokuResult() throws SudokuValidationException
    {
        assertTrue(sudoku.validateSudokuResults(Arrays.asList(VALID_SUDOKU_RESULT)));
    }

    public void testInvalidSudokuResult() throws SudokuValidationException
    {
        assertFalse(sudoku.validateSudokuResults(Arrays.asList(INVALID_SUDOKU_RESULT)));
    }

    public void testInvalidSudokuresult()
    {
       	try {
			sudoku.validateSudokuResults(Arrays.asList(INVALID_SUDOKU_RESULT_FORMAT));
        	fail();
		} catch (SudokuValidationException e) {
		}
    }

}

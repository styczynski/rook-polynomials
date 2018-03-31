
public class PolytowerUtil {
	
	/**
	 * A utility function to check if the towers on the board
	 * are placed correctly (does not attack themselves)
	 * 
	 * @param board - NxN 0/1 matrix containing tower positions.
	 * @param row - number of field row that is checked
	 * @param column - number of field column that is checked
	 * @return boolean - true if tower at position (row, column) does not attac anything
	 **/
	private static boolean isSafePlacement(int board[][], int row, int column) {
		
	    /* Check attacting on the left side of a tower*/
	    for (int i = 0; i < column; i++) {
	        if (board[row][i]>0) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
	/**
	 * Variable to capture number of solutions generated by solveRec
	 **/
	private static int numberOfSolutions = 0;
	
	/**
	 *
	 * A recursive function to solve a problem where there is
	 * {@code towToBePlaced} towers to place on board with allowed places marked in {@code allowedFields} matrix.
	 * Number of currently placed towers is {@code towPlaced} and they are placed in {@code board matrix}.
	 * {@code column} is number of column that is already being matched.
	 * The recursive function works in backtracking manner.
	 * Board matrix is modified but left as it was after function exit.
	 * Function saves number of found configurations in {@code numberOfSolutions} variable.
	 *
	 *
	 * @param board - Empty NxN 0/1 matrix used as helper (left empty at exit)
	 * @param allowedFields - NxN 0/1 matrix marking available board fields
	 * @param towToBePlaced - Total number of tower to be placed
	 * @param towPlaced - Currently placed towers
	 * @param column - Number of column currently modified
	 */
	private static void solveRec(int board[][], boolean allowedFields[][], int towToBePlaced, int towPlaced, int column){
		final int dim = board.length;
		
		
	    if (towPlaced == towToBePlaced && column>=dim) {
        	numberOfSolutions++;
	    }
	    
	    if(column >= dim) {
			return;
		}

	    for (int i = 0; i < dim; i++)
	    {
	        if ( isSafePlacement(board, i, column) && allowedFields[i][column] )
	        {
	            board[i][column] = 1;
	            solveRec(board, allowedFields, towToBePlaced, towPlaced+1, column + 1);
	            board[i][column] = 0;
	        }
	    }
	    solveRec(board, allowedFields, towToBePlaced, towPlaced, column + 1);
	}
	
	/**
	 * Function to find number of tower-placing solutions for given board.
	 * 
	 * @param board - Empty NxN 0/1 matrix used as helper (left empty at exit)
	 * @param allowedFields - NxN 0/1 matrix marking available board fields
	 * @param towToBePlaced - Total number of tower to be placed
	 * @return number of solutions
	 */
	private static int solve(int board[][], boolean allowedFields[][], int towToBePlaced){
		numberOfSolutions = 0;
		solveRec(board, allowedFields, towToBePlaced, 0, 0);
		return numberOfSolutions;
	}
	
	/**
	 * Function generating tower-polynomial (polytower) for given board.
	 * It returns array of polynomial factors.
	 * * int[0] will be polynomial constant
	 * * int[1] is factor associated with x^1
	 * * etc.
	 * 
	 * @param allowedFields - NxN 0/1 matrix marking available board fields
	 * @return int[] with polynomial factors
	 */
	public static int[] generatePolytower(boolean allowedFields[][]) {
		final int dim = allowedFields.length;
		int qmax = 0;
		
		for(int y=0;y<dim;++y) {
			for(int x=0;x<dim;++x) {
				if(allowedFields[y][x]) ++qmax;
			}
		}
		
		int polytower[] = new int[qmax+1];
		for(int i=0;i<=qmax;++i) {
			polytower[i] = 0;
		}
		
		int board[][] = new int[dim][dim];
		for(int y=0;y<dim;++y) {
			for(int x=0;x<dim;++x) {
				board[y][x]=0;
			}
		}
		
		for(int q=0;q<=qmax;++q) {
			polytower[q] = solve(board, allowedFields, q);
		}
		return polytower;
	}
	
	/**
	 * Function converting polytower factors to normalized
	 * human-readable form.
	 * 
	 * @param polytower - int[] polytower
	 * @return String representation of given polynomial
	 */
	public static String polytowerToString(int[] polytower) {
		String result = "";
		int len = polytower.length;
		boolean addLeadingOp = false;
		for(int i=0;i<len;++i) {
			if(i==0 && polytower[0]!=0) {
				result += (polytower[0]);
				addLeadingOp = true;
			} else if(i==1 && polytower[1]!=0) {
				if(addLeadingOp) result +=(" + ");
				if(polytower[1]==1) {
					result +=("x");
				} else {
					result +=(polytower[1]+"x");
				}
				addLeadingOp = true;
			} else if(polytower[i]!=0) {
				if(addLeadingOp) result +=(" + ");
				if(polytower[i]==1) {
					result +=("x^"+i);
				} else {
					result +=(polytower[i]+"x^"+i);
				}
				addLeadingOp = true;
			}
		}
		return result;
	}
	
	/**
	 * Function printing polytower in normalized
	 * human-readable form to the standard output.
	 * 
	 * @param polytower - int[] polytower
	 */
	static void printPolytower(int[] polytower) {
		System.out.println(polytowerToString(polytower));
	}
	
	
}

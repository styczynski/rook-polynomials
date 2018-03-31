import java.util.Scanner;

public class Main {
	 
	private static Scanner s;

	/**
	 * Entry point for testing algorithm.
	 */
	public static void main(String[] args) {
		
		s = new Scanner(System.in);
		final int dim = s.nextInt();
		
		boolean boardAllowedFields[][] = new boolean[dim][dim];
		s.nextLine();
		for(int y=0;y<dim;++y) {
			final String line = s.nextLine();
			for(int x=0;x<dim;++x) {
				boardAllowedFields[y][x] = (line.charAt(x)=='#');
			}
		}
		
		int polytower[] = PolytowerUtil.generatePolytower(boardAllowedFields);
		PolytowerUtil.printPolytower(polytower);
		
		s.close();
	}
	
}

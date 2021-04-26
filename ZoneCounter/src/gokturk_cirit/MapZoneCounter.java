/**
 * 
 */
package gokturk_cirit;

/**
 * @author Gokturk Cirit
 *
 */
public class MapZoneCounter implements ZoneCounterInterface {

	/**
	 * @param args
	 * @throws Exception
	 */
	
	public static int row;
	public static int col;
	public static int[][] mapArray;
	
	public static void main(String[] args) throws Exception {
		
		
		MapZoneCounter zoneCount = new MapZoneCounter();
		zoneCount.Init(new MapInterface() {
			
			// Creates / Allocates a map of given size.
			@Override
			public void SetSize(Dimension dim) {
				row = dim.width;
				col = dim.height;
				mapArray = new int [row][col];
				
			}
			
			// Get dimensions of given map.
			@Override
			public void GetSize(Dimension dim) {
				System.out.println("Dimensions of the map are : " + dim.width + " x " + dim.height);
				
			}
			
			// Sets border at given point.
			@Override
			public void SetBorder(int x, int y) throws Exception {
				if(mapArray[x][y] == 1)
					System.out.println("Specified coordinate is already a border!");
				else {
				mapArray[x][y] = 1;
				System.out.println("coordinates : " + x + "," + y + " set as border succesfully.");
				}
				
			}
			
			// Clears border at given point.
			@Override
			public void ClearBorder(int x, int y) throws Exception {
				if(mapArray[x][y] == 0)
					System.out.println("Specified coordinate is not a border!");
				else {
				mapArray[x][y] = 0;
				System.out.println("Border coordinates : " + x + "," + y + " removed succesfully.");}
				
			}
			
			// Checks if given point is border.
			@Override
			public boolean IsBorder(int x, int y) throws Exception {
				System.out.println("Is the point [" + x +"][" + y + "] a border?  ");
				return (mapArray[x][y] == 1);
			}
			
			// Show map contents.
			@Override
			public void Show() {
				for(int[] row: mapArray){
				      for(int item: row){
				        System.out.print(item);
				      }
				      System.out.println();
				      
				    }
				
			}
			
		});
		
		System.out.println();
		System.out.println("Calculated number of region(s) = " + zoneCount.Solve());

	}
	
	// Feeds map data into solution class, then get ready for Solve() method.
	@Override
	public void Init(MapInterface map) throws Exception {
		Dimension dim = new Dimension(4,9);
		map.SetSize(dim); 
		map.SetBorder(0,1);
		map.SetBorder(1,0);
		map.SetBorder(1,2);
		map.SetBorder(2,1);
		map.SetBorder(1,1);
		map.SetBorder(1,3);
		map.SetBorder(1,4);
		map.SetBorder(1,5);
		map.SetBorder(1,6);
		map.SetBorder(1,7);
		map.SetBorder(3,1);
		map.SetBorder(0,5);
		System.out.println(map.IsBorder(3, 1));
		System.out.println("getSize() method is initiated...");
		map.GetSize(dim);
		System.out.println();
		map.Show();
		
	}
	
	// Counts zones in map provided with Init() method, then return the result.
	@Override
	public int Solve() throws Exception {
		return countIslands(mapArray);
	}
	
	// Method for checking range and visited index values
	public boolean isSafe(int M[][], int r, int c, boolean visited[][]) {
		// row number is in range, column number is in range
		// and value is 1 and not yet visited
		return (r >= 0) && (r < row) && (c >= 0) && (c < col) && (M[r][c] == 0 && !visited[r][c]);
	}
	
	// A utility function to do DFS for a 2D boolean matrix.
	// It only considers the 8 neighbors as adjacent vertices
	public void DFS(int M[][], int r, int c, boolean visited[][]) {
		// These arrays are used to get row and column numbers
		// of 8 neighbors of a given cell
		int rowNbr[] = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
		int colNbr[] = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

		// Mark this cell as visited
		visited[r][c] = true;

		// Recur for all connected neighbors
		for (int k = 0; k < 8; ++k)
			if (isSafe(M, r + rowNbr[k], c + colNbr[k], visited))
				DFS(M, r + rowNbr[k], c + colNbr[k], visited);
	}
	
	public int countIslands(int M[][]) {
		// Make a boolean array to mark visited cells.
		// Initially all cells are unvisited
		boolean visited[][] = new boolean[row][col];

		// Initialize count as 0 and traverse through the all cells
		// of given matrix
		int count = 0;
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < col; ++j)
				if (M[i][j] == 0 && !visited[i][j]) // If a cell with
				{ // value 1 is not
					// visited yet, then new island found, Visit all
					// cells in this island and increment island count
					DFS(M, i, j, visited);
					++count;
				}

		return count;
	}

}

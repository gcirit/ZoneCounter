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
			
			// Sets a border at given point.
			@Override
			public void SetBorder(int x, int y) throws Exception {
				if(mapArray[x][y] == 1) {
					System.out.println("Specified coordinate is already a border!");
				}
				else {
				mapArray[x][y] = 1;
				System.out.println("coordinates : " + x + "," + y + " set as border succesfully.");
				}
				
			}
			
			// Clears a border at given point.
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
				if(mapArray[x][y] == 1)
					return true;
				else{
					return false;
				}
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
		
		//Display calculation result
		System.out.println();
		System.out.println("Calculated number of Zone(s) = " + zoneCount.Solve());

	}
	
	// Feeds map data into solution class, then get ready for Solve() method.
	@Override
	public void Init(MapInterface map) throws Exception {
		
		//Create dimension object and set an empty map 
		Dimension dim = new Dimension(24,36);
		map.SetSize(dim); 
		
		//Create an 2D int array to store border coordinates
		int [][] points = new int [][] { {0,18},{0,24},{1,16},{1,17},{1,24},{2,15},{2,24},
		{3,14},{3,25},{4,12},{4,13},{4,25},{5,11},{5,26},{5,35},{6,10},{6,26},{6,27},{6,28},{6,29},{6,30},{6,31},{6,32},
		{6,33},{6,34},{7,8},{7,9},{7,26},{8,7},{8,27},{9,5},{9,6},{9,27},{10,4},{10,5},{10,27},{11,3},{11,6},{11,28},{12,2},{12,7},{12,28},
		{13,0},{13,1},{13,7},{13,25},{13,26},{13,27},{13,28},{13,29},
		{14,8},{14,21},{14,22},{14,23},{14,24},{14,29},
		{15,8},{15,17},{15,18},{15,19},{15,20},{15,29},
		{16,9},{16,13},{16,14},{16,15},{16,16},{16,30},
		{17,9},{17,10},{17,11},{17,12},{17,30},
		{18,10},{18,31},{19,10},{19,31},{20,11},{20,31},{21,11},{21,32},{22,12},{22,32},{23,12},{23,33}
		};
		
		//Store all border coordinates into map object
		for(int i = 0; i<points.length; i++)
			map.SetBorder(points[i][0],points[i][1]);
		
		//Check if a coordinate is border in the map objecy by calling isBorder method. 
		System.out.println(map.IsBorder(3, 1));
		
		//Get size of the map and print it to console 
		System.out.println("getSize() method is initiated...");
		map.GetSize(dim);
		System.out.println();
		
		//Display map
		map.Show();
		
	}
	
	// Counts zones in map provided with Init() method, then return the result.
	@Override
	public int Solve() throws Exception {
		return countZones(mapArray);
	}
	
	// Method for checking range and visited index values
	public boolean isSafe(int M[][], int r, int c, boolean visited[][]) {
		// row number is in range, column number is in range
		// and value is 1 and not yet visited
		return (r >= 0) && (r < row) && (c >= 0) && (c < col) && (M[r][c] == 0 && !visited[r][c]);
	}
	
	// A utility function to do DFS for a 2D boolean matrix.
	// It only considers the 4 neighbors as adjacent vertices
	public void DFS(int M[][], int r, int c, boolean visited[][]) {
		// These arrays are used to get row and column numbers
		// of 4 neighbors of a given cell
		int rowNbr[] = new int[] { -1, 0, 0, 1 };
		int colNbr[] = new int[] {  0, -1, 1, 0 };

		// Mark this cell as visited
		visited[r][c] = true;

		// Recur for all connected neighbors
		for (int k = 0; k < 4; ++k)
			if (isSafe(M, r + rowNbr[k], c + colNbr[k], visited))
				DFS(M, r + rowNbr[k], c + colNbr[k], visited);
	}
	
	public int countZones(int M[][]) {
		// Make a boolean array to mark visited cells.
		// Initially all cells are unvisited
		boolean visited[][] = new boolean[row][col];

		// Initialize count as 0 and traverse through the all cells
		// of given matrix
		int count = 0;
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < col; ++j)
				if (M[i][j] == 0 && !visited[i][j]) // If a cell with
				{ // value 0 is not
					// visited yet then a new zone is found. 
					// Visit all cells in this zone and increment zone count
					DFS(M, i, j, visited);
					++count;
				}

		return count;
	}

}

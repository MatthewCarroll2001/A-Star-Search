
/*
 * Store information about the world, including heuristics at each location,
 * start, and goal nodes
 */
public class World {

	//	Record heuristics for each location
	//	Hold on to blocked node locations
	//	Keep track of start and goal nodes
	private int[][] heuristics;
	private boolean[][] blocked;
	private Coord start;
	private Coord goal;
	
	public World(int[][] heuristics, boolean[][]blocked, Coord start, Coord goal) {
		this.heuristics = heuristics;
		this.blocked = blocked;
		this.start = start;
		this.goal = goal;
	}
	
	public int[][] getHeuristics() { return heuristics; }
	public int getHeuristic(int x, int y) { return this.heuristics[x][y]; } 
	
	public boolean isBlocked(int x, int y) { return blocked[x][y]; }
	
	public Coord getStart() { return start; }
	
	public Coord getGoal() { return goal; }
	
	public int getWidth() { return heuristics.length; }
	public int getHeight() { return heuristics[0].length; }
	
	
	
	// Prints world at each step of a path with some time between each one,
	// acts as a small animation
	public void animate(Coord[] path) throws InterruptedException {
		
		System.out.println("Path animated:");
		
		for (int i = 0; i < path.length; i++) {
			Thread.sleep(500);
			Coord[] currPath = new Coord[i + 1];
			for (int j = 0; j < i + 1; j++) {
				currPath[j] = path[j];
			}
			System.out.println("Step " + (i + 1) + ": \n" + currentPathState(currPath));
		}
		
		System.out.println("Finished!");
		Thread.sleep(500);
	}
	
	// Utility method for animate(),
	// displays current world's state with path nodes filled
	private String currentPathState(Coord[] currPath) {
		String display = "";
		
		// show x-axis
		display += "\t";
		for (int i = 0; i < heuristics[0].length; i++) {
			display += i + "\t";
		}
		display += "\n";
		
		// create divider
		display += "\t";
		for (int i = 0; i < heuristics[0].length; i++) {
			display += "--------";
		}
		display += "\n";
		
		for (int y = 0; y < heuristics.length; y++) {
			// show y axis
			display += y + "\t|";
			for (int x = 0; x < heuristics[0].length; x++) {
				
				boolean pathCoord = false;
				for (int i = 0; i < currPath.length; i++)
					if (currPath[i].x == x && currPath[i].y == y)
						pathCoord = true;
				
				if (!blocked[x][y] && !pathCoord)
					display += "O";
				else if (pathCoord)
					display += "-";

				//	Show which nodes are start and goal
				if (start.x == x && start.y == y)
					display += "(S)";
				else if (goal.x == x && goal.y == y)
					display += "(G)";
				else if (blocked[x][y])
					display += "X";
				
				display += "\t";
			}
			
			display += "\n";
		}
		
		return display;
	}
	
	// Displays entire current world state,
	// standard toString() method
	@Override
	public String toString() {
		String display = "";
		
		// show x-axis
		display += "\t";
		for (int i = 0; i < heuristics[0].length; i++) {
			display += i + "\t";
		}
		display += "\n";
		
		// create divider
		display += "\t";
		for (int i = 0; i < heuristics[0].length; i++) {
			display += "--------";
		}
		display += "\n";
		
		for (int y = 0; y < heuristics.length; y++) {
			// show y axis
			display += y + "\t|";
			for (int x = 0; x < heuristics[0].length; x++) {
				
				if (!blocked[x][y])
					display += "O";
				
				//	Show which nodes are start and goal
				if (start.x == x && start.y == y)
					display += "(S)";
				else if (goal.x == x && goal.y == y)
					display += "(G)";
				else if (blocked[x][y])
					display += "X";
				
				display += "\t";
			}
			
			display += "\n";
		}
		
		return display;
	}
	
}

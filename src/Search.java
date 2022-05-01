import java.util.ArrayList;
import java.util.Stack;

public class Search {

	/*
	 * Full implementation of A* Search utilizing the heuristics of the problem world
	 */
	public static Coord[] AStarSearch(World world) {
		
		System.out.println("Searching for a solution path...");
		
		// instantiate solution path array
		// create a frontier for searching as a queue
		ArrayList<Coord> frontier = new ArrayList<>();
		ArrayList<Coord> searched = new ArrayList<>();
		
		// add start node as first to be searched
		frontier.add(world.getStart());
		
		// search until frontier is empty or goal is found
		Coord curr = frontier.get(0);
		while (!frontier.isEmpty()) {
			
			// Choose next node by finding the node with the smallest
			// total distance + heuristic
			curr = frontier.get(0);
			int smallestFVal = world.getHeuristic(curr.x, curr.y) + curr.distance;
			for (int i = 1; i < frontier.size(); i++) {
				Coord node = frontier.get(i);
				int heur = world.getHeuristic(node.x, node.y);
				if (heur + node.distance < smallestFVal && node.isValid(world, searched)) {
					smallestFVal = heur;
					curr = node;
				}
			}
			
			// check goal state for current node
			if (curr.equals(world.getGoal())) {
				System.out.println("Goal found!");
				return findPath(curr);
			}
			
			// check all four directions
			Coord up = curr.up();
			Coord down = curr.down();
			Coord left = curr.left();
			Coord right = curr.right();
			if (up.isValid(world, searched)) {
				frontier.add(up);
				up.setParent(curr);
			}
			if (down.isValid(world, searched)) {
				frontier.add(down);
				down.setParent(curr);
			}
			if (left.isValid(world, searched)) {
				frontier.add(left);
				left.setParent(curr);
			}
			if (right.isValid(world, searched)) {
				frontier.add(right);
				right.setParent(curr);
			}
			
			// remove already searched node from frontier and add to searched list
			searched.add(curr);
			frontier.remove(curr);
		}
		
		// If frontier ends up empty with no solution found,
		// no solution is possible
		System.out.println("No Path Found");
		return null;
	}
	
	/*
	 * Uses reverse linked-list tactics to find the path to the
	 * node passed in (the last node in the search)
	 */
	public static Coord[] findPath(Coord finalNode) {
		
		Stack<Coord> reversePath = new Stack<>();
		
		Coord curr = finalNode;
		reversePath.push(curr);
		
		Coord parent = curr.getParent();
		while(parent != null) {
			reversePath.push(parent);
			parent = parent.getParent();
		}
		
		int len = reversePath.size();
		Coord[] path = new Coord[len];
		
		for (int i = 0; i < len; i++) {
			path[i] = reversePath.pop();
		}
		
		return path;
		
	}
	
}

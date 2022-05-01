import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	/*
	 * Main thread, runs main tile world program
	 * Throws clause is only for the animation portion
	 */
	public static void main(String[] args) throws InterruptedException {
		
		//	Create scanner for user input
		Scanner scanner = new Scanner(System.in);
		boolean continueProgram = true;
		
		while(continueProgram) {
			//	All worlds are required to be 15 by 15 and have a 10% blocked node rate
			final int HEIGHT = 15;
			final int WIDTH = 15;
			final double PERCENT_TILES_BLOCKED = 0.1;
			
			//	Generate initial world and print it out
			World world = generateWorld(WIDTH, HEIGHT, PERCENT_TILES_BLOCKED, scanner);
			System.out.println("Current world:\n" + world);
			
			// Find best path
			Coord[] bestPath = Search.AStarSearch(world);
			
			// If path is found, animate it and print the full path as a list of coordinates
			if (bestPath != null) {
				
				System.out.println("\n");
				world.animate(bestPath);
				
				System.out.println("Shortest path:");
				for (int i = 0; i < bestPath.length; i++) {
					System.out.println("Step " + (i + 1) + ": (" + bestPath[i].x + ", " + bestPath[i].y + ")");
				}
			}
			
			String response;
			
			System.out.println("\nDo you want to repeat this exercise? Enter Y or N: ");
			scanner.nextLine();
			response = scanner.nextLine();
			
			if (!(response.equals("Y") || response.equals("y"))) {
				continueProgram = false;
				scanner.close();
			}
		}
	}
	
	/*
	 * Generate a new world with open and blocked nodes,
	 * automatically calculate heuristics using manhattan distance method
	 */
	public static World generateWorld(int width, int height, double percentBlocked, Scanner scanner) {
		
		// instantiate heuristic matrix
		int[][] heur = new int[height][width];
		
		// initialize to be changed later
		Coord start = new Coord(0, 0);
		Coord goal = new Coord(14, 14);
		
		//	Get start node from user; repeat if values are out of range
		boolean inputReceived = false;
		while (!inputReceived) {
			
			int startx;
			int starty;
			
			try {
				System.out.println("Enter start location x value in range 0(left) to 14(right)");
				startx = scanner.nextInt();
				System.out.println("Enter start location y value in range 0(top) to 14(bottom)");
				starty = scanner.nextInt();
				
				if (startx < 0 || startx > 14 || starty < 0 || starty > 14)
					throw new ArrayIndexOutOfBoundsException("Error: must enter proper values for start node coordinate");
			}
			catch (IllegalArgumentException e){
				System.out.println("Error: must input an integer");
				continue;
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			start = new Coord(startx, starty);
			inputReceived = true;
		}
		
		//	Get goal node from user; make sure goal is different from start
		inputReceived = false;
		while (!inputReceived) {
			
			int goalx;
			int goaly;
			
			try {
				System.out.println("Enter goal node x value (0 to 14)");
				goalx = scanner.nextInt();
				System.out.println("Enter start node y value (0 to 14)");
				goaly = scanner.nextInt();
				
				if (goalx < 0 || goalx > 14 || goaly < 0 || goaly > 14)
					throw new ArrayIndexOutOfBoundsException("Error: must enter proper values for goal node coordinate");
			}
			catch (IllegalArgumentException e){
				System.out.println("Error: must input an integer");
				continue;
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			goal = new Coord(goalx, goaly);
			
			if (goal.equals(start)) {
				System.out.println("Error: goal node must be different from start node");
				continue;
			}
			
			inputReceived = true;
		}
		
		boolean[][] blocked = new boolean[15][15];
		
		//	Find 10% of random tiles and make them blocked
		int n = width * height;
		for (int i = 0; i < (int)(n * percentBlocked); i++) {
			Random rand = new Random();
			int rand_tilex = rand.nextInt(15);
			int rand_tiley = rand.nextInt(15);
			
			//	Make sure not to block start or goal, or a node that's already blocked
			if (blocked[rand_tilex][rand_tiley]
				|| (rand_tilex == start.x && rand_tiley == start.y)
				|| (rand_tilex == goal.x && rand_tiley == goal.y)) {
				
				i--;
				continue;
			}
			
			blocked[rand_tilex][rand_tiley] = true;
		}
		
		//	Find heuristic
		//	In this case, it is the Manhattan distance
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (!blocked[i][j])
					heur[i][j] = Math.abs(i - goal.x) + Math.abs(j - goal.y);
		
		return new World(heur, blocked, start, goal);
	}

}

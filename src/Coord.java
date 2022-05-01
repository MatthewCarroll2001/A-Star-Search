import java.util.ArrayList;

/*
 * Store simple coordinate; used for nodes in tile world
 */
public class Coord {
	
	private Coord parent;
	public int x;
	public int y;
	public int distance;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
		this.distance = 0;
	}
	
	public Coord(int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	
	// Set parent for finding path after goal is found
	public void setParent(Coord parent) { this.parent = parent; }
	
	// Get parent for finding path after goal is found
	public Coord getParent() { return parent; }
	
	// Find relative coordinates to be traveled to in search
	public Coord up() { return new Coord(x, y + 1, distance + 1); }
	public Coord down() { return new Coord(x, y - 1, distance + 1); }
	public Coord left() { return new Coord(x - 1, y, distance + 1); }
	public Coord right() { return new Coord(x + 1, y, distance + 1); }
	
	// Check if a coordinate is valid
	public Boolean isValid(World world, ArrayList<Coord> searched) {
		
		int width = world.getWidth();
		int height = world.getHeight();
		
		Boolean alreadySearched = false;
		for (int i = 0; i < searched.size(); i++) {
			if (this.equals(searched.get(i))) {
				alreadySearched = true;
			}
		}
		
		return (this.x >= 0) && (this.x < width)
				&& (this.y >= 0) && (this.y < height)
				&& (!world.isBlocked(this.x, this.y))
				&& (!alreadySearched);
	}
	
	// increment distance for search calculation
	public void iterateDist() {
		this.distance++;
	}
	
	// Check equals state
	public boolean equals(Coord other) { return (this.x == other.x) && (this.y == other.y); }
	
	public String toString() {
		return this.x + ", " + this.y + "(" + this.distance + ")";
	}
}

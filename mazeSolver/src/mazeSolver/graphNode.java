package mazeSolver;

import java.util.ArrayList;

public class graphNode {

	private int rocketLocation;
	private int luckyLocation;
	private boolean visited;
    private ArrayList<graphNode> parents;
	
	public ArrayList<graphNode> getParents() {
		return parents;
	}
	public void setParents(ArrayList<graphNode> parents) {
		this.parents = parents;
	}
	public void addParent(graphNode node)
	{
		this.parents.add(node);
	}
	public graphNode(int x, int y)
	{
		this.rocketLocation = x;
		this.luckyLocation = y;
		this.visited = false;
		this.parents =  new ArrayList<graphNode> ();
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public int getRocketLocation() {
		return rocketLocation;
	}
	public void setRocketLocation(int rocketLocation) {
		this.rocketLocation = rocketLocation;
	}
	public int getLuckyLocation() {
		return luckyLocation;
	}
	public void setLuckyLocation(int luckyLocation) {
		this.luckyLocation = luckyLocation;
	}
}

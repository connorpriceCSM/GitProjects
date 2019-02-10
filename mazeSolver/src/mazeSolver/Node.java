package mazeSolver;

public class Node {

	private int number; // 1-28
	private String color; // will be R,G,Y, B, or blank (exit only)
	private String[] paths; // will be a char array of 28 spots to represent links to all the possible nodes. N will mean no link is possible and R,Y,G,B will represent a link and color path.

	public Node(int _number,  String _color, String[] _neighbors)
	{
		this.number = _number;
		this.color = _color;
		this.paths = _neighbors;

	}
	
	public Node() {}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String[] getNeighbors() {
		return paths;
	}

	public void setNeighbors(String[] neighbors) {
		this.paths = neighbors;
	}
	
	public void setNeighbor(int endNode, String color)
	{
		paths[endNode -1] = color;
	}
	public static void displayNode(Node testNode)
	{
		System.out.println("This node's number is " + testNode.getNumber());
		System.out.println("This node's color is " + testNode.getColor());
		System.out.println();
		for( int i = 0; i < testNode.paths.length; i++)
		{	
			if(testNode.paths[i] == "")
			System.out.println("This node does not connect to node " + (i+1));
			else
				System.out.println("This node connects to node " + (i+1) + " in a " + testNode.paths[i] + " path.");
		}
	}
	public static void main(String[] args)
	{
		int three = 3;
		String color = "Green";
		String[] neighbors = new String[6];
		neighbors[0] = "";
		neighbors[1] = "Red";
		neighbors[2] = "";
		neighbors[3] = "";
		neighbors[4] = "Yellow";
		neighbors[5] = "Blue";
		
		Node testNode = new Node(three, color, neighbors);
		testNode.setNeighbor(4, "Purple");
				
		displayNode(testNode);		
	}	
}
public class Node {

  private int x;
  private int y;
  
  public Node(int _x, int _y)
  {
	   this.x = _x;
	   this.y = _y;
	  
  }
  public Node() {}

public int getX() {
	return x;
}

public void setX(int x) {
	this.x = x;
}

public int getY() {
	return y;
}

public void setY(int y) {
	this.y = y;
}
  
  public double distanceBetweenNodes(Node node)
  {
	
	        int x = Math.abs(this.getX() - node.getX());
	        int y = Math.abs(this.getY() - node.getY());
	        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }
  
  public static void main(String[] args) 
	{
		System.out.println("This is working.");

	}
}

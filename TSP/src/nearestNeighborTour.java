import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class nearestNeighborTour {

	public ArrayList<Node> nodes;
	public ArrayList<Node> shortestTour;
	public int pointsNum;
	public double distance;
	
 public nearestNeighborTour(String fileName) throws FileNotFoundException
 {
	 Scanner readFile = new Scanner(new File(fileName));
	 pointsNum = Integer.parseInt(readFile.next());
	 nodes =  new ArrayList<Node>();
	 while(readFile.hasNext())
	 {
		 int x = Integer.parseInt(readFile.next());
		 int y = Integer.parseInt(readFile.next());
		 Node point = new Node(x,y);
		 this.nodes.add(point);
	 }
	 readFile.close();
 }
 
	public void getShortestTour( ArrayList<Node> nodes)
	{
		double shortestDistance;
		double currentDistance;
		Node shortestNode = new Node();
		ArrayList<Node> theTour =  new ArrayList<Node>();
		
		Node startingNode = nodes.get(0);
		theTour.add(startingNode);
		Node currentNode = startingNode;
		nodes.remove(startingNode);
		
		while(!(nodes.isEmpty()))
				{
				 shortestDistance = currentNode.distanceBetweenNodes(nodes.get(0));
				 shortestNode = nodes.get(0);
			
				 for(int j = 0; j < nodes.size(); j++)
				 {
					 currentDistance = currentNode.distanceBetweenNodes(nodes.get(j));
					 if(currentDistance < shortestDistance)
					 {
						 shortestDistance = currentDistance;
						 shortestNode = nodes.get(j);
					 } 
				 }
				 distance += shortestDistance;
				 nodes.remove(shortestNode);
				 theTour.add(shortestNode);
				 currentNode = shortestNode;
				}
		
		theTour.add(startingNode);
		distance += theTour.get(theTour.size()-2).distanceBetweenNodes(startingNode);
		shortestTour = theTour;
		
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Node> getShortestTour() {
		return shortestTour;
	}

	public int getPointsNum() {
		return pointsNum;
	}

	public double getDistance() {
		return distance;
	}

	public void displayShortestTour()
	{
		System.out.println("The shortest tour for the tested points is as follows:");
		for(int i = 0; i < shortestTour.size(); i++)
		{
			System.out.print("(" + shortestTour.get(i).getX()  + "," + shortestTour.get(i).getY() + ")");
			System.out.print(",");
		}
		System.out.println("");
		System.out.println("The distance of this tour is " + distance);
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		nearestNeighborTour test = new nearestNeighborTour("TestNodes.txt");
		long startTime = System.nanoTime();
		test.getShortestTour(test.getNodes());
		long endTime = System.nanoTime();
		test.displayShortestTour();
		System.out.println("It took " + (endTime - startTime) + " nanoseconds to finish");
		

	}
	
	public ArrayList<Node> getRandomNodes(int n)
	{
		ArrayList<Node> randomizedNodes  = new ArrayList <Node>();
		for(int i = 0; i < n; i++)
		{
			int x = (int) (Math.random() * 50);
			int y = (int) (Math.random() * 50);
			randomizedNodes.add(new Node(x,y));
		}
		return randomizedNodes;
	}
	
}

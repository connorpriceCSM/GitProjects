import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.awt.geom.Point2D;

public class exhaustiveSearchTour {

	public Node[] array_nodes;
	public ArrayList <ArrayList<Node>> nodes;
	public ArrayList<Node> shortestTour;
	public int pointsNum;
	public double distance;
	public Permutation permutation;
	
 public exhaustiveSearchTour(String fileName) throws FileNotFoundException
 {
	 Scanner readFile = new Scanner(new File(fileName));
	 pointsNum = Integer.parseInt(readFile.next());
	 array_nodes =  new Node[pointsNum];
	 int i = 0;
	 while(readFile.hasNext())
	 {
		 int x = Integer.parseInt(readFile.next());
		 int y = Integer.parseInt(readFile.next());
		 Node point = new Node(x,y);
		 array_nodes[i] = point;
		 i++;
	 }
	 readFile.close();
	 permutation = new Permutation();
	 nodes = permutation.permute(array_nodes);
 }
 
	public void getShortestTour( ArrayList<ArrayList<Node>> nodes)
	{
		double currentDistance = 0;
		double shortestDistance;
		Node startingNode;
		ArrayList<Node> shortTour = new ArrayList<Node>();
		
			for( int k = 0; k < nodes.get(0).size() - 1; k++)
			{	
				 currentDistance += nodes.get(0).get(k).distanceBetweenNodes(nodes.get(0).get(k+ 1));
			}
			
				startingNode = nodes.get(0).get(0);
				currentDistance += nodes.get(0).get(pointsNum-1).distanceBetweenNodes(startingNode);
				
				shortestDistance = currentDistance;
				shortTour =  nodes.get(0);
				currentDistance = 0;
				
		
				
		for(int i = 0; i < nodes.size(); i++)
		{
			for( int j = 0; j < nodes.get(i).size() - 1; j++)
			{	
				 
				 currentDistance += nodes.get(i).get(j).distanceBetweenNodes(nodes.get(i).get(j+ 1));
			}
			startingNode = nodes.get(i).get(0);
			currentDistance += nodes.get(i).get(pointsNum-1).distanceBetweenNodes(startingNode);
			
			if(currentDistance < shortestDistance)
			 {
				 shortestDistance = currentDistance;
				 shortTour =  nodes.get(i);
				 currentDistance = 0;
			 }
			else
			{
				currentDistance = 0;
			}
		}
		distance = shortestDistance;
		shortTour.add(shortTour.get(0));
		//distance += shortTour.get(shortTour.size()-1).distanceBetweenNodes(shortTour.get(0));
		shortestTour = shortTour;
		
	}
	
	public ArrayList<ArrayList<Node>> getNodes() {
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
		exhaustiveSearchTour test = new exhaustiveSearchTour("TestNodes.txt");
		long startTime = System.nanoTime();
		test.getShortestTour(test.getNodes());
		long endTime = System.nanoTime();
		test.displayShortestTour();
		System.out.println("It took " + (endTime - startTime) + " nanoseconds to finish");

	}
	
	public ArrayList<ArrayList<Node>> getRandomNodes(int n)
	{
		Permutation p =  new Permutation();
		Node[]  randomizedNodes  = new Node[n];
		for(int i = 0; i < n; i++)
		{
			int x = (int) (Math.random() * 50);
			int y = (int) (Math.random() * 50);
			randomizedNodes[i] =(new Node(x,y));
		}
		return p.permute(randomizedNodes);
	}
}

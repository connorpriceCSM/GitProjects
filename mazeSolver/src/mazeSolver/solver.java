package mazeSolver;

import java.io.File;

import java.io.FileNotFoundException;
import javafx.util.Pair;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.io.*; 
import java.util.*; 
public class solver {

	private  graphNode[][] combinations; // 2D matrix to keep track of all 784 possible node combinations
	private  LinkedHashMap<graphNode, LinkedList<graphNode>> neighbors; // our grap.
	private Node[] mazeNodes; // array to keep track of regular nodes
	private int rocketLocation; // rocket's Location at any given time
	private int luckyLocation; // lucky's location at any given time.
	private ArrayList<graphNode> missionLog = new ArrayList<graphNode> (); // ArrayList to keep track of output
	private Map<Integer,String> dictionary = new HashMap<Integer,String> (); //  Dictionary to turn node numbers into letters.
	private static final char Lucky = 'L';
	private static final char Rocket = 'R';
	private static int vertices;
	private static int paths;
	private boolean solved = false; // variable that says whether the maze has been solved yet.


public solver (String fileName) throws FileNotFoundException
{
	Scanner readFile = new Scanner(new File(fileName));
	vertices = Integer.parseInt(readFile.next()); // read the number of vertices (28)
	paths =  Integer.parseInt(readFile.next()); // read the number of paths (40)
	mazeNodes = new Node[vertices];
	// populate the mazeNodes except the exit with their respective numbers and colors.
	for( int i = 0; i < vertices - 1; i++)
	{
		String color = readFile.next();
		Node mazeNode = new Node(i+1, color, new String[28]);
		mazeNodes[i] = mazeNode;
	}
	// account for exit node
	mazeNodes[mazeNodes.length-1] = new Node(mazeNodes.length-1, "", new String[28]);
	// Rocket starts at 1. Lucky starts at 2.
	rocketLocation = Integer.parseInt(readFile.next());
	luckyLocation = Integer.parseInt(readFile.next());
	
	// Get the neighbors and respective path colors of all the nodes.
	while(readFile.hasNext())
	{
		int startingNode = Integer.parseInt(readFile.next());
		int finishNode = Integer.parseInt(readFile.next());
		String color = readFile.next();
		
		// add the colored path to the list of paths for each node.
		// note the minus 1 to account for proper java indexing.
		mazeNodes[startingNode -1].setNeighbor(finishNode, color);
	}
	// because of the array index rule. Add one to i and j.
	// Example the node at i = 1, j = 5  really is (2,6)
	combinations = new graphNode[vertices][vertices];
	for( int i = 0; i< vertices; i++)
	{
		for( int j = 0; j < vertices; j++)
		{
			combinations[i][j] = new graphNode(i+1,j +1);
		}
	}
	// populate the graph, the dictionary, then DFS on combinations[0][1] which equates to Node(1,2)
	neighbors = findAllPathNeighbors();
	populateDictionary();
	DFS(combinations[0][1]);
}
 
//find the attached nodes to each of the 784 possible combinations
public LinkedHashMap<graphNode, LinkedList<graphNode>> findAllPathNeighbors()
{
	LinkedHashMap<graphNode, LinkedList<graphNode>> allNeighbors =  new LinkedHashMap<graphNode, LinkedList<graphNode>>();
	for( int i = 0; i< combinations.length; i++)
	{
		for( int j = 0; j < combinations[i].length; j++)
		{
			// set a new key if the currentNode doesn't exist in the graph yet.
			graphNode currentNode = combinations[i][j];
			if(!allNeighbors.containsKey(currentNode))
			{
				allNeighbors.put(currentNode, new LinkedList());
			}
			 // boundary checker
			if(currentNode.getRocketLocation() <= 28 && currentNode.getLuckyLocation() <= 28 )
			{
			// get rocket's node and color
			Node rocketNode = mazeNodes[currentNode.getRocketLocation() -1]; //x
			String rocketColor = rocketNode.getColor();
			
			// get lucky's node and color 
			Node luckyNode = mazeNodes[currentNode.getLuckyLocation() -1]; //y
			String luckyColor = luckyNode.getColor();
			
			// go through the neighbors and add a node to the linked list if it can be reached from
			// currentNode. Add currentNode as a parent of the lucky node.
			for(int k = 0; k < mazeNodes.length; k++)
			{
				String luckyPathColor =luckyNode.getNeighbors()[k];
				if(rocketColor.equals(luckyPathColor))
				{
					allNeighbors.get(currentNode).add(combinations[i][k]);
					combinations[i][k].addParent(currentNode);
				}
				String rocketPathColor =rocketNode.getNeighbors()[k];
				if(luckyColor.equals(rocketPathColor))
				{
					allNeighbors.get(currentNode).add(combinations[k][j]);
					combinations[k][j].addParent(currentNode);
				}
			}
			}
		}
	}
	return allNeighbors;		
}

// DFS function. will call helper
public void DFS(graphNode node)
{
	System.out.println("Rocket starts at " + "Node " + rocketLocation + " (" +(dictionary.get(rocketLocation)) + ")");
	System.out.println("Lucky starts at " + "Node " + luckyLocation + " (" +(dictionary.get(luckyLocation))+ ")");
	this.resetVisited();
	
	DFSHelper(node);
	
	
} 

// DFS helper function to traverse through the graph.
// Once it finds a node combination where either rocket or lucky is in the room, the graph is solved
// and backtracking will begin.
public void DFSHelper(graphNode node)
{
	// mark the first node --> (1,2) as visited
	node.setVisited(true);
	// check if we've solved the maze. If so, begin backtracking
	if(node.getLuckyLocation() == 28 || node.getRocketLocation() == 28)
	{
		solved = true;
		backtrack(node);
		return;
	}
	// the iterator will go through the linked list of the current node and run DFS on them.
	Iterator<graphNode> adjacents = neighbors.get(node).listIterator();
	// DFS Magic!
	while( adjacents.hasNext())
	{
	   graphNode nextNode  = adjacents.next();
	   rocketLocation = nextNode.getRocketLocation();
	   luckyLocation  = nextNode.getLuckyLocation();
	   if(!nextNode.isVisited()) 
	   {
		   DFSHelper(nextNode);
	   }
	}
}

// helper function to backtrack through the graph starting at the solution
// missionLog will be filled. This method assumes  that each node has, at most, 2 parents.
public void backtrack(graphNode node)
{
	// backtrack so long as the log doesn't already contain the node being searched
	// and as long as the node isn't (1,2) where we started from.
   while(node != combinations[0][1] && !missionLog.contains(node))
   {
	   missionLog.add(node);
	   if(!node.getParents().isEmpty())
	   node = node.getParents().get(0);
	   else
	   {	
		   missionLog.remove(node);
		   node = missionLog.get(missionLog.size() -1).getParents().get(1);
	   }
   }
   // reverse missionLog to make it easier to traverse
   missionLog.add(combinations[0][1]);
   Collections.reverse(missionLog);
   printOutput();
   
}
// print our output!
public void printOutput()
{
	 
	 System.out.println();
	 for( int i = 0; i < missionLog.size()-1 ; i++)
	 {
		if(missionLog.get(i).getRocketLocation() == missionLog.get(i+1).getRocketLocation())
		{
			System.out.println(Lucky + " " + missionLog.get(i+1).getLuckyLocation() + " --> Lucky moves to Node " + dictionary.get(missionLog.get(i+1).getLuckyLocation()));
		}
		else if(missionLog.get(i).getLuckyLocation() == missionLog.get(i+1).getLuckyLocation())
		{
			System.out.println(Rocket + " " + missionLog.get(i+1).getRocketLocation() + " --> Rocket moves to Node " + dictionary.get(missionLog.get(i+1).getRocketLocation()));
		}
	 }
}
// method to reset the visited boolean on each node before DFS is run.
public void resetVisited()
{
	for( int i = 0; i< combinations.length; i++)
	{
		for( int j = 0; j < combinations[i].length; j++)
		{
			combinations[i][j].setVisited(false);
		}
	}
}
// populate the dictionary to go from integer to string
public void populateDictionary()
{
	dictionary.put(1, "A");
	dictionary.put(2, "B");
	dictionary.put(3, "C");
	dictionary.put(4, "D");
	dictionary.put(5, "E");
	dictionary.put(6, "F");
	dictionary.put(7, "G");
	dictionary.put(8, "H");
	dictionary.put(9, "I");
	dictionary.put(10, "J");
	dictionary.put(11, "K");
	dictionary.put(12, "L");
	dictionary.put(13, "M");
	dictionary.put(14, "N");
	dictionary.put(15, "O");
	dictionary.put(16, "P");
	dictionary.put(17, "Q");
	dictionary.put(18, "R");
	dictionary.put(19, "S");
	dictionary.put(20, "T");
	dictionary.put(21, "U");
	dictionary.put(22, "V");
	dictionary.put(23, "W");
	dictionary.put(24, "X");
	dictionary.put(25, "Y");
	dictionary.put(26, "Z");
	dictionary.put(27, "AA");
	dictionary.put(28, "GOAL");
	
}

public boolean hasNeighbors(graphNode node)
{
	if(neighbors.get(node).size() > 0)
	{
		return true;
	}
	else
	{
		return false;
	}
}

public LinkedList<graphNode> getNeighbors(graphNode node)
{
	return neighbors.get(node);
	
}

public Node[] getMazeNodes() {
	return mazeNodes;
}


public void setMazeNodes(Node[] mazeNodes) {
	this.mazeNodes = mazeNodes;
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


public ArrayList<graphNode> getMissionLog() {
	return missionLog;
}


public void setMissionLog(ArrayList<graphNode> missionLog) {
	this.missionLog = missionLog;
}


public static int getVertices() {
	return vertices;
}


public static void setVertices(int vertices) {
	solver.vertices = vertices;
}


public static int getPaths() {
	return paths;
}


public static void setPaths(int paths) {
	solver.paths = paths;
}

// How simple.. How beautiful.
public static void main(String[] args) throws FileNotFoundException
{
	solver s =  new solver("testNodes.txt");
	
}


}

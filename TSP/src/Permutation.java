
import java.io.*;
import java.util.*;
 
public class Permutation {
	
	public Permutation() 
	{
		
	}
	
	public ArrayList<ArrayList<Node>> permute(Node[] num) {
		ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
		permute(num, 0, result);
		return result;
	}
	 
	void permute(Node[] num, int start, ArrayList<ArrayList<Node>> result) {
	 
		if (start >= num.length) {
			ArrayList<Node> item = convertArrayToList(num);
			result.add(item);
		}
	 
		for (int j = start; j <= num.length - 1; j++) {
			swap(num, start, j);
			permute(num, start + 1, result);
			swap(num, start, j);
		}
	}
	 
	private ArrayList<Node> convertArrayToList(Node[] num) {
		ArrayList<Node> item = new ArrayList<Node>();
		for (int h = 0; h < num.length; h++) {
			item.add(num[h]);
		}
		return item;
	}
	 
	private void swap(Node[] a, int i, int j) {
		Node temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public  static void main(String[] args)
	{
		Permutation p = new Permutation();
		Node[] n = new Node[5];
		n[0] = new Node (0, 5);
		n[1] = new Node (4, 6);
		n[2] = new Node (3, 1);
		n[3] = new Node (7, 12);
		n[4] = new Node (5, 2);
		
		ArrayList<ArrayList<Node>> nodes =  p.permute(n);
		System.out.println(nodes);
		
	}
}
/*
 * Author: Connor Price
 * Part 1 of Score Trakker
 * 9/28/17
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreTrakker {

	private ArrayList<Student> students;
	
	public ScoreTrakker()
	{
		students =  new ArrayList<Student>();
	}

	public void loadDataFromFile(String fileName) throws FileNotFoundException
	{
		
		FileReader reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
		String tempName = "";
		int tempNum = 0;
		while(in.hasNextLine())
		{
			tempName = in.nextLine();
			tempNum = in.nextInt();
			students.add(new Student(tempName, tempNum));
			//***NOTE*** You MUST MOVE REMEMBER TO MOVE THE LINE AFTER YOU READ THE STUDENT
			if(!(in.hasNextLine()))
			{
				break;
			}
			else
			{
				in.nextLine();
			}

		}
		in.close();

	}
	public void printInOrder()
	{
		Collections.sort(students);
		for(Student student: students)
		{
			System.out.println(student.toString());
		}
	}

	public void processFiles() throws FileNotFoundException
	{
		
			loadDataFromFile("scores.txt");
		    printInOrder();
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		ScoreTrakker trakker = new ScoreTrakker();
		trakker.processFiles();
	}

}

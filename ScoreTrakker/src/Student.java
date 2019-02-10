
public class Student implements Comparable<Student> {

	private String name;
	private int score;


	public Student(String studentName, int studentScore)
	{
		name = studentName;
		score = studentScore;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int studentScore) {
		score = studentScore;
	}
	@Override
	// comparable function compares names
	public int compareTo(Student o) {

		return this.getName().compareTo(o.getName());
	}
	@Override
	public String toString() {
		return getName() + " " + getScore() + " ";
	}



}
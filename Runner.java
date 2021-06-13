//Programmer: Pauline Finlay
//Student No: B00107945
//Date: 20.10.2018
//Assignment
//Runner class to create a runner with getter & setter methods for name, age and category

package AssignmentSD3;

public class Runner {
	// variables for runner name, age and running category as set out in assignment
	// brief
	private String runnerName, runningCategory;
	private int runnerAge;

	// constructor with 3 parameters
	// modified from student class in week 1 labs
	public Runner(String runnerName, int runnerAge, String runningCategory) {
		if (runnerName.equals("") || runningCategory.equals(""))
			throw new EmptyStringException();

		this.runnerName = runnerName;
		this.runnerAge = runnerAge;
		this.runningCategory = runningCategory;
	}

	// getter for runnerAge
	// modified from student class in week 1 labs
	public int getAge() {
		return runnerAge;
	}

	// setter for runnerAge
	// modified from student class in week 1 labs
	public void setAge(int age) {
		this.runnerAge = age;
	}

	// getter for runnerName
	// modified from student class in week 1 labs
	public String getName() {
		return runnerName;
	}

	// setter for runnerName
	// modified from student class in week 1 labs
	public void setName(String name) {
		this.runnerName = name;
	}

	// getter for runningCategory
	// modified from student class in week 1 labs
	public String getCategory() {
		return runningCategory;
	}

	// setter for runningCategory
	// modified from student class in week 1 labs
	public void setCategory(String category) {
		this.runningCategory = category;
	}

	// empty string exception class
	// modified from EmptyStringException class in week 1 labs
	public class EmptyStringException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public EmptyStringException() {
			super("you cannot have empty strings");

		}

	}

	// override String method
	// modified from student class in week 1 labs
	// display each entry on new line with titles Name, age & category & their
	// respective values for each
	@Override
	public String toString() {
		return "\nRunner[Name: " + runnerName + ", Age: " + runnerAge + ", Category: " + runningCategory + "]";
	}

}

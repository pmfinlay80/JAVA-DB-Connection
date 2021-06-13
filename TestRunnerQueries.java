package AssignmentSD3;

import java.sql.SQLException;

public class TestRunnerQueries {
	public static void main(String[] args) {
		try {
			//create an obj of StudentQueries type
			RunnerQueries rq = new RunnerQueries("competition", "root", "Elsief80#1_3");
			
			//display all the students from the db
			System.out.println(rq.getAllRunners());
			
			/*the insert, update and delete statements need to be run once only
			 * so after testing them, you need to comment them out if you run
			 * this application several times*/
			//System.out.println(rq.insertRunner("Aurelia Power", 21, "sprint"));
			//System.out.println(rq.updateRunningCategoryByName("sprint", "Eabha O'Connor"));
			System.out.println(rq.deleteRunnerByName("Frankie O'Connor"));
			
			// re-display the students from the db
			System.out.println(rq.getAllRunners());
			
			//once finished, close connection
			//rq.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
}

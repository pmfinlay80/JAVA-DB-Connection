//Programmer: Pauline Finlay
//Student No: B00107945
//Date: 20.10.2018
//Assignment
//Runner Queries class establishes DB connection using prepared statements for sql queries

package AssignmentSD3;

// import statements for sql components and ArrayList
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RunnerQueries {
	// modified from StudentQueries class in Week4 labs
	private Connection c; // declare connection
	private PreparedStatement s1, s2, s3, s4, s5; // declare prepared statements

	public RunnerQueries(String db, String user, String password) throws SQLException {
		// modified from StudentQueries class in Week4 labs
		// initialise db connection
		c = connectToDB(db, user, password);
		// initialise prepared statements
		// modified from StudentQueries class in Week4 labs
		s1 = c.prepareStatement("SELECT * FROM runner");
		s2 = c.prepareStatement(
				"insert into runner " + "(runnerName, runnerAge, runningCategory )" + "values (?, ?, ?)");
		s3 = c.prepareStatement("update runner set runningCategory = ? where" + " (runnerName = ?)");
		s4 = c.prepareStatement("delete from runner where runnerName = ?");
		s5 = c.prepareStatement("SELECT runnerName FROM runner");
	}

	// establish database connection method
	// modified from StudentQueries class in Week4 labs
	// errors regarding public key retrieval resolved using StackOverflow
	// url:"https://stackoverflow.com/questions/50379839/connnection-java-mysql-public-key-retrieval-is-not-allowed"
	public Connection connectToDB(String db, String user, String password) throws SQLException {
		// specify connection path
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/" + db + "?useSSL=false&allowPublicKeyRetrieval=true", user, password);
	}

	// method to get all registered runners name, age and category from database
	// modified from StudentQueries class in Week4 labs
	public ArrayList<Runner> getAllRunners() throws SQLException {
		ArrayList<Runner> runners = new ArrayList<>();
		ResultSet rs = s1.executeQuery();
		while (rs.next()) {
			String name = rs.getString(2);
			int age = rs.getInt(3);
			String category = rs.getString(4);
			runners.add(new Runner(name, age, category));
		}
		return runners;
	}

	// method to insert new runner into datebase
	// modified from StudentQueries class in Week4 labs
	public int insertRunner(String name, int age, String category) throws SQLException {
		s2.setString(1, name);
		s2.setInt(2, age);
		s2.setString(3, category);
		return s2.executeUpdate();
	}

	// method to update category by runner name
	// modified from StudentQueries class in Week4 labs
	public int updateRunningCategoryByName(String category, String name) throws SQLException {
		s3.setString(1, category);
		s3.setString(2, name);
		return s3.executeUpdate();
	}

	// method to delete runner by name
	// modified from StudentQueries class in Week4 labs
	public int deleteRunnerByName(String name) throws SQLException {
		s4.setString(1, name);
		return s4.executeUpdate();
	}

	// method to find all runners names from database
	// modified from StudentQueries class in Week4 labs
	public ResultSet findName() throws SQLException {
		return s5.executeQuery();
	}

	public void closeConnection() throws SQLException {
		c.close();
	}

}

//Programmer: Pauline Finlay
//Student No: B00107945
//Date: 20.10.2018
//Assignment
//Race Registration Database GUI with main method

package AssignmentSD3;

// import statements
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.*;

import AssignmentSD3.Runner.EmptyStringException;

public class CompetitionApplication extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// the GUI is adapted from my SD2 Credit Union assignment last year which was
	// adapted from our BorderLayout Manager lab example in SD2
	public JFrame window = new JFrame("Race Registration Database"); // create main frame onto which to place the 3
																		// smaller
																		// panels

	public JPanel inputpanel = new JPanel(); // create 3 smaller panels to place on main frame
	public JPanel tabpanel = new JPanel();
	public JPanel panelnorth = new JPanel();

	// declare and initialise header label
	JLabel welcome = new JLabel("Welcome to Race Registration Database"); // welcome label
	// welcome.setFont(new Font ("TimesRoman", Font.BOLD + Font.ITALIC, 24)); //set
	// font type, size & effects

	// declare and initialise buttons
	JButton view = new JButton("View All Registered Runners"); // button to display all runners
	JButton updatecat = new JButton("Update Running Category"); // button to update category
	JButton addr = new JButton("Add New Runner"); // button to add new runner
	JButton delr = new JButton("Remove Runner"); // button to delete runner
	JButton close = new JButton("Close");

	// declare and initialise testareas and panels fro TabbedPane
	JTextArea ta1 = new JTextArea(20, 50); // panels and textareas for TabbedPane
	JPanel p1 = new JPanel();
	JTextArea ta2 = new JTextArea(20, 50);
	JPanel p2 = new JPanel();
	JTextArea ta3 = new JTextArea(20, 50);
	JPanel p3 = new JPanel();
	JTextArea ta4 = new JTextArea(20, 50);
	JPanel p4 = new JPanel();

	public CompetitionApplication() // GUI constructor
	{
		window.setLayout(new BorderLayout(5, 5)); // set Border Layout on main frame
		window.add(inputpanel, BorderLayout.SOUTH); // insert panels on the frame
		window.add(tabpanel, BorderLayout.WEST);
		window.add(panelnorth, BorderLayout.NORTH);

		inputpanel.setVisible(true); // make panels visible
		tabpanel.setVisible(true);
		panelnorth.setVisible(true);

		panelnorth.add(welcome); // add welcome label to north panel

		inputpanel.add(close); // add input panel to south panel
		close.addActionListener(this);

		// modified using a combination of the TabbedPane and TextArea Demos from Week2
		// labs
		JTabbedPane tp = new JTabbedPane(); // create TabbedPane
		tabpanel.add(tp); // add TabbedPane to the panel in west panel
		p1.add(ta1); // add textArea to panel 1
		p1.add(view); // add view button to panel 1
		view.addActionListener(this); // add action listener to view button

		p2.add(ta2); // add textArea to panel 2
		p2.add(updatecat); // add update button to panel 2
		updatecat.addActionListener(this); // add action listener

		p3.add(ta3); // add textArea to panel 3
		p3.add(addr); // add add button to panel 3
		addr.addActionListener(this); // add action listener

		p4.add(ta4); // add textArea to panel 4
		p4.add(delr); // add delete button to panel 4
		delr.addActionListener(this); // add action listener

		tp.add("Registered Runners", p1); // add tab names & panels to TabbedPane
		tp.add("Amend Race Category", p2);
		tp.add("Insert New Runner", p3);
		tp.add("Remove Runner", p4);

		window.setSize(850, 500); // set main frame size
		window.setVisible(true); // set visible
	}

	public static void main(String[] args) { // main method
		new CompetitionApplication(); // new application
	}

	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //

	public void actionPerformed(ActionEvent e) {
		// modified using the Student GUI from week 3 labs
		if (e.getSource() == view) // action on pressing view button
		{
			ta1.setText("");
			try {
				// connect to database
				RunnerQueries rq = new RunnerQueries("competition", "root", "Elsief80#1_3");
				// display message to user
				JOptionPane.showMessageDialog(window,
						"Displaying all Registered Runners Names, Ages and Race Category");
				// execute statement to retrieve all runner details from database & display in
				// text area
				// using RunnerQueries prepared statement
				// amended from TestStudentQueries in Week4 labs
				ta1.setText(rq.getAllRunners().toString());

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //

		if (e.getSource() == updatecat) // action on pressing update button
		{
			ta2.setText("");
			try {
				// connect to database using method in RunnerQueries
				RunnerQueries rq = new RunnerQueries("competition", "root", "Elsief80#1_3");
				// display message to user
				JOptionPane.showMessageDialog(window, "Enter Runner Name and new Race Category");
				// open input dialog to take Runner Name
				String name = JOptionPane.showInputDialog(window, "Enter Runner Name");
				// error message if left blank
				while (name.equals("")) {
					name = JOptionPane.showInputDialog(window, "You must enter Runner name", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				// execute prepared statement to find all runners names
				ResultSet rs = rq.findName();
				while (rs.next()) {
					String dbname = rs.getString("runnerName");
					if (!name.equalsIgnoreCase(dbname)) {
						JOptionPane.showMessageDialog(window, "Searching for Matching Entry - No Match Found");
					}
					if (name.equalsIgnoreCase(dbname)) {
						// display message to user
						JOptionPane.showMessageDialog(window, "Match Found");
						// uses Java api showOptionDialog
						// JOption OptionDialog amended using
						// https://www.mkyong.com/swing/java-swing-joptionpane-showoptiondialog-example/
						String[] options = { "Long Distance", "Middle Distance", "Sprint", "Hurdles", "Fun Run" }; // options
																													// for
																													// run
																													// categories
						// OptionDialog returns an int value
						int x = JOptionPane.showOptionDialog(window, "Click on category option",
								"Enter Running Category", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
								null, options, options[0]);
						// pass the selected int value the corresponding string value for DB
						if (x == 0) {
							rq.updateRunningCategoryByName("Long Distance", name);
						} else if (x == 1) {
							rq.updateRunningCategoryByName("Middle Distance", name);
						} else if (x == 2) {
							rq.updateRunningCategoryByName("Sprint", name);
						} else if (x == 3) {
							rq.updateRunningCategoryByName("Hurdles", name);
						} else if (x == 4) {
							rq.updateRunningCategoryByName("Fun Run", name);
						}

						JOptionPane.showMessageDialog(window, "Update Successful");
					}

					ta2.setText(rq.getAllRunners().toString());
					// error message if no match found
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //

		if (e.getSource() == addr) // action on pressing add button
		{
			ta3.setText("");
			try {
				// connect to database
				RunnerQueries rq = new RunnerQueries("competition", "root", "Elsief80#1_3");
				System.out.println("Connected"); // print to console to confirm database connection
				// display message to user
				JOptionPane.showMessageDialog(window, "Enter new Runners Name, Age and Race Category");
				// open input pane to take runner name
				String name = JOptionPane.showInputDialog(window, "Enter Runner Name");
				// error message if left blank
				while (name.equals("")) {
					name = JOptionPane.showInputDialog(window, "You must enter Runner name", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				// open input pane to take runner age
				String val = JOptionPane.showInputDialog(window, "Enter Runner Age");
				// error message if age not valid age 1-129
				// used by modifying https://www.regular-expressions.info/numericranges.html
				if (!Pattern.matches("^([1-9]|[1-9][0-9]|1[0-2][0-9])$", val)) {
					val = JOptionPane.showInputDialog(window, "Re-enter valid age", "Error", JOptionPane.ERROR_MESSAGE);
				}
				int age = Integer.parseInt(val);
				// open input pane to take race category
				// uses Java api showOptionDialog
				// JOption OptionDialog amended using
				// https://www.mkyong.com/swing/java-swing-joptionpane-showoptiondialog-example/
				// options for run categories
				String[] options = { "Long Distance", "Middle Distance", "Sprint", "Hurdles", "Fun Run" };
				// OptionDialog returns an int value
				int x = JOptionPane.showOptionDialog(window, "Click on category option", "Enter Running Category",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				// pass the selected int value the corresponding string value for DB
				if (x == 0) {
					String cat = "Long Distance";
					// execute prepared statement to add new runner
					// amended from TestStudentQueries in Week4 labs
					rq.insertRunner(name, age, cat);
				} else if (x == 1) {
					String cat = "Middle Distance";
					// execute prepared statement to add new runner
					// amended from TestStudentQueries in Week4 labs
					rq.insertRunner(name, age, cat);
				} else if (x == 2) {
					String cat = "Sprint";
					// execute prepared statement to add new runner
					// amended from TestStudentQueries in Week4 labs
					rq.insertRunner(name, age, cat);
				} else if (x == 3) {
					String cat = "Hurdles";
					// execute prepared statement to add new runner
					// amended from TestStudentQueries in Week4 labs
					rq.insertRunner(name, age, cat);
				} else if (x == 4) {
					String cat = "Fun Run";
					// execute prepared statement to add new runner
					// amended from TestStudentQueries in Week4 labs
					rq.insertRunner(name, age, cat);
				}

				// show confirmation message to user
				JOptionPane.showMessageDialog(window, "Registration Successful");
				// display all runners in textarea using RunnerQueries prepared statement
				// amended from TestStudentQueries in Week4 labs
				ta3.setText(rq.getAllRunners().toString());
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			// end try/catch
		}

		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //

		if (e.getSource() == delr) // action on pressing delete button
		{
			ta4.setText("");
			try {
				// connect to database
				RunnerQueries rq = new RunnerQueries("competition", "root", "Elsief80#1_3"); // open DB connection
				System.out.println("Connected"); // print to console to confirm database connection

				// display message to user
				JOptionPane.showMessageDialog(window, "Enter Runner Name to cancel registration");
				// open input dialog to enter runner name
				String name = JOptionPane.showInputDialog(window, "Enter Runner Name");

				// error if name left blank
				while (name.equals("")) {
					JOptionPane.showMessageDialog(window, "You must enter Runner name", "Error",
							JOptionPane.ERROR_MESSAGE);
					name = JOptionPane.showInputDialog(window, "Enter Runner Name");
				}

				// execute prepared statement to find all runners names
				ResultSet rs = rq.findName();
				while (rs.next()) {
					String dbname = rs.getString("runnerName");
					if (!name.equalsIgnoreCase(dbname)) {
						JOptionPane.showMessageDialog(window, "Searching for Matching Entry - No Match Found");
					}
					if (name.equalsIgnoreCase(dbname)) {
						// execute prepared statement to remove runner
						// amended from TestStudentQueries in Week4 labs
						rq.deleteRunnerByName(name);
						// display message to user
						JOptionPane.showMessageDialog(window, "Match Found-Removal Successful");
					}
					// display runners in textarea using RunnerQueries prepared statement
					// amended from TestStudentQueries in Week4 labs
					ta4.setText(rq.getAllRunners().toString());
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try/catch
		}

		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * //

		if (e.getSource() == close) // action on pressing close button
		{
			// display message to user and close application
			JOptionPane.showMessageDialog(window, "Thank You for using Race Registration Database - Goodbye!");
			System.exit(0);
		}
	}

}

//this class is part of the package, n.gui
package n.gui;

//import the following libraries for use within this class
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import n.db.DataSource;
import n.models.Referee;

/*the following class extends the existing JPanel class for use within
 * the Matches class and is used to display the relevant tab at the 
 * top of the gui and when clicked on by the user the relevant information */
public class Referees extends JPanel {
	
	
	/*declares the instance variables for this class as final (as they will not change), 
	 * which is the tab name that is displayed 
	 * at the top of the Gui and the versionID */
	public static final String TAB_NAME = "Referees";
	private static final long serialVersionUID = 1L;
	
	//an instance of DataSource and JTable (to display list of referees) will also be used within this class
	DataSource dataSource = null;
	JTable table = null; 
	
	/* the main constructor for the Referee class, a DataSource object is passed as a parameter into this class,
	 * which is then used to initialise the instance of the DataSource object within this class*/
	public Referees(DataSource fileStore) {
		this.dataSource = fileStore;
		
		//set the layout for the Referee tab to BorderLayout
		this.setLayout(new BorderLayout());
		
		//instantiate and initialise the table variable as a JTable
		table = new JTable();
		
		//call on the refreshTableData method within this class to populate the table with Referee object information
		refreshTableData();
		
		//instantiate a JScrollPane and add this to the table within the Referees tab on the gui
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	/*this table creates a DefaultTableModel to allow us to get the refereeTable and put this onto the model 
	 * for the table, once the model is created we  then add this to the table variable which would displays 
	 * the referee information in separated columns on the gui */
	public void refreshTableData() {
		DefaultTableModel model = new DefaultTableModel(
			dataSource.getRefereesData(),Referee.FIELD_NAMES);
		table.setModel(model);
	}


}

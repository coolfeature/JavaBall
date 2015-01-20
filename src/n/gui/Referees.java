package n.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import n.db.DataSource;
import n.models.Referee;

/**
 * This class draws a table populated with referees from the DataSource
 * instance variable.
 */
public class Referees extends JPanel {
	
	/*declares the instance variables for this class as final (as they will not change), 
	 * which is the serialVersionUID; and the tab name that is displayed at the top of the Gui */
	private static final long serialVersionUID = 1L;
	public static final String TAB_NAME = "Referees";
	
	//an instance of DataSource and JTable (to display list of referees) will also be used within this class
	DataSource dataSource = null;
	JTable table = null;
	
	/**
	 * The class takes the DataSource reference in the constructor.
	 * @param dataStore
	 */
	public Referees(DataSource dataStore) {
		this.dataSource = dataStore;
		
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
	
	/**
	 * This method forces the table object to update itself with respect to 
	 * data it is displaying.
	 */
	public void refreshTableData() {
		DefaultTableModel model = new DefaultTableModel(
			dataSource.getRefereesData(),Referee.FIELD_NAMES);
		table.setModel(model);
	}
}

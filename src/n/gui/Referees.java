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
	
	private static final long serialVersionUID = 1L;
	public static final String TAB_NAME = "Referees";
	DataSource dataSource = null;
	JTable table = null;
	
	/**
	 * The class takes the DataSource reference in the constructor.
	 * @param dataStore
	 */
	public Referees(DataSource dataStore) {
		this.dataSource = dataStore;
		this.setLayout(new BorderLayout());
		table = new JTable();
		refreshTableData();
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

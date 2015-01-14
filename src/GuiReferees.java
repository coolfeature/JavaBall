import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class GuiReferees extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final String TAB_NAME = "Referees";
	DataSource fileStore = null;
	JTable table = null;
			
	public GuiReferees(DataSource fileStore) {
		this.fileStore = fileStore;
		this.setLayout(new BorderLayout());
		table = new JTable();
		refreshTableData();
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	public void refreshTableData() {
		DefaultTableModel model = new DefaultTableModel(
			fileStore.getRefereesData(),Referee.FIELD_NAMES);
		table.setModel(model);
	}


}

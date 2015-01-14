package n.gui;
import javax.swing.JPanel;

import n.db.DataSource;


public class Allocation extends JPanel {
	
	public static final String TAB_NAME = "Allocation";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public Allocation(DataSource fileStore) {
		this.fileStore = fileStore;
	}

}

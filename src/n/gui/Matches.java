package n.gui;
import javax.swing.JPanel;

import n.db.DataSource;


public class Matches extends JPanel {
	
	public static final String TAB_NAME = "Matches";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public Matches(DataSource fileStore) {
		this.fileStore = fileStore;
	}

}

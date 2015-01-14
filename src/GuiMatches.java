import javax.swing.JPanel;


public class GuiMatches extends JPanel {
	
	public static final String TAB_NAME = "Matches";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public GuiMatches(DataSource fileStore) {
		this.fileStore = fileStore;
	}

}

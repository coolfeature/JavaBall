import javax.swing.JPanel;


public class GuiAllocation extends JPanel {
	
	public static final String TAB_NAME = "Allocation";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public GuiAllocation(DataSource fileStore) {
		this.fileStore = fileStore;
	}

}

import javax.swing.JPanel;


public class GuiChart extends JPanel {

	public static final String TAB_NAME = "Chart";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public GuiChart(DataSource fileStore) {
		this.fileStore = fileStore;
	}

	
}

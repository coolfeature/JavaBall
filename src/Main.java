import n.db.DataSource;
import n.gui.Container;

public class Main {
	public static void main(String[] args) {
		DataSource fileStore = new DataSource();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Container gui = new Container(fileStore);
                gui.setVisible(true);
            }
        });
	}
}

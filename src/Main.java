

public class Main {
	public static void main(String[] args) {
		DataSource fileStore = new DataSource();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui gui = new Gui(fileStore);
                gui.setVisible(true);
            }
        });
	}
}

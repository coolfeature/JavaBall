
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Gui extends JFrame {

	public static final String APP_NAME = "JavaBall";
	private static final long serialVersionUID = 1L;
	DataSource fileStore = null;
	
	public Gui(DataSource fileStore) {
		this.setLocation(200, 200);
		this.setSize(600, 500);
		this.setTitle(APP_NAME);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.fileStore = fileStore;
		this.layoutComponents();
	}

	private void layoutComponents() {
		JPanel container = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		GuiReferees refereesTab = new GuiReferees(fileStore);
		tabbedPane.addTab(GuiReferees.TAB_NAME, refereesTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		GuiSearch searchTab = new GuiSearch(fileStore,refereesTab);
		tabbedPane.addTab(GuiSearch.TAB_NAME, searchTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		GuiChart chartTab = new GuiChart(fileStore);
		tabbedPane.addTab(GuiChart.TAB_NAME, chartTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		GuiAllocation allocationTab = new GuiAllocation(fileStore);
		tabbedPane.addTab(GuiAllocation.TAB_NAME, allocationTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);
		
		GuiMatches matchesTab = new GuiMatches(fileStore);
		tabbedPane.addTab(GuiMatches.TAB_NAME, matchesTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_5);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		container.add(btnExit,BorderLayout.SOUTH);
		container.add(tabbedPane);
		this.add(container);
	}
}

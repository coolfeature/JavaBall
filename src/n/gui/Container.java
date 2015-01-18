package n.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import n.db.DataSource;

/**
 * The main container displays the main TabbedPane and an Exist button for the
 * program.
 */
public class Container extends JFrame {

	public static final String APP_NAME = "JavaBall";
	private static final long serialVersionUID = 1L;
	DataSource dataSource = null;
	
	public Container(DataSource fileStore) {
		this.setLocation(200, 200);
		this.setSize(777,544);
		this.setTitle(APP_NAME);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.dataSource = fileStore;
		this.layoutComponents();
	}
	
	/**
	 * This method instantiates the main TabbedPane object and its members.
	 * The container uses BorderLayout for the components layout.
	 */
	private void layoutComponents() {
		JPanel container = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		Referees refereesTab = new Referees(dataSource);
		tabbedPane.addTab(Referees.TAB_NAME, refereesTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		Manage searchTab = new Manage(dataSource,refereesTab);
		tabbedPane.addTab(Manage.TAB_NAME, searchTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		Chart chartTab = new Chart(dataSource);
		tabbedPane.addTab(Chart.TAB_NAME, chartTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		Report reportTab = new Report(dataSource);
		
		Matches allocationTab = new Matches(dataSource,refereesTab,reportTab);
		tabbedPane.addTab(Matches.TAB_NAME, allocationTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);
		
		tabbedPane.addTab(Report.TAB_NAME, reportTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_5);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataSource.writeReferees();
				dataSource.writeReport();
				System.exit(0);
			}
		});
		container.add(btnExit,BorderLayout.SOUTH);
		container.add(tabbedPane);
		this.add(container);
	}
}

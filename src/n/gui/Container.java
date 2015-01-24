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

	//constant instance variables of the Container class
	public static final String APP_NAME = "JavaBall";		//Name of application
	private static final long serialVersionUID = 1L;
	
	//DataSource object instance which will be used in this class
	DataSource dataSource = null;
	
	/**
	 * Constructor
	 * @param fileStore
	 */
	public Container(DataSource fileStore) {
		
		//sets the location, size and title of the JFrame window
		this.setLocation(200, 200);
		this.setSize(777,544);
		this.setTitle(APP_NAME);
		
		//when the x button on the top right of window is pressed, program should close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//assign the dataSource object local to this class to the object passed in as a parameter
		this.dataSource = fileStore;
		this.layoutComponents();
	}
	
	/**
	 * This method instantiates the main TabbedPane object and its members.
	 * The container uses BorderLayout for the components layout.
	 */
	private void layoutComponents() {
		
		//container and tabbed pane JComponents
		JPanel container = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		//Referees tab which will display the information from the Referees class
		Referees refereesTab = new Referees(dataSource);
		tabbedPane.addTab(Referees.TAB_NAME, refereesTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		//Manage tab which will display the information from the Manage class
		Manage searchTab = new Manage(dataSource,refereesTab);
		tabbedPane.addTab(Manage.TAB_NAME, searchTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		//Chart tab which will display the information from Chart class
		Chart chartTab = new Chart(dataSource);
		tabbedPane.addTab(Chart.TAB_NAME, chartTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		//Report tab which instantiates and displays the information from the Report class
		Report reportTab = new Report(dataSource);
		
		//Allocation tab which instantiates and will display information from the Matches tab
		Matches allocationTab = new Matches(dataSource,refereesTab,reportTab);
		tabbedPane.addTab(Matches.TAB_NAME, allocationTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);
		tabbedPane.addTab(Report.TAB_NAME, reportTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_5);
		
		//Exit button with action listener
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			
			//actionPerformed method that will write data to referees out and report text file and terminate
			public void actionPerformed(ActionEvent arg0) {
				dataSource.writeReferees();
				dataSource.writeReport();
				System.exit(0);
			}
		});
		
		//add the exit button and tabbed pane to the gui
		container.add(btnExit,BorderLayout.SOUTH);
		container.add(tabbedPane);
		this.add(container);
	}
}

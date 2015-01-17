//this class is part of the package, n.gui
package n.gui;

//import the following libraries for use within this class
import javax.swing.JPanel;

import n.db.DataSource;

/*the following class extends the existing JPanel class for use within
 * the Chart class and is used to display the relevant tab at the 
 * top of the gui and when clicked on by the user the relevant information */

public class Chart extends JPanel {

	/*declares the instance variables for this class as final (as they will not change), 
	 * which is the tab name that is displayed 
	 * at the top of the Gui and the versionID */
	public static final String TAB_NAME = "Chart";
	private static final long serialVersionUID = 1L;
	
	//an instance of DataSource will also be used within this class
	DataSource dataSource;
	
	/* the main constructor for the Chart class, a DataSource object is passed as a parameter into this class,
	 * which is then used to initialise the instance of the DataSource object within this class*/
	public Chart(DataSource fileStore) {
		this.dataSource = fileStore;
	}

	
}

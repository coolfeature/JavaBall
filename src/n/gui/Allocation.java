//this class is part of the package, n.gui
package n.gui;

//import the following libraries for use within this class
import javax.swing.JPanel;

import n.db.DataSource;

/*the following class extends the existing JPanel class for use within
 * the Allocation class and is used to display the relevant tab at the 
 * top of the gui and when clicked on by the user the relevant information */
public class Allocation extends JPanel {

	/*declares the instance variables for this class as final (as they will not change), 
	 * which is the tab name that is displayed 
	 * at the top of the Gui and the versionID */
	public static final String TAB_NAME = "Allocation";
	private static final long serialVersionUID = 1L;

	//an instance of DataSource will also be used within this class
	DataSource fileStore;

	/* the main constructor for the Allocation class, a DataSource object is passed as a parameter into this class,
	 * which is then used to initialise the instance of the DataSource object within this class*/
	public Allocation(DataSource fileStore) {
		this.fileStore = fileStore;
	}

}

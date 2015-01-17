//this class is part of the package, n.gui
package n.gui;

//import the following libraries for use within this class
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import n.db.DataSource;
import n.models.Match;
import n.models.Referee;
import n.models.South;

/*the following class extends the existing JPanel class for use within
 * the Matches class and is used to display the relevant tab at the 
 * top of the gui and when clicked on by the user the relevant information */

public class Matches extends JPanel {
	
	/*declares the instance variables for this class as final (as they will not change), 
	 * which is the tab name that is displayed 
	 * at the top of the Gui and the versionID */
	public static final String TAB_NAME = "Matches";
	private static final long serialVersionUID = 1L;
	
	//an instance of DataSource will also be used within this class
	DataSource fileStore;
	
	/* the main constructor for the Chart class, a DataSource object is passed as a parameter into this class,
	 * which is then used to initialise the instance of the DataSource object within this class*/
	public Matches(DataSource fileStore) {
		this.fileStore = fileStore;
		
		//set the layout of this tab as a borderLayout
		this.setLayout(new BorderLayout());
		
		//create a new button named 'Candidates for use within this class
		JButton btnGetCandidates = new JButton("Candidates");
		
		//add an actionLister to the button so there is a secondary step when the button is pressed
		btnGetCandidates.addActionListener(new ActionListener() {

			//perform the following if there is any user interaction with the gui
			public void actionPerformed(ActionEvent e) {
				
				/*create a new Referee's array using the existing referees that are stored in DataSource 
				 * object filestore, these will be potential candidate referees that can be allocated to a match */
				Referee[] candidates = fileStore.getReferees();
				
				//create another Referees array that will stores the selected referees against matches
				Referee[] selected = new Match((short)2,new South(),"Senior").allocateReferees(candidates);
				
				//then loop through the 'selected' array and print this to the console
				for (Referee r : selected) {
					System.out.println(r.toString());
				}
			}
			
		});
		
		//add the Candidate button to the gui
		this.add(btnGetCandidates, BorderLayout.NORTH);
		//set the preferred size of the tab dimensions to 300x300
		this.setPreferredSize(new Dimension(300, 300));
		
	}

}

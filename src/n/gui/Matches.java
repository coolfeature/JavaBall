package n.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import n.db.DataSource;
import n.models.Central;
import n.models.Match;
import n.models.North;
import n.models.Referee;
import n.models.South;


public class Matches extends JPanel {
	
	public static final String TAB_NAME = "Matches";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public Matches(DataSource fileStore) {
		this.fileStore = fileStore;
		this.setLayout(new BorderLayout());
		JButton btnGetCandidates = new JButton("Candidates");
		btnGetCandidates.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Referee[] candidates = fileStore.getReferees();
				Referee[] selected = new Match((short)2,new North(),"Senior").allocateReferees(candidates);
				System.out.println("---------------- NORTH --------------------");
				for (Referee r : selected) {
					System.out.println(r.toString());
				}
				System.out.println("");

				selected = new Match((short)2,new Central(),"Senior").allocateReferees(candidates);
				System.out.println("---------------- CENTRAL --------------------");
				for (Referee r : selected) {
					System.out.println(r.toString());
				}
				System.out.println("");
				
				selected = new Match((short)2,new South(),"Senior").allocateReferees(candidates);
				System.out.println("---------------- SOUTH --------------------");
				for (Referee r : selected) {
					System.out.println(r.toString());
				}
				System.out.println("");
			}
			
		});
		this.add(btnGetCandidates, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(300, 300));
		
	}

}

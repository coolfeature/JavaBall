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


public class Report extends JPanel {
	
	public static final String TAB_NAME = "Report";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public Report(DataSource fileStore) {
		this.fileStore = fileStore;
		this.setLayout(new BorderLayout());
		JButton btnGetCandidates = new JButton("Candidates");
		btnGetCandidates.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Referee[] candidates = fileStore.getReferees();
				Match m = new Match(2,new North(),"Senior");
				m.setAllocatedReferees(candidates);
				System.out.println("---------------- NORTH --------------------");
				for (Referee r : m.getAllocatedReferees()) {
					System.out.println(r.toString());
				}
				System.out.println("");

				m = new Match(2,new Central(),"Senior");
				m.setAllocatedReferees(candidates);
				System.out.println("---------------- CENTRAL --------------------");
				for (Referee r : m.getAllocatedReferees()) {
					System.out.println(r.toString());
				}
				System.out.println("");
				
				m = new Match(2,new South(),"Senior");
				m.setAllocatedReferees(candidates);
				System.out.println("---------------- SOUTH --------------------");
				for (Referee r : m.getAllocatedReferees()) {
					System.out.println(r.toString());
				}
				System.out.println("");
				
				Match[] ms = fileStore.getMatches();
				if (ms != null) {
					for(Match mt : ms) {
						System.out.println(mt.toString());
					}	
				}
				
			}
			
		});
		this.add(btnGetCandidates, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(300, 300));
		
	}
	
	private void layoutComponents() {
		
	}

}

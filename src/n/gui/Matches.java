package n.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import n.db.DataSource;
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
				List<Referee> candidates = fileStore.getMatchCandidates(new South(), "Senior");
				Collections.sort(candidates, Referee.MatchCandidates);
				for (Referee r : candidates) {
					System.out.println(r.toString());
				}
				
			}
			
		});
		this.add(btnGetCandidates, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(300, 300));
		
	}

}

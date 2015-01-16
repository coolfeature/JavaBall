package n.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import n.db.DataSource;
import n.models.Area;
import n.models.Match;
import n.models.Referee;
import n.models.TravelAreas;

public class Matches extends JPanel {

	public static final String TAB_NAME = "Matches";
	private static final String MATCH_REMOVE = "Remove";
	private static final String MATCH_ALLOCATE = "Allocate";
	private static final long serialVersionUID = 1L;
	
	DataSource dataSource;
	Match match;
	int lastWeek;

	public Matches(DataSource dataSource) {
		this.dataSource = dataSource;
		this.match = new Match();
		this.lastWeek = Match.MIN_MATCHES;
		refresh(Matches.MATCH_ALLOCATE);
	}
	
	private void refresh(String forAction) {
		this.removeAll();
		this.setLayout(new GridLayout(1, 2));
		this.add(drawLeftPanel(forAction));
		this.add(drawRightPanel());
		this.revalidate();
	}
	
	/*
	 * The method draws layout components organised into two vertical panels.
	 */
	private JComponent drawLeftPanel(String forAction) {
		// Left Panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel leftPanelGrid = new JPanel(new GridLayout(3,2));
		
		// -------------------------- WEEK ------------------------------------
		
		leftPanelGrid.add(new JLabel("Week"));
		JPanel weekPanel = new JPanel();
		weekPanel.setLayout(new BorderLayout());
		SpinnerModel weekModel = new SpinnerNumberModel(Match.MIN_MATCHES,
				Match.MIN_MATCHES, Match.MAX_MATCHES, Match.MIN_MATCHES);     
		JSpinner sprWeek = new JSpinner(weekModel);
		sprWeek.setValue(lastWeek);
		weekPanel.add(sprWeek,BorderLayout.EAST);
		leftPanelGrid.add(weekPanel);
		
		// ----------------------- HOME AREAS ---------------------------------
		leftPanelGrid.add(new JLabel("Area"));
		JComboBox<Area> cbHomeAreas = new JComboBox<Area>(TravelAreas.AREAS);
		/*
		 *  The making of the combo box entry editable is temporarily needed
		 *  for the setSelectedItem to take effect. 
		 */
		cbHomeAreas.setEnabled(true);
		cbHomeAreas.setSelectedItem(match.getArea());
		cbHomeAreas.setEnabled(forAction.equals(Matches.MATCH_ALLOCATE));
		System.out.println("cbHomeAreas" + forAction.equals(Matches.MATCH_ALLOCATE));
		cbHomeAreas.setMaximumSize(cbHomeAreas.getPreferredSize());
		leftPanelGrid.add(cbHomeAreas);

		// -------------------------- LEVEL -----------------------------------
		
		leftPanelGrid.add(new JLabel("Group"));
		JPanel panelRadioButtons = new JPanel();
		JRadioButton rbJunior = new JRadioButton();
		rbJunior.setText("Junior");
		rbJunior.setSelected(match.getCategory().equals(Match.JUNIOR) ? true : false);
		rbJunior.setEnabled(forAction.equals(Matches.MATCH_ALLOCATE));
		panelRadioButtons.add(rbJunior);
		JRadioButton rbSenior = new JRadioButton();
		rbSenior.setText("Senior");
		rbSenior.setSelected(match.getCategory().equals(Match.SENIOR) ? true : false);
		rbSenior.setEnabled(forAction.equals(Matches.MATCH_ALLOCATE));
		panelRadioButtons.add(rbSenior);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(rbJunior);
		radioGroup.add(rbSenior);
		
		leftPanelGrid.add(panelRadioButtons);
		leftPanel.add(leftPanelGrid,BorderLayout.NORTH);
		
		// ------------------- SPINNER LISTENER -------------------------------
		sprWeek.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lastWeek = (Integer)sprWeek.getValue();
				match.setWeek(lastWeek);
				Match existing = dataSource.search(match); 
				if (existing == null) {
					refresh(Matches.MATCH_ALLOCATE);
				} else {
					System.out.println("found" + existing.toString());
					match = existing;
					refresh(Matches.MATCH_REMOVE);
				}
			}
		});
		
		// ----------------------- ADD BUTTON ---------------------------------
		JButton btnAddMatch = new JButton(forAction);
		btnAddMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (forAction) {
					case Matches.MATCH_ALLOCATE : 
						match.setArea((Area) cbHomeAreas.getSelectedItem());
						match.setCategory(rbJunior.isSelected() ? Match.JUNIOR : Match.SENIOR );
						match.setWeek((Integer)sprWeek.getValue());
						match.setAllocatedReferees(dataSource.getReferees());
						if (dataSource.addMatch(match)) {
							JOptionPane.showMessageDialog(null, 
								"New Match with allocated referees has been saved.",
								"New Match", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, 
									"There was a error. "
									+ "Check if the match does not exist already.",
									"New Match", JOptionPane.WARNING_MESSAGE);
						}
						refresh(Matches.MATCH_REMOVE);
						break;
					case Matches.MATCH_REMOVE :
						System.out.println("REMOVE!");
					default : break;
				}
			}
		});
		
		leftPanel.add(btnAddMatch,BorderLayout.SOUTH);
		
		return leftPanel;
	}
	
	private JComponent drawRightPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout());
		if (match == null || match.getAllocatedReferees() == null) {
			JPanel noMatchesPanel = new JPanel(new BorderLayout());
			JLabel lblNoResults = new JLabel("No Matches");
			lblNoResults.setHorizontalAlignment(JLabel.CENTER);
			lblNoResults.setVerticalAlignment(JLabel.CENTER);
			noMatchesPanel.add(lblNoResults,BorderLayout.CENTER);
			rightPanel.add(noMatchesPanel);
			return rightPanel;
		}
		DefaultListModel<Referee> lstRefereesModel = new DefaultListModel<Referee>();
		for (Referee referee : match.getAllocatedReferees()) {
			lstRefereesModel.addElement(referee);
		}
		JList<Referee> lstReferees = new JList<Referee>();
		lstReferees.setModel(lstRefereesModel);
		lstReferees.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane spReferees = new JScrollPane(lstReferees);
		rightPanel.add(spReferees,BorderLayout.CENTER);
		return rightPanel;
	}
}

package n.gui;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
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
	public static final String MSG_NOMATCHES = "No Matches";
	
	private static final String MATCH_REMOVE = "Remove";
	private static final String MATCH_ALLOCATE = "Allocate";
	
	private static final String MSG_ALLOCATEFAIL = "ALLOCATEFAIL";
	private static final String MSG_SAVEFAIL = "SAVEFAIL";
	private static final String MSG_FREESLOT = "FREESLOT";
	private static final String MSG_SLOTTAKEN = "SLOTTAKEN";
	private static final String MSG_RMFAIL = "RMFAIL";
	private static final long serialVersionUID = 1L;
	
	DataSource dataSource;
	Referees refereesTab;
	Report reportTab;
	Match match;
	int lastWeek;
	JTextArea txtStatus;

	public Matches(DataSource dataSource,Referees refereesTab,Report matchesTab) {
		this.dataSource = dataSource;
		this.reportTab = matchesTab;
		this.refereesTab = refereesTab;
		txtStatus = new JTextArea("");
		txtStatus.setFont(new Font("Courier",Font.PLAIN,12));
		txtStatus.setEditable(false);
		txtStatus.setLineWrap(true);
		lastWeek = Match.MIN_MATCHES;
		match = new Match();
		refresh();
	}
	
	private void refresh() {
		this.removeAll();
		this.setLayout(new GridLayout(1, 2));
		
		String forAction;
		
		match.setWeek(lastWeek);
		/*
		 * The search returns a null or a reference to an object
		 */
		match = dataSource.search(match);
		/*
		 * The object returned from search must be cloned to ensure 
		 * the array object is not mutated. 
		 */
		match = match == null ? null : new Match(match);
		if (match == null) {
			match = new Match();
			setStatusMsg(Matches.MSG_FREESLOT);
			forAction = Matches.MATCH_ALLOCATE;
		} else {
			setStatusMsg(Matches.MSG_SLOTTAKEN);
			forAction = Matches.MATCH_REMOVE;
		}
		this.add(drawLeftPanel(forAction));
		this.add(drawRightPanel());
		// Update report tab
		reportTab.refresh();
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
		cbHomeAreas.setMaximumSize(cbHomeAreas.getPreferredSize());
		leftPanelGrid.add(cbHomeAreas);

		// -------------------------- LEVEL -----------------------------------
		
		leftPanelGrid.add(new JLabel("Group"));
		JPanel panelRadioButtons = new JPanel();
		JRadioButton rbJunior = new JRadioButton();
		rbJunior.setText(Match.JUNIOR);
		rbJunior.setSelected(match.getCategory().equals(Match.JUNIOR) ? true : false);
		rbJunior.setEnabled(forAction.equals(Matches.MATCH_ALLOCATE));
		panelRadioButtons.add(rbJunior);
		JRadioButton rbSenior = new JRadioButton();
		rbSenior.setText(Match.SENIOR);
		rbSenior.setSelected(match.getCategory().equals(Match.SENIOR) ? true : false);
		rbSenior.setEnabled(forAction.equals(Matches.MATCH_ALLOCATE));
		panelRadioButtons.add(rbSenior);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(rbJunior);
		radioGroup.add(rbSenior);
		
		leftPanelGrid.add(panelRadioButtons);
		leftPanel.add(leftPanelGrid,BorderLayout.NORTH);
		
		/* --------------------- ADD AND REMOVE -------------------------------
		 * Although the specification document does not require deallocating
		 * matches and therefore decreasing the number of allocations such 
		 * functionality has been implemented. 
		 */
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
						if (match.getSelectedReferees() != null) {
							if (dataSource.add(match)) {
								refresh();
								dataSource.increaseRefereeAllocation(match.getSelectedReferees());
								refereesTab.refreshTableData();
								setStatusMsg(forAction);
							} else {
								setStatusMsg(Matches.MSG_SAVEFAIL);
								refresh();
							}							
						} else {
							setStatusMsg(Matches.MSG_ALLOCATEFAIL);
						}

						break;
					case Matches.MATCH_REMOVE :
						if (dataSource.remove(match)) {
							dataSource.decreaseRefereeAllocation(match.getSelectedReferees());
							refereesTab.refreshTableData();
							refresh();
						} else {
							setStatusMsg(Matches.MSG_RMFAIL);
						}
					default : break;
				}
			}
		});

		
		// ------------------- SPINNER LISTENER -------------------------------
		sprWeek.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lastWeek = (int)sprWeek.getValue();
				refresh();
			}
		});

		leftPanel.add(txtStatus);
		leftPanel.add(btnAddMatch,BorderLayout.SOUTH);
		return leftPanel;
	}
	
	private JComponent drawRightPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout());
		if (match == null || match.getAllocatedReferees() == null) {
			JPanel noMatchesPanel = new JPanel(new BorderLayout());
			JLabel lblNoResults = new JLabel(Matches.MSG_NOMATCHES);
			lblNoResults.setHorizontalAlignment(JLabel.CENTER);
			lblNoResults.setVerticalAlignment(JLabel.CENTER);
			noMatchesPanel.add(lblNoResults,BorderLayout.CENTER);
			rightPanel.add(noMatchesPanel);
			return rightPanel;
		}
		DefaultListModel<String> lstRefereesModel = new DefaultListModel<String>();
		for (Referee referee : match.getAllocatedReferees()) {
			lstRefereesModel.addElement(referee.printRefereeRecord(Referee.LIST_RECORD_FORMAT));
		}
		JList<String> lstReferees = new JList<String>();
		lstReferees.setFont(new Font("Courier",Font.PLAIN,12));
		lstReferees.setModel(lstRefereesModel);
		lstReferees.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstReferees.setSelectedIndices(Referee.SELECTED_REFEREES);
		JScrollPane spReferees = new JScrollPane(lstReferees);
		rightPanel.add(spReferees,BorderLayout.CENTER);
		return rightPanel;
	}
	
	private void setStatusMsg(String type) {
		String msg = "";
		switch (type) {
			case Matches.MATCH_ALLOCATE : 
				msg = "A new match with allocated referees has been saved!\n\n"
						+ String.format(Match.LIST_DISPLAY_FORMAT, 
								Match.FIELD_WEEK,Match.FIELD_GROUP,
								Match.FIELD_AREA,"REF 1","REF 2");
				msg = msg + "\n" + match + "\n\n" + "Referee details:" 
						+ "\n" + match.printRefereeDetails(0) + "\n\n" 
						+ match.printRefereeDetails(1);
				break;
			case Matches.MSG_SAVEFAIL :
				msg = "There was a error.\n"
						+ "Check if the match does not exist already.";
				break;
			case Matches.MSG_FREESLOT : 
				msg = "There is not a match scheduled for this week";
				break;
			case Matches.MSG_SLOTTAKEN : 
				msg = "Scheduled match:\n"
						+ String.format(Match.LIST_DISPLAY_FORMAT, 
								Match.FIELD_WEEK,Match.FIELD_GROUP,
								Match.FIELD_AREA,"REF 1","REF 2");
				msg = msg + "\n" + match + "\n\n" 
						+ "Referee details at the time of allocation:" 
						+ "\n" + match.printRefereeDetails(0) + "\n\n" 
						+ match.printRefereeDetails(1);
				break;
			case Matches.MSG_RMFAIL :
				msg = "There was a error.\nThe match could not be removed";
				break;
			case Matches.MSG_ALLOCATEFAIL :
				msg = "There are not any suitable referees available.\n"
						+ "The match could not be allocated.";
				break;				
			default : break;
		}
		txtStatus.setText(msg);
	}
}


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class GuiSearch extends JPanel implements ActionListener {
	
	public static final String TAB_NAME = "Search";
	private static final long serialVersionUID = 1L;
	
	private static final String FORM_EDIT = "Edit";
	private static final String FORM_SAVE = "Save";
	private static final String FORM_ADD = "Add";
	private static final String FORM_CANCEL = "Cancel";
	private static final String FORM_DELETE = "Delete";
	
	JPanel editPanel;
	JButton btnFormCancel;
	JTable table;
	Referee referee;
	
	DataSource fileStore;
	GuiReferees refereesTab;
	
	public GuiSearch(DataSource fileStore,GuiReferees refereesTab) {
		this.fileStore = fileStore;
		this.refereesTab = refereesTab;
		this.setLayout(new BorderLayout());
		layoutComponents();
	}
	
	private void layoutComponents() {
		JPanel north = new JPanel(new GridLayout(1, 2));
		JPanel searchSection = new JPanel(new GridLayout(3, 2));

		JLabel lblFirstName = new JLabel("First name");
		searchSection.add(lblFirstName);
		
		JTextField txtFirstName = new JTextField("");
		txtFirstName.setMaximumSize(txtFirstName.getPreferredSize());
		
		JTextField txtLastName = new JTextField("");
		txtLastName.setMaximumSize(txtLastName.getPreferredSize());
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runSearch(txtFirstName.getText().trim(),txtLastName.getText().trim());
				txtFirstName.setText("");
				txtLastName.setText("");
				btnSearch.setEnabled(false);
			}
		});
		btnSearch.setEnabled(false);
		
		txtFirstName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {}
			@Override
			public void focusLost(FocusEvent arg0) {
            	if (!txtFirstName.getText().trim().equals("") 
            			&& !txtLastName.getText().trim().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
			}
        });
		searchSection.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last name");
		searchSection.add(lblLastName);

		txtLastName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if (!txtLastName.getText().equals("") 
            			&& !txtLastName.getText().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
            }
        });
		searchSection.add(txtLastName);

		JLabel lblDummy = new JLabel("");
		searchSection.add(lblDummy);

		searchSection.add(btnSearch);
		
		north.add(searchSection);

		JPanel addSection = new JPanel(new BorderLayout());
		JLabel lblAddTitle = new JLabel("Add a new Referee",SwingConstants.CENTER);
		addSection.add(lblAddTitle,BorderLayout.CENTER);
		JButton btnAdd = new JButton(GuiSearch.FORM_ADD);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddOrEditForm(GuiSearch.FORM_ADD);
			}
		});
		
		btnAdd.setEnabled(true);
		addSection.add(btnAdd,BorderLayout.SOUTH);
		
		north.add(addSection);
		this.add(north, BorderLayout.NORTH);
		
		editPanel = new JPanel(new BorderLayout());
		this.add(editPanel);
	}
	
	/*
	 * Let's reuse the button for Edit and Save using the label to store the 
	 * state. 
	 */
	
	private void showAddOrEditForm(String formAction) {
		editPanel.removeAll();
		if (formAction == GuiSearch.FORM_ADD) {
			referee = new Referee("","");
		} 
		JPanel addFormInput = new JPanel(new GridLayout(Referee.FIELD_NAMES.length, 2));
		
		// --------------------------- ID -------------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[0]));
		JTextField txtEditId = new JTextField(referee.getId());
		txtEditId.setMaximumSize(txtEditId.getPreferredSize());
		txtEditId.setEditable(false);
		addFormInput.add(txtEditId);
		
		// ----------------------- FIRST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[1]));
		JTextField txtEditFirstName = new JTextField(referee.getFirstName());
		txtEditFirstName.setMaximumSize(txtEditFirstName.getPreferredSize());
		txtEditFirstName.setEditable(formAction.equals(GuiSearch.FORM_ADD));
		addFormInput.add(txtEditFirstName);
		
		// ------------------------ LAST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[2]));
		JTextField txtEditLastName = new JTextField(referee.getLastName());
		txtEditLastName.setMaximumSize(txtEditLastName.getPreferredSize());
		txtEditLastName.setEditable(formAction.equals(GuiSearch.FORM_ADD));
		addFormInput.add(txtEditLastName);
		
		// ---------------------- QUALIFICAITONS ------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[3]));
		JPanel cbQualificationsPanel = new JPanel();
		
		JComboBox<String> cbAwardingBodies = new JComboBox<String>(Qualification.AWARDING_BODIES);
		cbAwardingBodies.setSelectedIndex(referee.getQualification().getAwardingBodyIndex());
		cbAwardingBodies.setMaximumSize(cbAwardingBodies.getPreferredSize());
		cbAwardingBodies.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbAwardingBodies.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getQualification().setAwardingBody(cbAwardingBodies.getSelectedItem().toString());
			}
		});
		cbQualificationsPanel.add(cbAwardingBodies);
		
		JComboBox<Short> cbQualificationLevel = new JComboBox<Short>(Qualification.LEVELS);
		cbQualificationLevel.setSelectedIndex(referee.getQualification().getLevelIndex());
		cbQualificationLevel.setMaximumSize(cbAwardingBodies.getPreferredSize());
		cbQualificationLevel.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbQualificationLevel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getQualification().setLevel((short)cbQualificationLevel.getSelectedItem());
			}
		});
		cbQualificationsPanel.add(cbQualificationLevel);
		addFormInput.add(cbQualificationsPanel);
		
		// ----------------------- ALLOCATIONS --------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[4]));		
		JTextField txtEditAllocations = new JTextField(Integer.toString(referee.getAllocations()));
		txtEditAllocations.setMaximumSize(txtEditAllocations.getPreferredSize());
		txtEditAllocations.setEditable(formAction.equals(GuiSearch.FORM_ADD));
		addFormInput.add(txtEditAllocations);
		
		// ----------------------- HOME AREAS ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[5]));
		JComboBox<String> cbHomeAreas = new JComboBox<String>(Referee.HOME_AREAS);
		cbHomeAreas.setSelectedIndex(referee.getHomeAreaIndex() == -1 ? 0 : 
			referee.getHomeAreaIndex());
		cbHomeAreas.setSelectedItem(referee.getHomeAreaIndex());
		cbHomeAreas.setMaximumSize(cbHomeAreas.getPreferredSize());
		cbHomeAreas.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbHomeAreas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.setHomeArea(cbHomeAreas.getSelectedItem().toString());
			}
		});
		addFormInput.add(cbHomeAreas);
		
		// ---------------------- TRAVEL AREAS --------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[6]));
		JPanel cbPanel = new JPanel();
		JCheckBox cbNorth = new JCheckBox();
		cbNorth.setText(Referee.HOME_AREAS[0]);
		cbNorth.setSelected(referee.getTravelAreas().isNorth());
		cbNorth.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbNorth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().setNorth(cbNorth.isSelected());
			}
		});
		cbPanel.add(cbNorth);
		
		JCheckBox cbCentral = new JCheckBox();
		cbCentral.setText(Referee.HOME_AREAS[1]);
		cbCentral.setSelected(referee.getTravelAreas().isCentral());
		cbCentral.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbCentral.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().setCentral(cbCentral.isSelected());
			}
		});
		cbPanel.add(cbCentral);
		
		JCheckBox cbSouth = new JCheckBox();
		cbSouth.setText(Referee.HOME_AREAS[2]);
		cbSouth.setSelected(referee.getTravelAreas().isSouth());
		cbSouth.setEnabled(formAction.equals(GuiSearch.FORM_ADD));
		cbSouth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().setSouth(cbSouth.isSelected());
			}
		});
		cbPanel.add(cbSouth);
		
		cbPanel.setMaximumSize(cbPanel.getPreferredSize());
		addFormInput.add(cbPanel);
		
		// ------------------------- BUTTONS ----------------------------------
		JButton btnAddOrEdit = new JButton(formAction.equals(GuiSearch.FORM_EDIT) 
				? GuiSearch.FORM_EDIT : GuiSearch.FORM_ADD);
		btnAddOrEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (btnAddOrEdit.getText()) {
					case GuiSearch.FORM_EDIT : 
						btnAddOrEdit.setText(GuiSearch.FORM_SAVE);
						cbAwardingBodies.setEnabled(true);
						cbQualificationLevel.setEnabled(true);
						cbHomeAreas.setEnabled(true);
						cbNorth.setEnabled(true);
						cbCentral.setEnabled(true);
						cbSouth.setEnabled(true);
						break;
					case GuiSearch.FORM_SAVE :
						if (validateTravelArea()) {
							if (fileStore.updateReferee(referee)) {
								refereesTab.refreshTableData();
								JOptionPane.showMessageDialog(null, "Referee details have been updated.",
										"New Data", JOptionPane.INFORMATION_MESSAGE);
								btnAddOrEdit.setText(GuiSearch.FORM_SAVE);
								showAddOrEditForm(GuiSearch.FORM_EDIT);
							};
						}  
						break;
					case GuiSearch.FORM_ADD :
						if (validateName(txtEditFirstName.getText()) 
								&& validateName(txtEditLastName.getText())) {
							referee.setFirstName(txtEditFirstName.getText().trim());
							referee.setLastName(txtEditLastName.getText().trim());
							if (validateAllocations(txtEditAllocations.getText().trim())
									&& validateTravelArea()) {
									referee.setId(fileStore.getRefereeId(referee));
									if (fileStore.addReferee(referee)) {
										JOptionPane.showMessageDialog(null, 
											"New Referee has been added.",
											"New Referee", JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(null, 
												"There was a error. "
												+ "Check if the referee does not exist already.",
												"New Referee", JOptionPane.WARNING_MESSAGE);
									}
									refereesTab.refreshTableData();
									showAddOrEditForm(GuiSearch.FORM_EDIT);
								} 
						}
						break;
					default: 
						break;
				}
			}
		});
		
		btnFormCancel = new JButton(GuiSearch.FORM_CANCEL);
		btnFormCancel.addActionListener(this);
		
		JButton btnFormDelete = new JButton(GuiSearch.FORM_DELETE);
		btnFormDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileStore.removeReferee(referee)) {
					runSearch(referee.getFirstName(),referee.getLastName());
					refereesTab.refreshTableData();
					JOptionPane.showMessageDialog(null, "Referee has been removed",
							"Entry removed", JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					JOptionPane.showMessageDialog(null, "Referee has not been found",
						"No entry", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JPanel addFormButtons;
		if (formAction.equals(GuiSearch.FORM_EDIT)) {
			addFormButtons = new JPanel(new GridLayout(1,3));
			addFormButtons.add(btnAddOrEdit);
			addFormButtons.add(btnFormCancel);
			addFormButtons.add(btnFormDelete);
		} else {
			addFormButtons = new JPanel(new GridLayout(1,2));
			addFormButtons.add(btnAddOrEdit);
			addFormButtons.add(btnFormCancel);
		}
		editPanel.add(addFormInput);
		editPanel.add(addFormButtons,BorderLayout.SOUTH);
		this.revalidate();
	}

	private boolean validateName(String name) {
		if(!name.matches(".*\\d.*")){
			if (!name.trim().equals("")) {
				return true;
			} else {
	    		JOptionPane.showMessageDialog(null, 
	            	"This field cannot be empty.",
	        		"Invalid input: " + !name.trim().equals(""), JOptionPane.WARNING_MESSAGE);
				return false;	
			}
		} else{
    		JOptionPane.showMessageDialog(null, 
        		"This field cannot contain numbers.",
    			"Invalid input: " + name, JOptionPane.WARNING_MESSAGE);
    		return false;
		}
	}
	
    public boolean validateAllocations(String allocations) {
		if(!allocations.matches(".*\\[a-zA-Z]+.*")){
    		try {
    			int alloc = Integer.parseInt(allocations.trim());
    			if (alloc > -1) {
    				referee.setAllocations(alloc);
    				return true;
    			} else {
            		JOptionPane.showMessageDialog(null, 
            			"Input must be a number greater or equal to 0",
        				"Invalid input", JOptionPane.WARNING_MESSAGE);
    				return false;	
    			}
    		} catch (NumberFormatException nfe) {
        		JOptionPane.showMessageDialog(null, "Input must be a valid number.",
					"Invalid input", JOptionPane.WARNING_MESSAGE);
        		return false;
    		}
		} else{
    		JOptionPane.showMessageDialog(null, 
        		"This field cannot contain characters.",
    			"Invalid input: " + allocations, JOptionPane.WARNING_MESSAGE);
    		return false;
		}
    }
	
	private boolean validateTravelArea() {
		String travelArea = referee.getTravelAreas().toString();
		if (!"".equals(travelArea)) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, TravelAreas.getAdvice(),
				"Invalid input", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	private void runSearch(String firstName, String lastName) {
		referee = fileStore.search(firstName,lastName);
		if (referee != null) {
			showAddOrEditForm(GuiSearch.FORM_EDIT);
		} else {
			JPanel noResultPanel = new JPanel(new BorderLayout());
			JLabel lblNoResults = new JLabel("No Results");
			lblNoResults.setHorizontalAlignment(JLabel.CENTER);
			lblNoResults.setVerticalAlignment(JLabel.CENTER);
			noResultPanel.add(lblNoResults,BorderLayout.CENTER);
			editPanel.add(noResultPanel);
			this.revalidate();
		}
		
	}
		
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnFormCancel) {
			editPanel.removeAll();
			editPanel.add(new JPanel(new BorderLayout()));
			this.revalidate();
		} 
	}

}

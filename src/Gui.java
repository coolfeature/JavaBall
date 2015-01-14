
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame implements ActionListener {
	
	JPanel editPanel;
	JButton btnSearch;
	JButton btnAdd;
	JButton btnExit;
	JButton btnFormCancel;
	
	JTextField txtFirstName;
	JTextField txtLastName;
	
	JTable table;
	Referee referee;
	FileStore fileStore = null;
	
	private static final String FORM_EDIT = "Edit";
	private static final String FORM_SAVE = "Save";
	private static final String FORM_ADD = "Add";
	private static final String FORM_CANCEL = "Cancel";
	private static final String FORM_DELETE = "Delete";
	
	public Gui() {
		this.setLocation(200, 200);
		this.setSize(600, 500);
		this.setTitle("Java Ball");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		fileStore = new FileStore();
		this.layoutComponents();
	}

	private void layoutComponents() {
		JPanel container = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent listTab = new JPanel(new BorderLayout());
		table = new JTable();
		refreshTableData();
		JScrollPane scrollPane = new JScrollPane(table);
		listTab.add(scrollPane, BorderLayout.NORTH);
		listTab.setPreferredSize(new Dimension(300, 300));
		tabbedPane.addTab("Referees", listTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent searchTab = makeSearchTab();
		tabbedPane.addTab("Search", searchTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		container.add(btnExit,BorderLayout.SOUTH);
		container.add(tabbedPane);
		this.add(container);
	}

	public void refreshTableData() {
		DefaultTableModel model = new DefaultTableModel(
				fileStore.getRefereesData(),Referee.FIELD_NAMES);
		table.setModel(model);
	}

	protected JComponent makeSearchTab() {
		JPanel searchPanel = new JPanel(new BorderLayout());
		
		JPanel north = new JPanel(new GridLayout(1, 2));
		JPanel searchSection = new JPanel(new GridLayout(3, 2));

		JLabel lblFirstName = new JLabel("First name");
		searchSection.add(lblFirstName);

		txtFirstName = new JTextField("");
		txtFirstName.setMaximumSize(txtFirstName.getPreferredSize());
		txtFirstName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {}
			@Override
			public void focusLost(FocusEvent arg0) {
            	if (!txtFirstName.getText().equals("") 
            			&& !txtLastName.getText().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
			}
        });
		searchSection.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last name");
		searchSection.add(lblLastName);

		txtLastName = new JTextField("");
		txtLastName.setMaximumSize(txtLastName.getPreferredSize());
		txtLastName.addKeyListener((new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if (!txtLastName.getText().equals("") 
            			&& !txtLastName.getText().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
            }
        }));
		searchSection.add(txtLastName);

		JLabel lblDummy = new JLabel("");
		searchSection.add(lblDummy);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		btnSearch.setEnabled(false);
		searchSection.add(btnSearch);
		
		north.add(searchSection);

		JPanel addSection = new JPanel(new BorderLayout());
		JLabel lblAddTitle = new JLabel("Add a new Referee",SwingConstants.CENTER);
		addSection.add(lblAddTitle,BorderLayout.CENTER);
		btnAdd = new JButton(Gui.FORM_ADD);
		btnAdd.addActionListener(this);
		btnAdd.setEnabled(true);
		addSection.add(btnAdd,BorderLayout.SOUTH);
		
		north.add(addSection);
		searchPanel.add(north, BorderLayout.NORTH);
		
		editPanel = new JPanel(new BorderLayout());
		searchPanel.add(editPanel);
		return searchPanel;
	}
	
	/*
	 * Let's use the button label to store the some state. 
	 */
	
	private void showAddOrEditForm(String formAction) {
		editPanel.removeAll();
		if (formAction == Gui.FORM_ADD) {
			referee = new Referee("","");
		} 
		JPanel addFormInput = new JPanel(new GridLayout(Referee.FIELD_NAMES.length, 2));
		
		// --------------------------- ID -------------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[0]));
		JTextField txtEditId = new JTextField(referee.getId());
		txtEditId.setMaximumSize(txtEditId.getPreferredSize());
		txtEditId.setEditable(formAction.equals(Gui.FORM_ADD));
		addFormInput.add(txtEditId);
		
		// ----------------------- FIRST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[1]));
		JTextField txtEditFirstName = new JTextField(referee.getFirstName());
		txtEditFirstName.setMaximumSize(txtEditFirstName.getPreferredSize());
		txtEditFirstName.setEditable(formAction.equals(Gui.FORM_ADD));
		addFormInput.add(txtEditFirstName);
		
		// ------------------------ LAST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[2]));
		JTextField txtEditLastName = new JTextField(referee.getLastName());
		txtEditLastName.setMaximumSize(txtEditLastName.getPreferredSize());
		txtEditLastName.setEditable(formAction.equals(Gui.FORM_ADD));
		addFormInput.add(txtEditLastName);
		
		// ---------------------- QUALIFICAITONS ------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[3]));
		JTextField txtEditQualification = new JTextField(referee.getQualification().toString());
		txtEditQualification.setMaximumSize(txtEditQualification.getPreferredSize());
		txtEditQualification.setEditable(formAction.equals(Gui.FORM_ADD));
		txtEditQualification.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {}
			@Override
			public void focusLost(FocusEvent arg0) {referee.setQualification(
					new Qualification(txtEditQualification.getText().trim()));}
		});
		addFormInput.add(txtEditQualification);
		
		// ----------------------- ALLOCATIONS --------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[4]));
		JTextField txtEditAllocations = new JTextField(Integer.toString(referee.getAllocations()));
		txtEditAllocations.setMaximumSize(txtEditAllocations.getPreferredSize());
		txtEditAllocations.setEditable(formAction.equals(Gui.FORM_ADD));
		addFormInput.add(txtEditAllocations);
		
		// ----------------------- HOME AREAS ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[5]));
		JComboBox<String> cbHomeAreas = new JComboBox<String>(Referee.HOME_AREAS);
		cbHomeAreas.setSelectedIndex(referee.getHomeAreaIndex() == -1 ? 0 : 
			referee.getHomeAreaIndex());
		cbHomeAreas.setSelectedItem(referee.getHomeAreaIndex());
		cbHomeAreas.setMaximumSize(cbHomeAreas.getPreferredSize());
		cbHomeAreas.setEnabled(formAction.equals(Gui.FORM_ADD));
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
		cbNorth.setEnabled(formAction.equals(Gui.FORM_ADD));
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
		cbCentral.setEnabled(formAction.equals(Gui.FORM_ADD));
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
		cbSouth.setEnabled(formAction.equals(Gui.FORM_ADD));
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
		JButton btnAddOrEdit = new JButton(formAction.equals(Gui.FORM_EDIT) 
				? Gui.FORM_EDIT : Gui.FORM_ADD);
		btnAddOrEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (btnAddOrEdit.getText()) {
					case Gui.FORM_EDIT : 
						btnAddOrEdit.setText(Gui.FORM_SAVE);
						txtEditQualification.setEditable(true);
						cbHomeAreas.setEnabled(true);
						cbNorth.setEnabled(true);
						cbCentral.setEnabled(true);
						cbSouth.setEnabled(true);
						break;
					case Gui.FORM_SAVE :
						// Check qualification
						if (!"".equals(referee.getQualification().toString())) {
							if (!"".equals(referee.getTravelAreas().toString())) {
								referee.setHomeArea(cbHomeAreas.getSelectedItem().toString());
								if (fileStore.updateReferee(referee)) {
									refreshTableData();
									JOptionPane.showMessageDialog(null, "Referee details have been updated.",
											"New Data", JOptionPane.INFORMATION_MESSAGE);
									btnAddOrEdit.setText(Gui.FORM_SAVE);
									showAddOrEditForm(Gui.FORM_EDIT);
								};
							} else {
								JOptionPane.showMessageDialog(null, TravelAreas.getAdvice(),
									"Invalid input", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, Qualification.getAdvice(),
								"Invalid input", JOptionPane.WARNING_MESSAGE);							
						} 
						break;
					case Gui.FORM_ADD : 
						System.out.println("add"); 
						break;
					default: 
						break;
				}
			}
		});
		
		btnFormCancel = new JButton(Gui.FORM_CANCEL);
		btnFormCancel.addActionListener(this);
		
		JButton btnFormDelete = new JButton(Gui.FORM_DELETE);
		btnFormDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileStore.removeReferee(referee)) {
					refreshTableData();
					JOptionPane.showMessageDialog(null, "Referee has not been removed",
							"Entry removed", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Referee has not been found",
						"No entry", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JPanel addFormButtons;
		if (formAction.equals(Gui.FORM_EDIT)) {
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
	
	private void runSearch() {
		String firstName = txtFirstName.getText().trim(); 
		String lastName = txtLastName.getText().trim();
		referee = fileStore.search(firstName,lastName);
		if (referee != null) {
			showAddOrEditForm(Gui.FORM_EDIT);
		} else {
			JPanel noResultPanel = new JPanel(new BorderLayout());
			JLabel lblNoResults = new JLabel("No Results");
			lblNoResults.setHorizontalAlignment(JLabel.CENTER);
			lblNoResults.setVerticalAlignment(JLabel.CENTER);
			noResultPanel.add(lblNoResults,BorderLayout.CENTER);
			editPanel.add(noResultPanel);
			this.revalidate();
		}
		txtFirstName.setText("");
		txtLastName.setText("");
		btnSearch.setEnabled(false);
	}
		

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnSearch) {
			runSearch();
		} else if (event.getSource() == btnAdd) {
			showAddOrEditForm(Gui.FORM_ADD);
		} else if (event.getSource() == btnFormCancel) {
			editPanel.removeAll();
			editPanel.add(new JPanel(new BorderLayout()));
			this.revalidate();
		} else if (event.getSource() == btnExit) {
			System.exit(0);
		}
	}
}

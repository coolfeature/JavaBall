
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame implements ActionListener {
	
	JPanel editPanel;
	JButton btnSearch;
	JButton btnAdd;
	JButton btnExit;
	JButton btnFormCancel;
	
	JTextField txtFirstName;
	JTextField txtLastName;
	
	Gui gui = this;
	FileStore fileStore = null;
	
	private static final String[] TRAVEL_AREAS = {"North","Central","South"};
	private static final String FORM_EDIT = "Edit";
	private static final String FORM_SAVE = "Save";
	private static final String FORM_ADD = "Add";
	
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

		JComponent listTab = makeRefereesTab();
		listTab.setPreferredSize(new Dimension(300, 300));
		tabbedPane.addTab("Referees", listTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent searchTab = makeSearchTab();
		tabbedPane.addTab("Search", searchTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		btnExit = new JButton("Exit");
		container.add(btnExit,BorderLayout.SOUTH);
		container.add(tabbedPane);
		this.add(container);
	}

	protected JComponent makeRefereesTab() {
		JPanel panel = new JPanel(new BorderLayout());
		JTable table = new JTable(fileStore.getRefereesData(),
				Referee.FIELD_NAMES);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.NORTH);
		return panel;
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
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		btnAdd.setEnabled(true);
		addSection.add(btnAdd,BorderLayout.SOUTH);
		
		north.add(addSection);
		searchPanel.add(north, BorderLayout.NORTH);
		
		editPanel = new JPanel(new BorderLayout());
		searchPanel.add(editPanel);
		return searchPanel;
	}
	
	private void showAddOrEditForm(Referee referee,String formAction) {
		editPanel.removeAll();
		if (referee == null || formAction == Gui.FORM_ADD) {
			referee = new Referee("","","","",0,"","");
		} 
		JPanel addForm = new JPanel(new GridLayout(Referee.FIELD_NAMES.length + 1, 2));
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[0]));
		JTextField txtEditId = new JTextField(referee.getId());
		txtEditId.setMaximumSize(txtEditId.getPreferredSize());
		txtEditId.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditId);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[1]));
		JTextField txtEditFirstName = new JTextField(referee.getFirstName());
		txtEditFirstName.setMaximumSize(txtEditFirstName.getPreferredSize());
		txtEditFirstName.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditFirstName);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[2]));
		JTextField txtEditLastName = new JTextField(referee.getLastName());
		txtEditLastName.setMaximumSize(txtEditLastName.getPreferredSize());
		txtEditLastName.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditLastName);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[3]));
		JTextField txtEditQualification = new JTextField(referee.getQualification());
		txtEditQualification.setMaximumSize(txtEditQualification.getPreferredSize());
		txtEditQualification.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditQualification);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[4]));
		JTextField txtEditAllocations = new JTextField(Integer.toString(referee.getAllocations()));
		txtEditAllocations.setMaximumSize(txtEditAllocations.getPreferredSize());
		txtEditAllocations.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditAllocations);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[5]));
		JTextField txtEditHomeArea = new JTextField(referee.getHomeArea());
		txtEditHomeArea.setMaximumSize(txtEditHomeArea.getPreferredSize());
		txtEditHomeArea.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditHomeArea);
		
		addForm.add(new JLabel(Referee.FIELD_NAMES[6]));
		JTextField txtEditTravelAreas = new JTextField(referee.getTravelAreas());
		txtEditTravelAreas.setMaximumSize(txtEditTravelAreas.getPreferredSize());
		txtEditTravelAreas.setEditable(formAction.equals(Gui.FORM_ADD));
		addForm.add(txtEditTravelAreas);
		
		JButton btnFormAction = new JButton(formAction.equals(Gui.FORM_EDIT) ? Gui.FORM_EDIT : Gui.FORM_ADD);
		btnFormAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (btnFormAction.getText()) {
					case Gui.FORM_EDIT : 
						btnFormAction.setText(Gui.FORM_SAVE);
						txtEditQualification.setEditable(true);
						txtEditHomeArea.setEditable(true);
						txtEditTravelAreas.setEditable(true);
						break;
					case Gui.FORM_SAVE : 
						System.out.println("save"); break;
					case Gui.FORM_ADD : System.out.println("add"); break;
					default: break;
				}
			}
		});
		addForm.add(btnFormAction);
		btnFormCancel = new JButton("Cancel");
		btnFormCancel.addActionListener(this);
		addForm.add(btnFormCancel);		
		
		editPanel.add(addForm);
		this.revalidate();
	}

	public Referee search(String firstName, String lastName) {
		Referee[] referees = fileStore.getReferees();
		Referee referee = null;
		for (Referee ref : referees) {
			if (ref.getFirstName().equals(firstName)
					&& ref.getLastName().equals(lastName)) {
				referee = ref;
			}
		}
		return referee;
	}
	
	private void runSearch() {
		String firstName = txtFirstName.getText().trim(); 
		String lastName = txtLastName.getText().trim();
		Referee referee = search(firstName,lastName);
		showAddOrEditForm(referee,Gui.FORM_EDIT);;
		txtFirstName.setText("");
		txtLastName.setText("");
		btnSearch.setEnabled(false);
	}
		

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnSearch) {
			runSearch();
		} else if (event.getSource() == btnAdd) {
			showAddOrEditForm(null,Gui.FORM_ADD);
		} else if (event.getSource() == btnFormCancel) {
			editPanel.removeAll();
			editPanel.add(new JPanel(new BorderLayout()));
			this.invalidate();
		}
	}
}

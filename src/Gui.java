
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame implements ActionListener {

	DefaultTableModel tblSearchResultModel;
	JTable tblSearchResult;
	
	JButton btnSearch;
	JTextField txtFirstName;
	JTextField txtLastName;
	
	FileStore fileStore = null;

	public Gui() {
		this.setLocation(200, 200);
		this.setSize(600, 500);
		this.setTitle("Java Ball");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		fileStore = new FileStore();
		this.layoutComponents();
	}

	private void layoutComponents() {
		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panel1 = makeRefereesTab();
		panel1.setPreferredSize(new Dimension(300, 300));
		tabbedPane.addTab("Referees", panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeSearchTab();
		tabbedPane.addTab("Search", panel2);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		this.add(tabbedPane);
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
		JPanel panel = new JPanel(new BorderLayout());
		JPanel north = new JPanel(new GridLayout(3, 2));

		JLabel lblFirstName = new JLabel("First name");
		north.add(lblFirstName);

		txtFirstName = new JTextField("");
		txtFirstName.setMaximumSize(txtFirstName.getPreferredSize());
		txtFirstName.addKeyListener((new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if (!txtFirstName.getText().equals("") 
            			&& !txtLastName.getText().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
            }
        }));
		north.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last name");
		north.add(lblLastName);

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
		north.add(txtLastName);

		JLabel lblDummy = new JLabel("");
		north.add(lblDummy);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		btnSearch.setEnabled(false);
		north.add(btnSearch);
		
		panel.add(north, BorderLayout.NORTH);
		// --------------------------------------------------------------------

		JPanel south = new JPanel(new BorderLayout());
		tblSearchResult = new JTable();
		tblSearchResultModel = new DefaultTableModel(null, Referee.FIELD_NAMES);
		tblSearchResult.setModel(tblSearchResultModel);
		JScrollPane scrollPane = new JScrollPane(tblSearchResult);
		south.add(scrollPane);
		panel.add(south);
		return panel;
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
		tblSearchResultModel.setDataVector(null, Referee.FIELD_NAMES);
		String firstName = txtFirstName.getText().trim(); 
		String lastName = txtLastName.getText().trim();
		Referee referee = search(firstName,lastName);
		Object[] resultRow = new Object[]{
			referee.getId()
			,referee.getFirstName()
			,referee.getLastName()
			,referee.getQualification()
			,referee.getAllocations()
			,referee.getHomeArea()
			,referee.getTravelAreas()
		};
		tblSearchResultModel.addRow(resultRow);	
		JTextField txtCell = new JTextField();;
		txtCell.setEditable(false);
		DefaultCellEditor dce = new DefaultCellEditor(txtCell);
		tblSearchResult.getColumnModel().getColumn(0).setCellEditor(dce);
		tblSearchResult.getColumnModel().getColumn(1).setCellEditor(dce);
		tblSearchResult.getColumnModel().getColumn(2).setCellEditor(dce);
		tblSearchResult.getColumnModel().getColumn(4).setCellEditor(dce);
	}
		

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnSearch) {
			runSearch();
		}

	}

}

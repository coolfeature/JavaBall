

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Gui extends JFrame implements ActionListener {
	
	FileStore fileStore = null;
	
	public Gui () {
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
		panel1.setPreferredSize(new Dimension(300,300));
		tabbedPane.addTab("Referees", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeSearchTab();
		tabbedPane.addTab("Search", panel2);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
       
        this.add(tabbedPane);
	}
	
	protected JComponent makeRefereesTab() {
		JPanel panel = new JPanel(new BorderLayout());
		JTable table = new JTable(fileStore.getRefereesData(),Referee.FIELD_NAMES);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane,BorderLayout.NORTH);
        return panel;
	}
	    
    protected JComponent makeSearchTab() {
        JPanel panel = new JPanel(new BorderLayout());
		JPanel top = new JPanel(new GridLayout(3,2));
		
		JLabel lblFirstName = new JLabel("First name");
		top.add(lblFirstName);

		JTextField txtFirstName = new JTextField("");
		txtFirstName.setMaximumSize(txtFirstName.getPreferredSize());
		top.add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last name");
		top.add(lblLastName);
		
		JTextField txtLastName = new JTextField("");
		txtLastName.setMaximumSize(txtLastName.getPreferredSize());
		top.add(txtLastName);

		JLabel lblDummy = new JLabel("");
		top.add(lblDummy);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		top.add(btnSearch);
		
		panel.add(top,BorderLayout.NORTH);
		// --------------------------------------------------------------------
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane,BorderLayout.SOUTH);
        return panel;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

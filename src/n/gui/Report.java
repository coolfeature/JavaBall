package n.gui;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import n.db.DataSource;
import n.models.Match;

/**
 * The class displays the match report.
 */
public class Report extends JPanel {
	
	/** instance variable for the report tab's name */
	public static final String TAB_NAME = "Report";
	/** instance variable for serialVersionUID set to 1L */
	private static final long serialVersionUID = 1L;
	
	/** Instance variables for DataSource and JTextArea also found in this class */
	DataSource dataSource;
	JTextArea txtReport;
	
	/**
	 * The constructor takes the DataStore reference as an argument.
	 * @param dataSource
	 */
	public Report(DataSource dataSource) {
		this.dataSource = dataSource;
		
		//set the layout for the Report tab to BorderLayout
		this.setLayout(new BorderLayout());
		
		//instantiate and initialise the txtReport variable as a JTextArea
		txtReport = new JTextArea("");
		
		//set the font for the report within the text area to Courier, plain at size 16
		txtReport.setFont(new Font("Courier",Font.PLAIN,16));
		this.add(txtReport, BorderLayout.CENTER);
		refresh();
	}
	
	/**
	 * The method refreshes the displayed match report.
	 */
	public void refresh() {
		Match[] matches = dataSource.getMatches();
		if (matches != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(Match.REPORT_DISPLAY_FORMAT, 
					Match.FIELD_WEEK,Match.FIELD_GROUP,Match.FIELD_AREA,
					Match.FIELD_REFEREE1,Match.FIELD_REFEREE2));
			for (Match match : dataSource.getMatches()) {
				sb.append("\n" + match.toReportString());
			}	
			txtReport.setText(sb.toString());
		} else {
			txtReport.setText(Matches.MSG_NOMATCHES);
		}	
	}
}

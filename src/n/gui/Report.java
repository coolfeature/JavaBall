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
	
	public static final String TAB_NAME = "Report";
	private static final long serialVersionUID = 1L;
	DataSource dataSource;
	JTextArea txtReport;
	
	/**
	 * The constructor takes the DataStore reference as an argument.
	 * @param dataSource
	 */
	public Report(DataSource dataSource) {
		this.dataSource = dataSource;
		this.setLayout(new BorderLayout());
		txtReport = new JTextArea("");
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

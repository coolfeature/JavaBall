package n.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

import n.db.DataSource;
import n.models.Referee;


public class Chart extends JPanel {

	public static final String TAB_NAME = "Chart";
	private static final long serialVersionUID = 1L;
	DataSource dataSource;
	
	/**
	 * The idea is to display a bar chart showing each referee's allocations.
	 * @param fileStore
	 */
	public Chart(DataSource fileStore) {
		this.dataSource = fileStore;    
		repaint();
	}
	
	public void refresh() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{	
		// LinkedHashMap as insertion order is important  
		Map<String, Referee> chartBars = new LinkedHashMap<String, Referee>();
		Referee[] referees = dataSource.getReferees();
		
		for (Referee r : referees) {
			chartBars.put(r.getId(), r);
		}
		
		// get max allocations
		int max = Integer.MIN_VALUE;
		for (Referee r : chartBars.values()) {
			max = Math.max(max, r.getAllocations());
		}
		
		// left padding
		int widthPad = 30;
		
		// top padding
		int heightPad = 30;
		
		// width of one column
		int width = ((getWidth() - (2*widthPad)) / chartBars.size()) - 2;

		// draw y line
		g.drawLine(widthPad, heightPad, widthPad, (getHeight() - (2*heightPad)));
		
		
/*		int divisionLineXStart = (widthPad - 3);
		int divisionLineXEnd = (widthPad + 3);
		for (int i=1;i<max+1;i++) {
			int divisionLineY = (int) ((getHeight()-(2*heightPad)) * ((double)i / max));
			g.drawLine(divisionLineXStart, divisionLineY, divisionLineXEnd, divisionLineY);
		}
		g.drawLine(widthPad, heightPad, widthPad, (getHeight() - (2*heightPad)));*/
		
		for (String id : chartBars.keySet())
		{
			Referee ref = chartBars.get(id);
			// the height of the bar 
			int height = (int) ((getHeight()-(2*heightPad)) * ((double)ref.getAllocations() / max));
			g.setColor(Color.green);
			// fill column
			g.fillRect(widthPad, getHeight() - height - heightPad, width, height);
			g.setColor(Color.black);
			// draw column
			g.drawRect(widthPad, getHeight() - height - heightPad, width, height);
			// draw number of allocations above each bar
			g.drawString(Integer.toString(ref.getAllocations()), widthPad  + (width/3), getHeight() - height - heightPad);
			// show id labels for columns
			g.drawString(id, widthPad + (width/3), getHeight() - (heightPad/2));
			// set the x padding for the next column
			widthPad += (width + 2);
		}
	}
}

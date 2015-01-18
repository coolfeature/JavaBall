package n.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import n.db.DataSource;
import n.models.Referee;


public class Chart extends JPanel {

	public static final String TAB_NAME = "Chart";
	private static final long serialVersionUID = 1L;
	
	/** Gap between each bar in pixels */
	private static final int GAP = 10;	
	
	/** Data source containing referee information */
	private DataSource dataSource;
	/** Width of bar in pixels */
	private int width;
	/** Vertical position of the bottom of the bar */
	private int bottom;
	/** Number of pixels representing one match allocation */
	private int pixelPerAllo;


	/**
	 * Constructor setting fileStore
	 * @param fileStore data store containing list of referees
	 */
	public Chart(DataSource fileStore) {
		this.dataSource = fileStore;
	}


	/**
	 * Method to paint a single bar
	 * @param g The graphics object to draw to
	 * @param x Position on the x-axis
	 * @param referee object being displayed as bar
	 */
	private void paintBar(Graphics2D g, int x, Referee referee){
		int barTop = bottom-referee.getAllocations()*pixelPerAllo;
		g.setColor(Color.green);
		g.fillRect(x, barTop, width, referee.getAllocations()*pixelPerAllo);
		g.setColor(Color.black);
		g.drawString(referee.getId(), x + (width/3), bottom+20);
		g.drawString("" + referee.getAllocations(), x + (width/3), barTop - 12);
	}

	/** Method to set the number of pixels inside the bar representing 1 allocation */
	private void setAllocationPixel (){
		int max = 0;
		for(Referee ref : dataSource.getReferees()) {
			if (ref.getAllocations() > max){
				max = ref.getAllocations();
			}
		}
		pixelPerAllo = (this.getHeight() - 60) / max;
	}

	/**
	 * Updates the display by painting the bar chart
	 * @param g the Graphics object
	 */  
	public void paintComponent(Graphics g)
	{  
		// Recover Graphics2D
		Graphics2D g2 = (Graphics2D) g;
		Referee[] refs = dataSource.getReferees();
		width = (this.getWidth()-(refs.length+1)*GAP) / refs.length; 
		setAllocationPixel();
		bottom = this.getHeight()-30;

		//sets font for label
		g2.setFont(new Font("Monospaced", Font.BOLD, 18));

		//iterated over referee array and paints the corresponding bar
		for (int i = 0; i < refs.length; i++)
		{
			paintBar(g2, (i+1)*GAP+i*width, refs[i]);
		}
	}
}

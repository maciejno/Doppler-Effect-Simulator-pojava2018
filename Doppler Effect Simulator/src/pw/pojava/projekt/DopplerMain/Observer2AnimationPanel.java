package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeries;

public class Observer2AnimationPanel extends ChartPanel {

	private static final long serialVersionUID = 1L;
	XYSeries xySeries;									
	//SourceSwingWorker worker;	
	Double x,y;	
	GUI superior;//referencja do GUI
	
	public Observer2AnimationPanel(GUI superior) {
		super(superior.fchart[2]);
		xySeries = new XYSeries("Observer 2 signal");
		superior.observer2Collection.addSeries(xySeries);
		this.superior = superior;
		//worker = new SourceSwingWorker();
	}
}

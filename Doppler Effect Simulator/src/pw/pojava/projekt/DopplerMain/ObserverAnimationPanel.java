package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pw.pojava.projekt.DopplerMain.SourceAnimationPanel.SourceSwingWorker;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public class ObserverAnimationPanel extends ChartPanel {
	
	private static final long serialVersionUID = 1L;
												
	XYSeries xySeries;									
	SourceSwingWorker worker;	
	Double x,y;	
	GUI superior;//referencja do GUI
	
	public ObserverAnimationPanel(GUI superior) {
		super(superior.fchart[0]);
		xySeries = new XYSeries("Source signal");
		superior.sourceCollection.addSeries(xySeries);
		this.superior = superior;
		//worker = new SourceSwingWorker();
	}
}

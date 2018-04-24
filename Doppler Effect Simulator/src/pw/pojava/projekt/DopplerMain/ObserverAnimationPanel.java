package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public class ObserverAnimationPanel extends ChartPanel {
	
	private static final long serialVersionUID = 1L;
	
	XYSeriesCollection xySeriesCollection;
	XYSeries xySeries;
	JFreeChart chart;

	public ObserverAnimationPanel(JFreeChart chart, XYSeriesCollection collection, XYSeries series) {
		super(chart);
		this.chart = chart;
		xySeriesCollection = collection;
		xySeries = series;
		
	}	
}

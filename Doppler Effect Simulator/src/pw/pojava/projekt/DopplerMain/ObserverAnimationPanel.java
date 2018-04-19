package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public class ObserverAnimationPanel extends ChartPanel {
	
	private static final long serialVersionUID = 1L;

	public ObserverAnimationPanel(JFreeChart chart) {
		super(chart);
	}	
}

package pw.pojava.projekt.DopplerMain;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class GUI extends JPanel {

	//panels in main panel
	JPanel pWest, pEast, pAnimation, pChart, pChartSource, pChartObserver1, pChartObserver2, pLanguage, pOptions, pControl;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	
	
	public GUI() {
		this.setLayout(new BorderLayout());//sets layout for main panel
		
		//tworzy panele
		pWest = new JPanel();
		pEast = new JPanel();
		pAnimation = new MainAnimationPanel();
		pChart = new JPanel();
		pChartSource = new SourceAnimationPanel(); 
		pChartObserver1 = new Observer1AnimationPanel();
		pChartObserver2 = new Observer2AnimationPanel();
		pLanguage = new JPanel();
		pOptions = new JPanel();
		pControl = new JPanel();
		
		//dodaje i ustawia 2 panele: lewy i prawy do glownego panelu
		this.add(BorderLayout.WEST, pWest);
		this.add(BorderLayout.EAST, pEast);
		//dodaje i ustawia panele do lewego panelu
		pWest.add(BorderLayout.NORTH, pAnimation);
		pWest.add(BorderLayout.SOUTH, pChart);
		//dodaje i ustawia panele do prawego panelu
		pEast.add(BorderLayout.NORTH, pLanguage);
		pEast.add(BorderLayout.CENTER, pOptions);
		pEast.add(BorderLayout.SOUTH, pControl);
		//dodaje i ustawia panele z wykresami do panelu pChart
		pChart.add(BorderLayout.NORTH, pChartSource);
		pChart.add(BorderLayout.CENTER, pChartObserver1);
		pChart.add(BorderLayout.SOUTH, pChartObserver2);
	}


}

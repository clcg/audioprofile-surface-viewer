package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import dataset.Audiogram;
import edu.uiowa.UserAudiogramData;
import edu.uiowa.UserPlotListener;
import resources.*;

import org.jfree.ui.RectangleEdge;

import java.awt.geom.Rectangle2D;

public class AudiogramGraph extends JPanel implements UserPlotListener{

	JLabel lblChart = new JLabel();
	BufferedImage defaultImage;

	public boolean graphMultipleAudiograms = false;
	
	Audiogram currentAudiogram = null;
	
	//Setting the values to -20 will hide it
	Audiogram defaultAudiogram = new Audiogram(-20);

	private String [] xStringValues = new String[]{".125","0.25","0.5","1","","2","","4","","8"};
	private float [] xValues = new float[]{1, 2, 3, 4, 4.5f, 5, 5.5f, 6,6.5f,7};

	public AudiogramGraph() {
		
		this.updateGraph();
		UserAudiogramData.addPlotListener(this);

	}

	public void setAudiogram(Audiogram a) {

		currentAudiogram = a;

		updateGraph();

	}

	public void removeAudiogram() {

		currentAudiogram = null;

		updateGraph();

	}

	public void updateGraph() {


		XYSeriesCollection dataset = new XYSeriesCollection();
		float age = 0;
		String locus ="";
		if(currentAudiogram != null) {
			dataset.addSeries(currentAudiogram.generateXYSeries());
			age = currentAudiogram.getAge();
			locus = currentAudiogram.getLocus();
		} else {
			dataset.addSeries(defaultAudiogram.generateXYSeries());
			
		}
		/**
		 * Add user defined audiogram
		 */
		if(UserAudiogramData.getHasAudiogram()) {
			Audiogram demoAudiogram = UserAudiogramData.getAudiogram();
			demoAudiogram.setPatient("Demo");
			dataset.addSeries(demoAudiogram.generateXYSeries());
		}
		
		JFreeChart jFreeChart = ChartFactory.createXYLineChart(
				"Audiogram at age "+age+" for "+locus, "Frequency", "Hearing Level in Decibels (dB HL)", dataset,
				PlotOrientation.VERTICAL, false, true, false);

		NumberAxis yAxis = new NumberAxis("Hearing Level (dB HL)");
		Range r = new Range(-10,130);
		yAxis.setRange(r);
		yAxis.setInverted(true);
		yAxis.setFixedAutoRange(10);
		yAxis.setTickUnit(new NumberTickUnit(10));

		jFreeChart.getXYPlot().setRangeAxis(yAxis);

		NumberAxis xAxis = new NumberAxis("Frequency (kHz)")
		{
			@Override
			protected List refreshTicksHorizontal(Graphics2D g2,
					Rectangle2D dataArea, RectangleEdge edge) {
				List<NumberTick> a = new ArrayList<NumberTick>();
				for(int i = 0; i < xValues.length; i++) {
					a.add(new NumberTick(TickType.MAJOR,xValues[i],xStringValues[i], TextAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER,
							0.0));
				}

				return a;
			}
		};
		
		jFreeChart.getXYPlot().setDomainAxis(xAxis);

		TickUnits units = new TickUnits();
		units.add(new NumberTickUnit(1));

		jFreeChart.getXYPlot().getDomainAxis().setStandardTickUnits(units);

		System.out.println(jFreeChart.getXYPlot().getDomainAxis());

		jFreeChart.getXYPlot().setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

		BufferedImage image = jFreeChart.createBufferedImage(700,436);

		ChartPanel cpanel = new ChartPanel( jFreeChart ) {
			@Override
			public Dimension getPreferredSize() {
				// given some values of w & h
				return new Dimension(480, 500);
			}
		};
		
		cpanel.setSize(500, 500);
		cpanel.setRangeZoomable(false);
		cpanel.setDomainZoomable(false);

		this.removeAll();
		this.add(cpanel);

		this.repaint();

	}

	public void userDefinedPlotUpdated() {
		updateGraph();
	}

}

package edu.uiowa;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.controllers.mouse.camera.ICameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.IColorMappable;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.ColorMapRainbowNoBorder;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.providers.StaticTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.ITickRenderer;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.legends.colorbars.AWTColorbarLegend;

import dataset.Audiogram;
import demos.Jzy3dRotationProblem.InvertedColorMapRainbow;
import edu.uiowa.mappers.AudiogramMapper;

public class AudiogramChartFactory {

	static String[] lociStrings = { "DFNA9", "DFNA8/12", "DFNA2A", "DFNA6/12/14", "GJB2" };
	static String[] freqString = {".125",".25",".5","1","2","4","8"};


	public static Chart builtChart(Surface s)
	{

		Mapper mapper = s.mapper;

		int ageSteps = 60;
		Range ageRange = new Range( s.minAge, s.maxAge );

		final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(1, 7), 14, ageRange, ageSteps), mapper);
		surface.setColorMapper(new ColorMapper(new InvertedColorMapRainbow(), 0, 130, new Color(1, 1, 1, 1f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(true);
		surface.setBoundingBoxDisplayed(false);
		surface.setWireframeColor(new Color(0,0,0));
		
		Shape surface2 = null;
		if(UserAudiogramData.getHasAudiogram()) {
			Audiogram demoAudiogram = UserAudiogramData.getAudiogram();
			
			surface2 = Builder.buildOrthonormal(new OrthonormalGrid(new Range(1, 7), 14, new Range( demoAudiogram.getAge()-1, demoAudiogram.getAge()+1), 4), new AudiogramMapper(demoAudiogram));
			surface2.setColor(Color.RED);
			surface2.setWireframeDisplayed(false);
			surface2.setFaceDisplayed(true);
		}

		// With "awt", everything works. With "newt", various issues with mouse rotation/clicking, depending on JOGL version.
		Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, "awt"); // or "awt"
		chart.getScene().getGraph().add(surface);
		if(UserAudiogramData.getHasAudiogram()) {
			chart.getScene().getGraph().add(surface2);
		}
		//chart.addMouseController();
		
		AWTCameraMouseController mc = new NonzoomingMouseControler(chart);
		
		chart.setAxeDisplayed(true);
		chart.addKeyController();
		

		chart.getAxeLayout().setZTickRenderer(new ITickRenderer() {
			@Override
			public String format(double value) {
				try {

					return ""+(130 - value);
				} catch (Exception e) {
					return String.valueOf(value);
				}


			}
		});

		chart.getAxeLayout().setXTickRenderer(new ITickRenderer() {
			@Override
			public String format(double value) {
				try {

					int index = (int) value;
				
					return freqString[index-1];

				} catch (Exception e) {
					return String.valueOf(value);
				}


			}
		});

		chart.getAxeLayout().setYTickRenderer(new ITickRenderer() {
			@Override
			public String format(double value) {
				try {
					return "" + ((int)value);
				} catch (Exception e) {
					return String.valueOf(value);
				}


			}
		});


		chart.getAxeLayout().setXAxeLabel("Frequency (kHz)");
		chart.getAxeLayout().setYAxeLabel("Age");
		chart.getAxeLayout().setZAxeLabel("Hearing Loss (dB HL)");
		System.out.println("Tick: "+chart.getAxeLayout().getZTickRenderer().getClass().toString());

		double [] ticks = {1,2,3,4,5,6,7};
		
		StaticTickProvider a = new StaticTickProvider(ticks);
		chart.getAxeLayout().setXTickProvider(a);

		int maxAge = (int) Math.ceil(s.maxAge);
		int ageTickCount = maxAge / 10 + 1;
		
		double []ticks2 = new double[ageTickCount];//{0, 10, 20,30 ,40,50,60};
		for(int i = 0; i< ageTickCount; i++) {
			ticks2[i] = i*10;
		}
		
		
		a= new StaticTickProvider(ticks2);
		chart.getAxeLayout().setYTickProvider(a);

		double []ticks3 = {0, 10, 20,30 ,40,50,60,70,80,90,100,110,120,130};
		a= new StaticTickProvider(ticks3);
		chart.getAxeLayout().setZTickProvider(a);

		AWTColorbarLegend cl = new AWTColorbarLegend(surface, 
		        chart.getView().getAxe().getLayout().getZTickProvider(), 
		        chart.getView().getAxe().getLayout().getZTickRenderer());
		
		
		surface.setLegend(cl);
		
		chart.getView().setBoundManual(new BoundingBox3d(1, 7, 0,maxAge, 0, 130));

		return chart;

	}



	public static class InvertedColorMapRainbow extends ColorMapRainbowNoBorder {

		public Color getColor(double x, double y, double z, double zMin,
				double zMax) {
			// TODO Auto-generated method stub
			return super.getColor(x, y, z, zMin, zMax);
		}

		public Color getColor(IColorMappable colorable, double x, double y,
				double z) {
			// TODO Auto-generated method stub
			return super.getColor(colorable, x, y, 130-z);
		}

		@Override
		public Color getColor(IColorMappable colorable, double z) {
			// TODO Auto-generated method stub
			return super.getColor(colorable, 130-z);
		}

	}
	
	public static class NonzoomingMouseControler extends AWTCameraMouseController
	{
		public NonzoomingMouseControler(Chart chart) {
			super(chart);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) 
		{
			//Do nothing!
		}
	}
	
}

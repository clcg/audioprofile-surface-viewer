package edu.uiowa;

import gui.AudiogramGraph;
import gui.PatientAudiogramDialog;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartView;
import org.jzy3d.maths.Coord3d;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import dataset.Audiogram;

public class MainApplet extends JApplet implements UserPlotListener{

	Vector<Surface> surfaces;

	HashMap<String, Surface>surfaceMap = new HashMap<String, Surface>();

	JPanel mainPanel;

	Component currentMainComponent;
	Chart c;

	Coord3d defaultViewpoint = new Coord3d(5.6903043, 0.28719786, 225.16661);
	
	Pattern locusRegEx = Pattern.compile("DFNA(\\d+)\\s*");
	
	JPanel audiogramPanel = new JPanel();
	
	JSlider ageSlider;
	
	Surface currentSurface = null;
	
	AudiogramGraph audiogramGraph = null;


	public void init( ) {
		//setName("Audiogram Surface Viewer");
		this.setLayout(new BorderLayout());
		
		Vector<Surface> m_surfaces = DatasetLoader.loadDataset();
		
		this.surfaces = m_surfaces;

		for(Surface s: surfaces)
		{
			surfaceMap.put(s.title, s);
		}

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 650));       


		mainPanel = new JPanel(new BorderLayout());

		JPanel menu = new JPanel();
		menu.add(new JLabel("Locus: "));

		Object[] loci = surfaceMap.keySet().toArray();

		Comparator<Object> lociCompare= new Comparator<Object>() {
			 
		    public int compare(Object s1, Object s2) {
	 
		    	String str1 = (String)s1;
		    	String str2 = (String)s2;
		    	
		    	Matcher m1 = locusRegEx.matcher(str1);
		    	m1.find();
		    	Integer i1 = Integer.parseInt(m1.group(1));
		    	
		    	Matcher m2 = locusRegEx.matcher(str2);
		    	m2.find();
		    	Integer i2 = Integer.parseInt(m2.group(1));
		    	
		    	
		    	return i1.compareTo(i2);
	 
		      //descending order
		      //return fruitName2.compareTo(fruitName1);
		    }
	 
		};
		
		Arrays.sort((Object[])loci,lociCompare);

		JComboBox<Object> lociComboBox = new JComboBox<Object>(loci);

		lociComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String lociName = (String)cb.getSelectedItem();
				
				mainPanel.remove(currentMainComponent);
				currentSurface = surfaceMap.get(lociName);
				c = AudiogramChartFactory.builtChart( currentSurface );
				c.getView().setViewPoint(defaultViewpoint, true);
				currentMainComponent = (Component) c.getCanvas();

				mainPanel.add( currentMainComponent,BorderLayout.CENTER);
				//revalidate();
				invalidate();
				validate();
				updateSlider();
			}
		});;


		menu.add(lociComboBox);
		
		final JButton addAudiogram = new JButton("Add Patient Audiogram");
		addAudiogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Adding audiogram");
				PatientAudiogramDialog dialog = new PatientAudiogramDialog(SwingUtilities.windowForComponent(addAudiogram));
			}
		});
		menu.add(addAudiogram);
		
		mainPanel.add(menu,BorderLayout.NORTH);
		
		
		c= AudiogramChartFactory.builtChart( surfaceMap.get( lociComboBox.getItemAt(0) ) );
		c.getView().setViewPoint(defaultViewpoint, true);
		currentSurface = surfaceMap.get( lociComboBox.getItemAt(0) );

		currentMainComponent = (Component)c.getCanvas();
		mainPanel.add( currentMainComponent,BorderLayout.CENTER);
		
		audiogramPanel = new JPanel(new BorderLayout());
		audiogramGraph = new  AudiogramGraph();
		audiogramPanel.add(audiogramGraph,BorderLayout.CENTER);
		
		JPanel sliderPanel = new JPanel(new BorderLayout());
		JLabel ageLabel = new JLabel("Audiogram Age:");
		sliderPanel.add(ageLabel, BorderLayout.NORTH);
		
		ageSlider = new JSlider(0, 65);
		ageSlider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				updateAudiogram();			
			}
		});;
		ageSlider.setMajorTickSpacing(10);
		ageSlider.setMinorTickSpacing(1);
		ageSlider.setPaintTicks(true);
		ageSlider.setPaintLabels(true);
		ageSlider.setSnapToTicks(true);
		
		sliderPanel.add(ageSlider,BorderLayout.CENTER);
		audiogramPanel.add(sliderPanel,BorderLayout.SOUTH);
	
		mainPanel.add(audiogramPanel,BorderLayout.EAST);

		add(mainPanel, BorderLayout.CENTER);

		
		//pack();
		//setLocationRelativeTo(null);
		setVisible(true);

		/*
		 * Useful code to find the current perspective
        c.getCanvas().addMouseController(new MouseListener() {

			public void mouseReleased(java.awt.event.MouseEvent e) {}

			public void mousePressed(java.awt.event.MouseEvent e) {	}

			public void mouseExited(java.awt.event.MouseEvent e) {	}

			public void mouseEntered(java.awt.event.MouseEvent e) {	}

			public void mouseClicked(java.awt.event.MouseEvent e) {

        	    ChartView view =(ChartView) c.getView();


        	    Coord3d eye = view.getCamera().getEye();
        	    System.out.println("eye: X:"+eye.x+" Y:"+eye.y+" Z:"+eye.z);
        	    Coord3d viewPoint = view.getViewPoint();
        	    System.out.println("ViewPoint: X:"+viewPoint.x+" Y:"+viewPoint.y+" Z:"+viewPoint.z);
        	    Coord3d scaling = view.getLastViewScaling();
        	    System.out.println("Scaling: X:"+scaling.x+" Y:"+scaling.y+" Z:"+scaling.z);

        	    view.setViewPoint(new Coord3d(5.6903043, 0.28719786, 225.16661), true);

			}
		});
		 */
		
		UserAudiogramData.addPlotListener(this);
		updateSlider();
	}

	
	private void updateSlider() {
		float currentAge = ageSlider.getValue();
		if(currentAge > currentSurface.maxAge)
			currentAge = currentSurface.maxAge;
		
		ageSlider.setMinimum((int) Math.floor(currentSurface.minAge));
		
		ageSlider.setMaximum((int) Math.floor(currentSurface.maxAge));
		ageSlider.setValue( (int) Math.floor(currentAge ) );
		ageSlider.updateUI();
		updateAudiogram();
		
	}
	
	public void updateAudiogram() {
		
		Audiogram a = new Audiogram();
		float age = ageSlider.getValue();
		float xValues[] = {1,2,3,4,4.5f,5,5.5f,6,6.5f,7};
		
		for(int i = 0; i< xValues.length;i++) {
			a.setFreqValue(i,(float) (130f-(float) currentSurface.mapper.f(xValues[i],age)));
		}
		a.setAge(age);
		a.setLocus( currentSurface.title );
		audiogramGraph.setAudiogram(a);
		//this.pack();
	}

	public void userDefinedPlotUpdated() {
		c = AudiogramChartFactory.builtChart( currentSurface );
		c.getView().setViewPoint(defaultViewpoint, true);
		currentMainComponent = (Component) c.getCanvas();

		mainPanel.add( currentMainComponent,BorderLayout.CENTER);
		
		invalidate();
		validate();
		if(UserAudiogramData.getHasAudiogram())
			ageSlider.setValue( Math.round( UserAudiogramData.getAudiogram().getAge() ) );
		updateSlider();
		
	}


}

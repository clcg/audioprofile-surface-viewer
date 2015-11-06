package demos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.IColorMappable;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.providers.StaticTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.ITickRenderer;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Jzy3dRotationProblem {

	static String[] freqString = {".125","",".25","",".5","","1","","2","","4","","8"};
	static String[] lociStrings = { "DFNA9", "DFNA8/12", "DFNA2A", "DFNA6/12/14", "GJB2" };


	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                init();
            }

        });
    }
    

    static void init() {
        Mapper mapper = new Mapper() {

            @Override
            public double f(double x, double y) {
            	 float     p00 =       2.782f;
            	 float     p10 =        18.1f;
            	 float     p01 =      -1.039f;
            	 float     p20 =      -1.888f;
            	 float     p11 =     -0.1633f;
            	 float     p02 =     0.06092f;
            	 float     p30 =     0.02815f;
            	 float     p21 =     0.01568f;
            	 float     p12 =   0.0008371f;
            	 float     p03 =  -0.0007139f;
            	return 130 - (p00 + p10*x + p01*y + p20*Math.pow(x, 2) + p11*x*y + p02*Math.pow(y,2) + p30*Math.pow(x,3) + p21*Math.pow(x,2)*y 
            	                    + p12*x*Math.pow(y,2) + p03*Math.pow(y,3));
            }

        };

        Range range = new Range(-3, 3);
        int steps = 80;

        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(new Range(1, 10), 10, new Range(0, 60), steps), mapper);
        surface.setColorMapper(new ColorMapper(new InvertedColorMapRainbow(), 0, 150, new Color(1, 1, 1, 1f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setBoundingBoxDisplayed(false);
        
        
        // With "awt", everything works. With "newt", various issues with mouse rotation/clicking, depending on JOGL version.
        Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, "awt"); // or "awt"
        chart.getScene().getGraph().add(surface);
        chart.addMouseController();
        chart.setAxeDisplayed(true);
        
        
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
                    return freqString[index];
                    
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
        
        double [] ticks = {1,2,3,4,5,6,7,8,9,10};
        StaticTickProvider a = new StaticTickProvider(ticks);
        chart.getAxeLayout().setXTickProvider(a);
        
        double []ticks2 = {0, 10, 20,30 ,40,50,60};
        a= new StaticTickProvider(ticks2);
        chart.getAxeLayout().setYTickProvider(a);
        
        double []ticks3 = {0, 10, 20,30 ,40,50,60,70,80,90,100,110,120,130};
        a= new StaticTickProvider(ticks3);
        chart.getAxeLayout().setZTickProvider(a);
        
        chart.getView().setBoundManual(new BoundingBox3d(1, 10, 0, 65, 0, 130));
        

        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 600));        
        
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add((Component) chart.getCanvas(),BorderLayout.CENTER);
        
        JPanel menu = new JPanel();
        menu.add(new JLabel("Locus: "));
        menu.add(new JComboBox<String>(lociStrings));
        mainPanel.add(menu,BorderLayout.NORTH);
        
        frame.add(mainPanel);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static class InvertedColorMapRainbow extends ColorMapRainbow {

		public Color getColor(double x, double y, double z, double zMin,
				double zMax) {
			// TODO Auto-generated method stub
			return super.getColor(x, y, z, zMin, zMax);
		}
		
		public Color getColor(IColorMappable colorable, double x, double y,
				double z) {
			// TODO Auto-generated method stub
			return super.getColor(colorable, x, y, z).negative();
		}
    	
    	@Override
		public Color getColor(IColorMappable colorable, double z) {
			// TODO Auto-generated method stub
			return super.getColor(colorable, z).negative();
		}
    	
    }

}
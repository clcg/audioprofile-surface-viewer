package edu.uiowa.mappers;

import org.jzy3d.plot3d.builder.Mapper;


public class Poly33Mapper extends Mapper{
	
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
	 
	 public Poly33Mapper(String coefs)
	 {
		 String[] parts = coefs.split("\t");
		 p00 =  Float.parseFloat(parts[0]);
		 p10 =  Float.parseFloat(parts[1]);
		 p01 =  Float.parseFloat(parts[2]);
		 p20 =  Float.parseFloat(parts[3]);
		 p11 =  Float.parseFloat(parts[4]);
		 p02 =  Float.parseFloat(parts[5]);
		 p30 =  Float.parseFloat(parts[6]);
		 p21 =  Float.parseFloat(parts[7]);
		 p12 =  Float.parseFloat(parts[8]);
		 p03 =  Float.parseFloat(parts[9]);
	 }
	 
	 @Override
	public double f(double x, double y) {
		return 130 - (p00 + p10*x + p01*y + p20*Math.pow(x, 2) + p11*x*y + p02*Math.pow(y,2) + p30*Math.pow(x,3) + p21*Math.pow(x,2)*y 
                 + p12*x*Math.pow(y,2) + p03*Math.pow(y,3));
	}

	public double value(double x, double y) {
		return (p00 + p10*x + p01*y + p20*Math.pow(x, 2) + p11*x*y + p02*Math.pow(y,2) + p30*Math.pow(x,3) + p21*Math.pow(x,2)*y 
                 + p12*x*Math.pow(y,2) + p03*Math.pow(y,3));
	}
	
}

package edu.uiowa.mappers;

import org.jzy3d.plot3d.builder.Mapper;

import dataset.Audiogram;

public class AudiogramMapper extends Mapper{

	 int []freqMap = {0,1,2,3,5,7,9};
	 
	 Audiogram audiogram;
	 
	 public AudiogramMapper(Audiogram audiogram)
	 {
		 this.audiogram = audiogram;
	 }

	 @Override
	public double f(double x, double y) {
		 //Y = age
		 //X = freq
		 
		System.out.println("X: "+x+" Y: "+y);
		
		int idx = (int) Math.round(x)-1;
		
		return 130 - audiogram.interpolate((float) x - 1);
	 }
	 
}
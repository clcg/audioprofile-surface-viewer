package dataset;

import java.util.Vector;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

/**
 * 
 * @author krtaylor
 *
 */
public class Audiogram implements Cloneable{
	
	public static final int NUM_FREQ = 10;
	String patient;
	float age;
	String ear;
	String locus;
	


	//Freq
	public Float freq_125 = Float.MIN_VALUE;
	public Float freq_250 = Float.MIN_VALUE;
	public Float freq_500 = Float.MIN_VALUE ;
	public Float freq_1k = Float.MIN_VALUE;
	public Float freq_1_5k = Float.MIN_VALUE;
	public Float freq_2k = Float.MIN_VALUE;
	public Float freq_3k = Float.MIN_VALUE;
	public Float freq_4k = Float.MIN_VALUE;
	public Float freq_6k = Float.MIN_VALUE;
	public Float freq_8k = Float.MIN_VALUE;
	
	public boolean changedByRule = false;
	public boolean rejected = false;
	public String rejectText = "";
	public boolean reviewed = false;

	public boolean accepted = false;
	public int nid = -1;
	
	
	/**
	 * This is used if you want to add extra features to the csv output.
	 * Note the title is added to the dataset
	 * i.e. coefficients of a line fitted
	 */
	Vector<String> metaData = new Vector<String>();
	
	public Float values[] = new Float[10];
	private Audiogram reviewedAudiogram = null;
	
	public Audiogram()
	{
		
	}
	
	public Audiogram(float allValues)
	{
		for(int i = 0; i < 10; i++) {
			this.setFreqValue(i, allValues);
		}
	}

	public XYSeries generateXYSeries() {
		XYSeries series = new XYSeries(patient+" : "+age);
		
		if( freq_125  != Float.MIN_VALUE )
			series.add(1, freq_125);
			
		if( freq_250 != Float.MIN_VALUE )
			series.add(2, freq_250);
			
		if( freq_500  != Float.MIN_VALUE )
			series.add(3, freq_500);
			
		if( freq_1k   != Float.MIN_VALUE )
			series.add(4, freq_1k);
			
		if( freq_1_5k != Float.MIN_VALUE )
			series.add(4.5, freq_1_5k);
			
		if( freq_2k  != Float.MIN_VALUE )
			series.add(5, freq_2k);
			
		if( freq_3k  != Float.MIN_VALUE )
			series.add(5.5, freq_3k);
			
		if( freq_4k  != Float.MIN_VALUE )
			series.add(6, freq_4k);
			
		if( freq_6k  != Float.MIN_VALUE )
			series.add(6.5, freq_6k);
			
		if( freq_8k  != Float.MIN_VALUE )
			series.add(7, freq_8k);
		
		
		return series;
	}
	


	/**
	 * @return the patient
	 */
	public String getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(String patient) {
		this.patient = patient;
	}

	/**
	 * @return the age
	 */
	public float getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(float age) {
		this.age = age;
	}

	/**
	 * @return the ear
	 */
	public String getEar() {
		return ear;
	}

	/**
	 * @param ear the ear to set
	 */
	public void setEar(String ear) {
		this.ear = ear;
	}
	
	public void setFreqValue(int index, float value) {
			if(index == 0)
				freq_125 = value;
			if(index == 1)
				freq_250 = value; 
			if(index == 2)
				freq_500  = value;
			if(index == 3)
				freq_1k  = value;
			if(index == 4)
				freq_1_5k = value;
			if(index == 5)
				freq_2k  = value;
			if(index == 6)
				freq_3k  = value;
			if(index == 7)
				freq_4k  = value;
			if(index == 8)
				freq_6k  = value;
			if(index == 9)
				freq_8k  = value;
		
	}
	
	public Float getFreqValue(int index) {
		
		if(index == 0)
			return freq_125 ;
			if(index == 1)
			return freq_250 ;
			if(index == 2)
			return freq_500 ;
			if(index == 3)
			return freq_1k ;
			if(index == 4)
			return freq_1_5k;
			if(index == 5)
			return freq_2k ;
			if(index == 6)
			return freq_3k ;
			if(index == 7)
			return freq_4k ;
			if(index == 8)
			return freq_6k ;
			if(index == 9)
			return freq_8k ;
			
			return freq_125;
		
	}

	public int numFreqIndicies() {
		int count = 0;
		
		for(int i = 0; i < 10; i++) {
			
			float temp = getFreqValue(i);
			if(Math.abs(getFreqValue(i)) > Float.MIN_VALUE ) {
				count++;
			} else {
				temp = Math.abs(getFreqValue(i));
				
			}
		}
		
		return count;
	}
	
	/**
	 * @return the locus
	 */
	public String getLocus() {
		if(locus == null)
			return "";
		return locus;
	}

	/**
	 * @param locus the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}

	/**
	 * Linearly interpolates between frequency values 
	 * @param freq
	 * @return
	 */
	public float interpolate(float freq) {
		float value = 0;
		
		if(freq > 3) {
			//Take care of the logarithm scaling of higher frequencies
			freq = 2.0f*freq-3f;
		}
		
		int lowFreq = (int) Math.floor(freq);
		int highFreq = (int) Math.ceil(freq);
		
		//Determine if integer frequency value
		if(lowFreq == highFreq) {
			System.out.println("Integer frequency value");
			return getFreqValue(lowFreq);
		} 
		
		
		float lowValue = getFreqValue(lowFreq);
		float highValue = getFreqValue(highFreq);
		
		//http://en.wikipedia.org/wiki/Linear_interpolation
		float ratio = (freq - lowFreq) / (highFreq - lowFreq);
		value = lowValue + (highValue - lowValue) * ratio;
		return value;
		
	}
	
	
	public Object clone() throws CloneNotSupportedException {

	    Audiogram clone=(Audiogram)super.clone();

	    // make the shallow copy of the object of type Department
	    
	    clone.patient = this.patient;
	    clone.age = this.age;
	    clone.ear = this.ear;
	    
	    for(int i = 0; i < 10; i++)
	    	clone.setFreqValue(i,  this.getFreqValue(i) );
	    
	    clone.nid = -1;
	    
	    return clone;

	  }
	
}

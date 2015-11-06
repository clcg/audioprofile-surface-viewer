package edu.uiowa;

import java.util.Vector;

import dataset.Audiogram;

public class UserAudiogramData {
	/**
	 * An audiogram entered by the user via the GUI
	 */
	static private boolean hasAudiogram;
	static Audiogram audiogram;
	static Vector<UserPlotListener> plotListeners = new Vector<UserPlotListener>();
	
	static public void addPlotListener( UserPlotListener listener ) {
		plotListeners.add(listener);
	}
	
	static public void setAudiogram(Audiogram a) {
		audiogram = a;
		hasAudiogram = true;
		for(UserPlotListener l : plotListeners) {
			l.userDefinedPlotUpdated();
		}
	}
	
	static public void setHasAudiogram(boolean b) {
		hasAudiogram = b;
		for(UserPlotListener l : plotListeners) {
			l.userDefinedPlotUpdated();
		}
	}

	public static boolean getHasAudiogram() {
		// TODO Auto-generated method stub
		return hasAudiogram;
	}

	public static Audiogram getAudiogram() {
		// TODO Auto-generated method stub
		return audiogram;
	}
	
}

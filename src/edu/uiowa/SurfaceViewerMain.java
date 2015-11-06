package edu.uiowa;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SurfaceViewerMain {
	
	public static void main(String[] args) {
		Vector<Surface> surfaces = DatasetLoader.loadDataset();
		MainGUI gui = new MainGUI(surfaces);
		
	}

}

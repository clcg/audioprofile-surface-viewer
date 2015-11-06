package demos;

import gui.AudiogramGraph;

import java.awt.EventQueue;

import javax.swing.JFrame;

import dataset.Audiogram;

public class AudiogramTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		AudiogramGraph ag = new  AudiogramGraph();
		frame.add(ag);
		frame.setSize(500, 550);
		
		Audiogram audiogram = new Audiogram();
		for(int i = 0; i < 10; i++)
			audiogram.setFreqValue(i, i*10);
		audiogram.setEar("C");
		ag.setAudiogram(audiogram);
		ag.updateGraph();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    }
	
}

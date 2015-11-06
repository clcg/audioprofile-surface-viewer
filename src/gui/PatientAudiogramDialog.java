package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dataset.Audiogram;
import edu.uiowa.UserAudiogramData;
import edu.uiowa.UserPlotListener;

public class PatientAudiogramDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] freqLabels = {"Age: ","125 Hz (HL dB): ", "250 Hz (HL dB): ", "500 Hz (HL dB): ", "1 kHz (HL dB): ","1.5 kHz (HL dB): ","2 kHz (HL dB): ", "3 kHz  (HL dB): ","4 kHz  (HL dB): ","6 kHz  (HL dB): ","8 kHz  (HL dB): "};
	private Vector<JTextField> textFields = new Vector<JTextField>();
	
	private static Vector<String> prevTextFieldValues = new Vector<String>();
	
	public PatientAudiogramDialog(Window window) {
		super(window);
		System.out.println("Creating dialog");
		

		setupGUI();
		
		this.setModal(true);
		this.setSize(325, 435);
		this.setVisible(true);
			
	}
	
	private void setupGUI() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel audiogramPanel = new JPanel();
		audiogramPanel.setLayout(new SpringLayout());
	
			
		int numPairs = freqLabels.length;

		//Create and populate the panel.
		JPanel p = new JPanel(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
		    JLabel l = new JLabel(freqLabels[i], JLabel.TRAILING);
		    p.add(l);
		    JTextField textField = new JTextField(10);
		    
		    //Just for testing
		    //textField.setText("2");
		    if(prevTextFieldValues.size() == numPairs) {
		    	textField.setText( prevTextFieldValues.get(i) );
		    } else {
		    	prevTextFieldValues.add("");
		    }
		    
		    l.setLabelFor(textField);
		    p.add(textField);
		    textFields.add(textField);
		    
		}

		//Lay out the panel.
		SpringUtilities.makeCompactGrid(p,
		                                numPairs, 2, //rows, cols
		                                6, 6,        //initX, initY
		                                6, 6);       //xPad, yPad
		
		
		panel.add(p,BorderLayout.CENTER);
		
		
		JPanel buttonPanel = new JPanel();
		
		JButton clearButton = new JButton("Clear");
		
		//buttonPanel.add( clearButton );
		
		JButton removeButton = new JButton("Remove From Plot");
		removeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				UserAudiogramData.setHasAudiogram( false );
//				UserAudiogramData.setAudiogram( null );
				setVisible(false);
			}
		});
		buttonPanel.add(removeButton);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("Adding audiogram");
				Audiogram a = generateAudiogram();
				saveValues();
				
				UserAudiogramData.setAudiogram( a );
				UserAudiogramData.setHasAudiogram( true );
				
				if(a != null)
					setVisible(false);
				
			}
		});
		
		buttonPanel.add(addButton);
		panel.add(buttonPanel,BorderLayout.SOUTH);
		
		this.setContentPane(panel);
		
	}
	
	
	/**
	 * 
	 * @return Audiogram or null is a validate audiogram couldn't be constructed from the values
	 */
	public Audiogram generateAudiogram() {
		
		boolean hasAge = false;
		float age = 1;
		try {
			age = Float.parseFloat(textFields.get(0).getText());
			if(age > 0)
				hasAge = true;
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		if(!hasAge) {
			JOptionPane.showMessageDialog(null, "Please enter an age.");
			return null;
		}
		
		int numFrequencyValues = this.numFreq();
		if(numFreq() != freqLabels.length - 1) {
			JOptionPane.showMessageDialog(null, "Please enter valid values for each frequency. (-10 to 130)");
			return null;
		}
		
		Audiogram a = new Audiogram();
		a.setAge(age);
		a.setPatient("Demo");
		for(int i = 1; i < textFields.size(); i++) {
			try {
				double value = Double.parseDouble(textFields.get(i).getText());
				a.setFreqValue(i - 1, (float)value);
				System.out.println(i-1+" "+value);
			} catch (NumberFormatException e) {
				
			}
		}
		
		return a;
		
	}
	
	private int numFreq() {
		int count = 0;
		for(int i = 1; i < textFields.size(); i++) {
			try {
				double value = Double.parseDouble(textFields.get(i).getText());
				count++;
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
		}
		return count;
	}
	
	
	private void saveValues() {
		for(int i = 0; i < textFields.size(); i++) {
			prevTextFieldValues.set(i, textFields.get(i).getText());
		}
	}

	
	
	
	
}

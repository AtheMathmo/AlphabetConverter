package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import converter.Converter;


public class ConverterGUI {
	// Current program ver.
	public static final double VERSION = 0.0;
		

	    public static void main(String[] args) {
	    	// Make it look nicer.
	    	try {
	    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    	
	    	
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
	    
	    private static void createAndShowGUI() {
	        System.out.println("Created GUI on EDT? "+
	                SwingUtilities.isEventDispatchThread());
	        
	        JFrame f = new JFrame("Converter GUI");
	        ResultDisplay resultDisplay = new ResultDisplay();
	        f.add(resultDisplay, BorderLayout.CENTER);
	        f.add(new InputFields(resultDisplay), BorderLayout.NORTH);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        // Add buttons and rendering panel to frame.
	        f.pack();
	        f.setVisible(true);
	    }
	}

class ResultDisplay extends JPanel {
	
	JLabel display;
	
	public ResultDisplay() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
		
		initComponents();
	}
	
	public void initComponents() {
		display = new JLabel("Enter inputs above");
		display.setFont(new Font("Verdana",1,40));
		this.add(display, BorderLayout.CENTER);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(800,600);
	}
	
	public void printResult(String displayText, Font displayFont) {
		display.setFont(displayFont);
		display.setText(displayText);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		//Draw Text
		g.drawString("Alphabet Converter v"+ConverterGUI.VERSION, 10, 20);
		g.drawString("Author: James Lucas",10,40);
	}
	
}

class InputFields extends JPanel implements ActionListener {
	Font resultFont = new Font("Verdana",1,40);
	Font exceptionFont = new Font("Verdana",1,20);
	
	ResultDisplay resultDisplay;
	
	JTextField input;
	JTextField sourceAlphabetInput;
	JTextField targetAlphabetInput;
	
	JButton submitButton;
	
	public InputFields(ResultDisplay resultDisplay) {
		this.resultDisplay = resultDisplay;
		setBorder(BorderFactory.createBevelBorder(2));
		setLayout(new GridLayout(1,3,30,10));
		
		initComponents();
	}
	
	public void initComponents() {
		
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);
		
		input = new JTextField("Input");
		sourceAlphabetInput = new JTextField("Source Alphabet");
		targetAlphabetInput = new JTextField("Target Alphabet");
			
		this.add(input);
		this.add(sourceAlphabetInput);
		this.add(targetAlphabetInput);
		this.add(submitButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("submit".equals(e.getActionCommand())) {
			//DO STUFF
			String inputText = input.getText();
			String sourceAlphabet = sourceAlphabetInput.getText();
			String targetAlphabet = targetAlphabetInput.getText();
			System.out.println(inputText+","+sourceAlphabet+","+targetAlphabet);
			String output = "";
			try {
				output = Converter.Convert(inputText, sourceAlphabet, targetAlphabet);
				resultDisplay.printResult(output, resultFont);
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				resultDisplay.printResult(e1.getMessage(), exceptionFont);
			}
			
			System.out.print(output);
		}
	}
}


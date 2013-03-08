package com.github.bugra.MeasureRectangle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


@SuppressWarnings("serial")
public class MeasureRectangle extends JApplet {
    RectanglesCanvas canvas;
    
    JTextField bottomTextField;
    JTextField bottomATextField;
    JTextField bottomBTextField;
    
    JTextField topTextField;
    JTextField topATextField;
    JTextField topBTextField;
    
    JTextField topRectangleMeasure;
    JTextField bottomRectangleMeasure;
    
    // TODO Ratio of Rectangle Measures
    private double topRectangleValue = 10.0;
    private double bottomRectangleValue = 10.0;
    private double ratioHeightofRectangles = (double)topRectangleValue/ bottomRectangleValue;
    
    private final int MIN_SLIDER = 0;
    private final int MAX_SLIDER = 100;
    private final int INITIAL_SLIDER = 10;
    
    private final int INITIAL_WIDTH = 800;
    private final int INITIAL_HEIGHT = 600;
    
    
    // Initial value of sliders
    double tempWidth = 0.5;

    public void init() {

        // 1. Get the content pane and assign layout

        Container container = getContentPane();
        
        // 2. Add the canvas with rectangles

        canvas = new RectanglesCanvas();
        container.add(canvas);

        // 3. Create the control panel with titled border

        JPanel belowPanel = new JPanel();
        TitledBorder southBorder = new TitledBorder("Change the ratio of below rectangle");
        belowPanel.setBorder(southBorder);

        // 4. Create a label, slider and text field

        JLabel label = new JLabel("Below Rectangle: ", JLabel.RIGHT);

        JSlider bottomSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        									MAX_SLIDER, INITIAL_SLIDER);
        bottomSlider.addChangeListener(new SliderListener());
        
        bottomTextField = new JTextField("0.65", 4);
        bottomATextField = new JTextField("aTextField");
        bottomBTextField = new JTextField("bTextField");
        // 5. Add the label, slider and text field to the panel

        belowPanel.add(label);
        belowPanel.add(bottomSlider);
        belowPanel.add(bottomTextField);
        belowPanel.add(bottomATextField);
        belowPanel.add(bottomBTextField);

        // 6. Add the north panel to the frame

        container.add(BorderLayout.SOUTH, belowPanel);
        
        JPanel topPanel = new JPanel();
        TitledBorder topBorder = new TitledBorder("Change the ratio of above rectangle");
        topPanel.setBorder(topBorder);

        // 4. Create a label, slider and text field

        JLabel topLabel = new JLabel("Above Rectangle", JLabel.RIGHT);

        JSlider topSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        								MAX_SLIDER, INITIAL_SLIDER);
        
        topSlider.addChangeListener(new SliderListener());

        topTextField = new JTextField("0.65", 4);
        topATextField = new JTextField("aTextField");
        topBTextField = new JTextField("bTextField");
        // 5. Add the label, slider and text field to the panel

        topPanel.add(topLabel);
        topPanel.add(topSlider);
        topPanel.add(topTextField);
        topPanel.add(topATextField);
        topPanel.add(topBTextField);
        
        container.add(BorderLayout.NORTH, topPanel);
        
        topRectangleMeasure = new JTextField();
        topRectangleMeasure.setText("10");
        topRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  //warn();
        	  }
        	  public void removeUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());  
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  warn();
        	  }
        	  public void insertUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());  
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  //warn();
        	  }
        	  
        	  public void warn() {
        	   	  if (Integer.parseInt(topRectangleMeasure.getText())<=0){
        	   		  JOptionPane.showMessageDialog(null,
        	   				  "Error: Please enter number bigger than 0", "Error Massage",
        	          JOptionPane.ERROR_MESSAGE);
        	     }
        	  }
        	});
        
        bottomRectangleMeasure = new JTextField();
        bottomRectangleMeasure.setText("10");
        bottomRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  //warn();
        	  }
        	  public void removeUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());  
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  warn();
        	  }
        	  public void insertUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
        		  ratioHeightofRectangles = topRectangleValue / bottomRectangleValue;
        		  //warn();
        	  }
        	  
        	  public void warn() {
        	   	  if (Integer.parseInt(bottomRectangleMeasure.getText())<=0){
        	   		  JOptionPane.showMessageDialog(null,
        	   				  "Error: Please enter number bigger than 0", "Error Massage",
        	          JOptionPane.ERROR_MESSAGE);
        	     }
        	  }
        	});
        
        topRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        bottomRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(50, 50));
        TitledBorder westBorder = new TitledBorder("Ratio");
        //JSeparator separator = new JSeparator();
        westPanel.setBorder(westBorder);
        westPanel.setLayout(new GridLayout(0,1));
        westPanel.add(topRectangleMeasure);
        westPanel.add(new JSeparator(SwingConstants.VERTICAL));
        westPanel.add(new JSeparator(SwingConstants.VERTICAL));
        westPanel.add(new JSeparator(SwingConstants.VERTICAL));
        westPanel.add(new JSeparator(SwingConstants.VERTICAL));
        //westPanel.add(new JSeparator(SwingConstants.VERTICAL));
        westPanel.add(bottomRectangleMeasure);        
        container.add(BorderLayout.WEST, westPanel);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        
        this.addComponentListener(new ComponentAdapter() {
        	  public void componentResized(ComponentEvent event) {
        	     canvas.setSizeCanvas(canvas.getSize());
        	     canvas.setBottomRectangleHeight(canvas.getHeight() - canvas.getSpacingBetweenRectangles() - topRectangleMeasure.getHeight());
        	  }
        	});
    }
    
    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider tempSlider = (JSlider) e.getSource();
            tempWidth = (tempSlider.getValue() / (double)MAX_SLIDER);
            bottomTextField.setText(Double.toString(tempWidth));
            int width = (int) ((canvas.getWidthCanvas() - 
	 							(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
            
	 		canvas.setTopRectangleWidth(width);
	 		canvas.setBottomRectangleWidth(width);
	 		System.out.println(getRatioHeightOfRectangles());
            canvas.repaint();
        }
        public double getRatioHeightOfRectangles(){
        	return ratioHeightofRectangles;
        }
    }
    
}
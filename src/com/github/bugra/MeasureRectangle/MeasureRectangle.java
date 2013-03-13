package com.github.bugra.MeasureRectangle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;

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
    
    JPanel topPanel;
    JPanel bottomPanel;
    JPanel westPanel;
    JPanel eastPanel;
    
    // Functional slider for rectangle resizing
    JSlider topSlider;
    JSlider bottomSlider;
    
    // Non-functional sliders which will reside in the east panel?
    JSlider redTopSlider;
    JSlider redBottomSlider;
    // TODO Ratio of Rectangle Measures
    public static final double INITIAL_TOP_RECTANGLE_VALUE = 1.0;
    public static final double INITIAL_BOTTOM_RECTANGLE_VALUE = 1.0;
    
    // Border Titles for East and West panel
    public static final String westPanelTitle = "Red Sliders";
    public static final String eastPanelTitle = "Ratio";
    
    // Length of text Fields
    public static final int LENGTH_OF_TEXT_FIELD = 3;
    
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final JSeparator SEPARATOR = new JSeparator(SwingConstants.VERTICAL);
    private double topRectangleValue = INITIAL_TOP_RECTANGLE_VALUE;
    private double bottomRectangleValue = INITIAL_BOTTOM_RECTANGLE_VALUE;
    
    public static final String INITIAL_VALUE_LABEL = "1";
    
    private final int MIN_SLIDER = 0;
    private final int MAX_SLIDER = 100;
    private final int INITIAL_SLIDER = 10;
    
    private final int INITIAL_WIDTH = 800;
    private final int INITIAL_HEIGHT = 600;
    
    // Initial value of sliders
    double tempWidth = 0.5;

    public void init() {
    	
    	Container container = getContentPane();
        canvas = new RectanglesCanvas();
        
        //canvas.add(gridComponent);
        container.add(canvas);
        
        bottomPanel = new JPanel();
        TitledBorder southBorder = new TitledBorder("Change the ratio of below rectangle");
        bottomPanel.setBorder(southBorder);
        JLabel label = new JLabel("Below Rectangle: ", JLabel.RIGHT);
        bottomSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        									MAX_SLIDER, INITIAL_SLIDER);
        bottomSlider.addChangeListener(new SliderListener());
        
        bottomTextField = new JTextField(Double.toString((double) INITIAL_SLIDER / MAX_SLIDER), 
        									LENGTH_OF_TEXT_FIELD);
        bottomATextField = new JTextField("aTextField");
        bottomBTextField = new JTextField("bTextField");

        bottomPanel.add(label);
        bottomPanel.add(bottomSlider);
        bottomPanel.add(bottomTextField);
        bottomPanel.add(bottomATextField);
        bottomPanel.add(bottomBTextField);

        container.add(BorderLayout.SOUTH, bottomPanel);
        
        topPanel = new JPanel();
        TitledBorder topBorder = new TitledBorder("Change the ratio of above rectangle");
        topPanel.setBorder(topBorder);

        JLabel topLabel = new JLabel("Above Rectangle", JLabel.RIGHT);

        topSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        								MAX_SLIDER, INITIAL_SLIDER);
        
        topSlider.addChangeListener(new SliderListener());
        topTextField = new JTextField(Double.toString((double) INITIAL_SLIDER / MAX_SLIDER), 
        									LENGTH_OF_TEXT_FIELD);
        
        topATextField = new JTextField("aTextField");
        topBTextField = new JTextField("bTextField");

        topPanel.add(topLabel);
        topPanel.add(topSlider);
        topPanel.add(topTextField);
        topPanel.add(topATextField);
        topPanel.add(topBTextField);
        
        // INITIALIZATION
        tempWidth = (int) (bottomSlider.getValue() / (double)MAX_SLIDER);
        canvas.setTopRectangleWidth((int) ((canvas.getWidthCanvas() - 
					(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth));
 		canvas.setBottomRectangleWidth((int) ((canvas.getWidthCanvas() - 
					(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth));

 		container.add(BorderLayout.NORTH, topPanel);
        topRectangleMeasure = new JTextField();
        topRectangleMeasure.setText(INITIAL_VALUE_LABEL);
        topRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  
        	  public void changedUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  //warn();
        	  }
        	  
        	  public void removeUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());  
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  warn();
        	  }
        	  
        	  public void insertUpdate(DocumentEvent e) {
        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());  
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  //warn();
        	  }
        	  
        	  public void warn() {
        	   	  if (Integer.parseInt(topRectangleMeasure.getText()) <= 0){
        	   		  JOptionPane.showMessageDialog(null,
        	   				  "Error: Please enter number bigger than 0", "Error Massage",
        	          JOptionPane.ERROR_MESSAGE);
        	     }
        	  }
        	});
        
        bottomRectangleMeasure = new JTextField();
        bottomRectangleMeasure.setText(INITIAL_VALUE_LABEL);
        bottomRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  
        	  public void changedUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  //warn();
        	  }
        	  
        	  public void removeUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());  
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  warn();
        	  }
        	  
        	  public void insertUpdate(DocumentEvent e) {
        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
        		  canvas.setBottomRectangleValue(bottomRectangleValue);
        		  canvas.setTopRectangleValue(topRectangleValue);
        		  canvas.setSizeCanvas(canvas.getSize());
        		  canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
							canvas.getTopRectangleHeight() + 
							canvas.getSpacingBetweenRectangles());
        		  canvas.setTopRectangleHeight(canvas.getTopRectangleHeight());
        		  canvas.setBottomRectangleHeight(canvas.getBottomRectangleHeight());
        		  tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        		  double ratioOfRectangles = canvas.getTopRectangleValue() / 
   					   canvas.getBottomRectangleValue();
        		  bottomTextField.setText(Double.toString(tempWidth));
        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
        		  canvas.repaint();
        		  //warn();
        	  }
        	  
        	  public void warn() {
        	   	  if (Integer.parseInt(bottomRectangleMeasure.getText()) <= 0){
        	   		  JOptionPane.showMessageDialog(null,
        	   				  "Error: Please enter number bigger than 0", "Error Massage",
        	          JOptionPane.ERROR_MESSAGE);
        	     }
        	  }
        	  
        	});
        
        topRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        bottomRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        
        westPanel = new JPanel();
        eastPanel = new JPanel();
        
        westPanel.setPreferredSize(new Dimension(50, 50));
        //eastPanel.setPreferredSize(new Dimension(50, 50));
        
        TitledBorder westBorder = new TitledBorder(westPanelTitle);
        TitledBorder eastBorder = new TitledBorder(eastPanelTitle);
        
        
        // WEST PANEL DESIGN
        westPanel.setBorder(westBorder);
        westPanel.setLayout(new GridLayout(0,1));
        westPanel.add(topRectangleMeasure);
        westPanel.add(SEPARATOR);
        westPanel.add(SEPARATOR);
        westPanel.add(SEPARATOR);
        westPanel.add(SEPARATOR);
        westPanel.add(bottomRectangleMeasure);        
        
        // Initialize the non-functional sliders in the east panel
        redTopSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
				MAX_SLIDER, INITIAL_SLIDER);
        redTopSlider.addChangeListener(new SliderListener());
        
        redBottomSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
				MAX_SLIDER, INITIAL_SLIDER);
        redBottomSlider.addChangeListener(new SliderListener());
        
        // EAST PANEL DESIGN
        eastPanel.setBorder(eastBorder);
        eastPanel.setLayout(new GridLayout(0, 1));
        eastPanel.add(redTopSlider);
        eastPanel.add(SEPARATOR);
        eastPanel.add(redBottomSlider);
        
        container.add(BorderLayout.EAST, eastPanel);
        container.add(BorderLayout.WEST, westPanel);
        
       
        
        
        
        
        
        
        
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

        // GRID
        Grids grid = new Grids(200, 200, 2, 10);
	    add(grid);
        
        this.addComponentListener(new ComponentAdapter() {
        	  public void componentResized(ComponentEvent event) {
        	     canvas.setSizeCanvas(canvas.getSize());
        	     tempWidth = (bottomSlider.getValue() / (double) MAX_SLIDER);
        	     int width = (int) ((canvas.getWidthCanvas() - 
							(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
        	     canvas.setTopRectangleWidth(width);
     	 		 canvas.setBottomRectangleWidth(width);
     	 		 canvas.repaint();
        	  }
        });
    }
    
    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
        	canvas.setSizeCanvas(canvas.getSize());
            JSlider tempSlider = (JSlider) e.getSource();
            tempWidth = (tempSlider.getValue() / (double)MAX_SLIDER);
            topSlider.setValue(tempSlider.getValue());
            bottomSlider.setValue(tempSlider.getValue());
            double ratioOfRectangles = canvas.getTopRectangleValue() / 
					   canvas.getBottomRectangleValue();
            bottomTextField.setText(Double.toString(tempWidth));
            topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth)));
            int width = (int) ((canvas.getWidthCanvas() - 
	 							(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
            canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
						canvas.getTopRectangleHeight() + 
						canvas.getSpacingBetweenRectangles()+10);
	 		canvas.setTopRectangleWidth(width);
	 		canvas.setBottomRectangleWidth(width);
            canvas.repaint();
        }
    }
    
}
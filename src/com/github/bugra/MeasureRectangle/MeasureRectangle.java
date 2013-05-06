package com.github.bugra.MeasureRectangle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.NumericShaper;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;


@SuppressWarnings("serial")
public class MeasureRectangle extends JApplet {
	Container container;
	RectanglesCanvas canvas;
    
    JTextField bottomTextField;
    JTextField topTextField;
    
    JTextField topRectangleMeasure;
    JTextField bottomRectangleMeasure;
    
    JTextField topNumeratorField;
    JTextField topDenominatorField;
    JTextField bottomNumeratorField;
    JTextField bottomDenominatorField;
    
    
    public int topNumerator = 0;
    public int topDenominator = 0;
    public int bottomNumerator = 0;
    public int bottomDenominator = 0;
    
    public Fraction topFraction;
    public Fraction bottomFraction;
    
    JButton topUndoButton;
    JButton bottomUndoButton;
    
    JPanel topPanel;
    JPanel bottomPanel;
    
    JSlider topSlider;
    JSlider bottomSlider;
    
    JCheckBox topCheckBox;
    JCheckBox bottomCheckBox;
    
    JLabel topLabel;
    
    int xPosition;
    
    Point tempPoint;
    JPopupMenu pop;
    JLabel labelPop;
    
    public static final double INITIAL_TOP_RECTANGLE_VALUE = 1.0;
    public static final double INITIAL_BOTTOM_RECTANGLE_VALUE = 1.0;
    
    public static final double INITIAL_TOP_ITERATION_MEASURE = 0;
    public static final double INITIAL_BOTTOM_ITERATION_MEASURE = 0;
    
    public static final int TOP_LABEL_MIN_VALUE = -375;
    public static final int TOP_LABEL_MAX_VALUE = 385;
    public static final int TOP_LABEL_Y_POSITION = 55;
    
    // UNDO BUTTON INITIALIZATION
    public static final String TOP_UNDO_BUTTON_TEXT = "UNDO";
    public static final String BOTTOM_UNDO_BUTTON_TEXT = "UNDO";
    
    // Length of text Fields
    public static final int LENGTH_OF_TEXT_FIELD = 3;
    
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final JSeparator SEPARATOR = new JSeparator(SwingConstants.VERTICAL);
    
    private double topRectangleValue;
    private double bottomRectangleValue;
    
    private UndoManager topUndoManager;
    private UndoManager bottomUndoManager;
    
    public static final String INITIAL_VALUE_LABEL = "1";
    
    public static final Dimension WEST_PANEL_SIZE = new Dimension(50, 50);
    
    private int MIN_SLIDER = 0;
    private int MAX_SLIDER;
    private int TOP_INITIAL_SLIDER;
    private int BOTTOM_INITIAL_SLIDER;
    public int commonFactor;
    
    
    private int TOTAL_RANGE;
	private double LABEL_MOVEMENT_VALUE;
    
    private final int INITIAL_WIDTH = 800;
    private final int INITIAL_HEIGHT = 600;
    
    private final String[] DENOMINATOR_VALUES = {" ", "1", "2", "3", "4", "6", "12" };
    JComboBox topDenominatorComboBox;
    JComboBox bottomDenominatorComboBox;
    
    public static final int MAJOR_TICK_SPACING = 12;
    public static final int MINOR_TICK_SPACING = 1;
    
    private final Dimension INITIAL_SLIDER_DIMENSION = new Dimension(710, 50);
    // Initial value of sliders
    double tempWidth = 0.5;
    
    // OPTIONS READ FROM SETTINGS FILE
    private boolean asynchronous; 

    public void init() {
    	
    	// Initialization for Options
    	SettingsReader sr = SettingsReader.getInstance();
		HashMap<String, Integer> settings = new HashMap<String, Integer>();
		try {
			settings = sr.readFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Asynchronous or Synchronous Slider
    	asynchronous = settings.get("Asynchronous") == 1 ? true:false;
    	System.out.println(asynchronous);
    	
    	topRectangleValue = settings.get("h1");
    	bottomRectangleValue = settings.get("h2");
    	
    	TOTAL_RANGE = TOP_LABEL_MAX_VALUE - TOP_LABEL_MIN_VALUE;
    	LABEL_MOVEMENT_VALUE = TOTAL_RANGE / (double)MAX_SLIDER;
    	
        topRectangleMeasure = new JTextField();
        topNumeratorField = new JTextField();
        topFraction = new Fraction();
        topDenominatorComboBox = new JComboBox(DENOMINATOR_VALUES);
        topDenominatorField = new JTextField();
        topUndoManager = new UndoManager();
        topRectangleMeasure.getDocument().addUndoableEditListener(topUndoManager);
        topRectangleMeasure.setText(INITIAL_VALUE_LABEL);
        topRectangleMeasure.setText(String.valueOf(topRectangleValue));
        

        bottomRectangleMeasure = new JTextField(String.valueOf(bottomRectangleValue));
        bottomNumeratorField = new JTextField();
        bottomDenominatorField = new JTextField();
        bottomFraction = new Fraction();
        bottomDenominatorComboBox = new JComboBox(DENOMINATOR_VALUES);
        
        
        commonFactor = Fraction.getGcdNumbers((int)topRectangleValue, (int) bottomRectangleValue);
        TOP_INITIAL_SLIDER = (int) (topRectangleValue / commonFactor) * MAJOR_TICK_SPACING;
        BOTTOM_INITIAL_SLIDER = (int) (bottomRectangleValue / commonFactor) * MAJOR_TICK_SPACING;
        
        MAX_SLIDER = commonFactor * MAJOR_TICK_SPACING;
        System.out.println(topRectangleValue);
        topRectangleValue *= MAJOR_TICK_SPACING;
    	bottomRectangleValue *= MAJOR_TICK_SPACING;
    	
    	container = getContentPane();
        canvas = new RectanglesCanvas();
        
        bottomPanel = new JPanel();
        TitledBorder southBorder = new TitledBorder("Quantity B");
        bottomPanel.setBorder(southBorder);
        bottomSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        									MAX_SLIDER, BOTTOM_INITIAL_SLIDER);
        
        
        bottomSlider.setPreferredSize( INITIAL_SLIDER_DIMENSION );
        bottomTextField = new JTextField(Double.toString((double) BOTTOM_INITIAL_SLIDER / MAX_SLIDER), 
        									LENGTH_OF_TEXT_FIELD);
        bottomCheckBox =  new JCheckBox();
        bottomCheckBox.addItemListener(
        		new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED){
							bottomTextField.setVisible(false);
						}
						else{
							bottomTextField.setVisible(true);
						}
					}
				});

        topPanel = new JPanel();
        TitledBorder topBorder = new TitledBorder("Quantity A");
        topPanel.setBorder(topBorder);
        topLabel = new JLabel("",SwingConstants.CENTER);
        topLabel.setText("Label");
        topSlider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER, 
        								MAX_SLIDER, TOP_INITIAL_SLIDER);
        
        if(asynchronous){
        	topSlider.addChangeListener(new AsynchronousTopSliderListener());
            bottomSlider.addChangeListener(new AsynchronousBottomSliderListener());
        }else{
        	topSlider.addChangeListener(new SynchronousSliderListener());
        	bottomSlider.addChangeListener(new SynchronousSliderListener());
        }
        topSlider.setPreferredSize(INITIAL_SLIDER_DIMENSION);
        topSlider.setToolTipText(Integer.toString(topSlider.getValue()));
        
        topSlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        topSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        //topSlider.setPaintLabels(true);
        topSlider.setPaintTicks(true);
        topSlider.setPaintTrack(true);
        //topSlider.setSnapToTicks(true);
        
        topTextField = new JTextField(Double.toString((double) TOP_INITIAL_SLIDER / MAX_SLIDER), 
        									LENGTH_OF_TEXT_FIELD);
        topTextField = new JTextField(Double.toString(TOP_INITIAL_SLIDER), LENGTH_OF_TEXT_FIELD);
        
        topCheckBox =  new JCheckBox();
        topCheckBox.addItemListener(
        		new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED){
							topTextField.setVisible(false);
						}else{
							topTextField.setVisible(true);
						}
					}
				});
        
        // INITIALIZATION of the components
        tempWidth = (int) (bottomSlider.getValue() / (double)MAX_SLIDER);
        canvas.setTopRectangleWidth((int) ((canvas.getWidthCanvas() - 
					(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth));
 		canvas.setBottomRectangleWidth((int) ((canvas.getWidthCanvas() - 
					(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth));
 		

        topDenominatorComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
            	JComboBox cb = (JComboBox)e.getSource();
                String denominator = (String)cb.getSelectedItem();
                updateTopDenominator(denominator);

            }
            
            public void updateTopDenominator(String denominator){
            	topDenominator = denominator.equalsIgnoreCase(" ") ? 0: Integer.valueOf(denominator) ;
            	topFraction.setDenominator(topDenominator);
            }
        });

        
        topRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  if (topRectangleMeasure.getText() == null | topRectangleMeasure.getText().equals(" ")){
        			  //topRectangleMeasure.setText("0");
        		  }else{
	        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());
	        		  topFraction.setWhole((int)topRectangleValue);
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
	        		  bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	  
        	  public void removeUpdate(DocumentEvent e) {
        		  if (topRectangleMeasure.getText() == null | topRectangleMeasure.getText().equals(" ")){
        			  //topRectangleMeasure.setText("0");
        		  }else{
	        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText());
	        		  topFraction.setWhole((int)topRectangleValue);
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
	        		  bottomTextField.setText(Double.toString(tempWidth * MAX_SLIDER));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	  
        	  public void insertUpdate(DocumentEvent e) {
        		  if (topRectangleMeasure.getText() == null | topRectangleMeasure.getText().equals(" ")){
        			  //topRectangleMeasure.setText("0");
        		  }else{
	        		  topRectangleValue = Double.parseDouble(topRectangleMeasure.getText()); 
	        		  topFraction.setWhole((int)topRectangleValue);
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
	        		  bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	});
        
        
        
        topNumeratorField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				if (topNumeratorField.getText() == null | topNumeratorField.getText().equals(" ")){
					topNumerator = 0;
				}else{ 
					topNumerator = Integer.parseInt(topNumeratorField.getText());
				}
				topFraction.setNumerator(topNumerator);
			}

			public void insertUpdate(DocumentEvent arg0) {
				if (topNumeratorField.getText() == null | topNumeratorField.getText().equals(" ")){
					topNumerator = 0;
				}else{ 
					topNumerator = Integer.parseInt(topNumeratorField.getText());
				}
				topFraction.setNumerator(topNumerator);
			}

			public void removeUpdate(DocumentEvent arg0) throws NumberFormatException {
				if (topNumeratorField.getText() == null | topNumeratorField.getText().equals(" ")){
					topNumerator = 0;
				}else{ 
					topNumerator = Integer.parseInt(topNumeratorField.getText());
				}
				topFraction.setNumerator(topNumerator);
			}
        });

        bottomDenominatorComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
            	JComboBox cb = (JComboBox)e.getSource();
                String denominator = (String)cb.getSelectedItem();
                updateBottomDenominator(denominator);
            }
            
            public void updateBottomDenominator(String denominator){
            	bottomDenominator = denominator.equalsIgnoreCase(" ") ? 0: Integer.valueOf(denominator) ;
            	bottomFraction.setDenominator(bottomDenominator);
            }
        });   
        
        bottomNumeratorField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				if (bottomNumeratorField.getText() == null | bottomNumeratorField.getText().equals(" ")){
					bottomNumerator = 0;
				}else{ 
					bottomNumerator = Integer.parseInt(bottomNumeratorField.getText());
				}
				bottomFraction.setNumerator(bottomNumerator);
			}

			public void insertUpdate(DocumentEvent arg0) {
				if (bottomNumeratorField.getText() == null | bottomNumeratorField.getText().equals(" ")){
					bottomNumerator = 0;
				}else{ 
					bottomNumerator = Integer.parseInt(bottomNumeratorField.getText());
				}
				bottomFraction.setNumerator(bottomNumerator);
			}

			public void removeUpdate(DocumentEvent arg0) throws NumberFormatException {
				if (bottomNumeratorField.getText() == null | bottomNumeratorField.getText().equals(" ")){
					bottomNumerator = 0;
				}else{ 
					bottomNumerator = Integer.parseInt(bottomNumeratorField.getText());
				}
				bottomFraction.setNumerator(bottomNumerator);
			}
        });
        
        bottomUndoManager = new UndoManager();
        bottomRectangleMeasure.getDocument().addUndoableEditListener(bottomUndoManager);
        bottomRectangleMeasure.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  if (bottomRectangleMeasure.getText() == null | bottomRectangleMeasure.getText().equals(" ")){
        			  //bottomRectangleMeasure.setText("0");
        		  }else{
	        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
	        		  bottomFraction.setWhole((int) bottomRectangleValue);
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
	        		  bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	  
        	  public void removeUpdate(DocumentEvent e) {
        		  if (bottomRectangleMeasure.getText() == null | bottomRectangleMeasure.getText().equals(" ")){
        			  //bottomRectangleMeasure.setText("0");
        		  }else{
	        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
	        		  bottomFraction.setWhole((int) bottomRectangleValue);
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
	        		  bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	  
        	  public void insertUpdate(DocumentEvent e) {
        		  if (bottomRectangleMeasure.getText() == null | bottomRectangleMeasure.getText().equals(" ")){
        			  //bottomRectangleMeasure.setText("0");
        		  }else{
	        		  bottomRectangleValue = Double.parseDouble(bottomRectangleMeasure.getText());
	        		  bottomFraction.setWhole((int) bottomRectangleValue);
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
	        		  bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
	        		  topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
	        		  canvas.repaint();
        		  }
        	  }
        	});
        
       
        
        topUndoButton = new JButton(TOP_UNDO_BUTTON_TEXT);
        topUndoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	if(topUndoManager.canUndo())
            		topUndoManager.undo();
            }
        }); 
        
        bottomUndoButton = new JButton(BOTTOM_UNDO_BUTTON_TEXT);
        bottomUndoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	if(bottomUndoManager.canUndo())
            		bottomUndoManager.undo();
            }
        });
        
        topRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        bottomRectangleMeasure.setPreferredSize(new Dimension(10, 50));
        
        GridBagConstraints c = new GridBagConstraints();
        // Elements of Top Panel
        
        topPanel.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(topRectangleMeasure, c);
        c.fill = GridBagConstraints.NORTH;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.ipadx = 20;
        c.gridx = 1;
        c.gridy = 0;
        topPanel.add(topNumeratorField,c);
        c.fill = GridBagConstraints.NORTH;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.ipadx = 20;
        c.gridx = 1;
        c.gridy = 1;
        topPanel.add(topDenominatorComboBox,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 0;
        topPanel.add(topUndoButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;     
        c.weightx = 0.0;
        c.gridwidth = 5;
        c.gridx = 0;
        c.gridy = 2;
        topPanel.add(topSlider, c);
        bottomPanel.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;     
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        bottomPanel.add(bottomSlider, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        bottomPanel.add(bottomRectangleMeasure, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        bottomPanel.add(bottomNumeratorField, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        bottomPanel.add(bottomDenominatorComboBox, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 2;
        bottomPanel.add(bottomUndoButton, c);
        
        // Add the canvas into the container
        container.add(canvas);
        
        
        // Add the panels into the container
        container.add(BorderLayout.SOUTH, bottomPanel);
        container.add(BorderLayout.NORTH, topPanel);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

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
    
    class AsynchronousTopSliderListener implements ChangeListener{
    	public void stateChanged(ChangeEvent e){
    		canvas.setSizeCanvas(canvas.getSize());
    		JSlider tempSlider = (JSlider) e.getSource();
    		int tempValue = tempSlider.getValue();
    		tempWidth = (tempValue / (double)MAX_SLIDER);
    		topSlider.setValue(tempValue);
    		int width = (int) ((canvas.getWidthCanvas() - 
						(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
    		canvas.setTopRectangleWidth(width);
    		xPosition = (int)(LABEL_MOVEMENT_VALUE * tempValue) + TOP_LABEL_MIN_VALUE;
    		topSlider.setToolTipText(String.valueOf(tempValue));
    		tempPoint = new Point(xPosition, TOP_LABEL_Y_POSITION);
    		topLabel.setText(String.valueOf(((JSlider) e.getSource()).getValue()));
    		topLabel.setLocation(tempPoint);
    		canvas.repaint();
    	}
    	
    	public boolean isValueAdjusting(ChangeEvent e){
    		JSlider tempSlider = (JSlider)e.getSource();
    		return tempSlider.getValueIsAdjusting();
    	}
    }
    
    class AsynchronousBottomSliderListener implements ChangeListener{
    	public void stateChanged(ChangeEvent e){
    		canvas.setSizeCanvas(canvas.getSize());
    		JSlider tempSlider = (JSlider) e.getSource();
    		int tempValue = tempSlider.getValue();
    		tempWidth = (tempValue / (double)MAX_SLIDER);
    		bottomSlider.setValue(tempSlider.getValue());
    		int width = (int) ((canvas.getWidthCanvas() - 
					(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
    		canvas.setBottomRectangleWidth(width);
    		bottomSlider.setToolTipText(String.valueOf(tempValue));
    		canvas.repaint();
    	}
    }
    
    class SynchronousSliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
        	canvas.setSizeCanvas(canvas.getSize());
            JSlider tempSlider = (JSlider) e.getSource();
            tempWidth = (tempSlider.getValue() / (double)MAX_SLIDER);
            topSlider.setValue(tempSlider.getValue());
            bottomSlider.setValue(tempSlider.getValue());
            double ratioOfRectangles = canvas.getTopRectangleValue() / 
					   canvas.getBottomRectangleValue();
            bottomTextField.setText(DECIMAL_FORMAT.format((tempWidth * MAX_SLIDER)));
            topTextField.setText(DECIMAL_FORMAT.format((ratioOfRectangles * tempWidth * MAX_SLIDER)));
            int width = (int) ((canvas.getWidthCanvas() - 
	 							(2 * RectanglesCanvas.posXTopRectangle)) * tempWidth);
            canvas.setBottomRectangleYPosition(RectanglesCanvas.posYTopRectangle +
						canvas.getTopRectangleHeight() + 
						canvas.getSpacingBetweenRectangles()+10);
	 		canvas.setTopRectangleWidth(width);
	 		canvas.setBottomRectangleWidth(width);
	 		topSlider.setToolTipText(String.valueOf(tempSlider.getValue()));
	 		bottomSlider.setToolTipText(String.valueOf(tempSlider.getValue()));
            canvas.repaint();
        }
    }
    
    public static void main(String[] args){
    	MeasureRectangle theApplet = new MeasureRectangle();
        theApplet.init();   // invoke the applet's init() method
        theApplet.start();  // starts the applet

        // Create a window (JFrame) and make applet the content pane.
        javax.swing.JFrame window = new javax.swing.JFrame("Version 0.01");
        window.setContentPane(theApplet);
        window.setPreferredSize(new Dimension(800,600));
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.pack();              // Arrange the components.
        window.setVisible(true);    // Make the window visible.
    }
    
}
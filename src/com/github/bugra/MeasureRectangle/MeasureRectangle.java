package com.github.bugra.MeasureRectangle;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MeasureRectangle extends JApplet {
    RectanglesCanvas canvas;
    JTextField textField;
    JTextField bottomATextField;
    JTextField bottomBTextField;
    JTextField topTextField;
    JTextField topATextField;
    JTextField topBTextField;
    JTextField cTextField;
    float alphaValue = 0.65f;

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

        JSlider bottomSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 65);
        bottomSlider.addChangeListener(new SliderListener());
        
        textField = new JTextField("0.65", 4);
        bottomATextField = new JTextField("aTextField");
        bottomBTextField = new JTextField("bTextField");
        // 5. Add the label, slider and text field to the panel

        belowPanel.add(label);
        belowPanel.add(bottomSlider);
        belowPanel.add(textField);
        belowPanel.add(bottomATextField);
        belowPanel.add(bottomBTextField);

        // 6. Add the north panel to the frame

        container.add(BorderLayout.SOUTH, belowPanel);
        
        JPanel topPanel = new JPanel();
        TitledBorder topBorder = new TitledBorder("Change the ratio of above rectangle");
        topPanel.setBorder(topBorder);

        // 4. Create a label, slider and text field

        JLabel topLabel = new JLabel("Above Rectangle", JLabel.RIGHT);

        JSlider topSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 65);
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

    }
    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider tempSlider = (JSlider) e.getSource();
            alphaValue = (float)(tempSlider.getValue()/100.0);
            textField.setText(Float.toString(alphaValue));
            canvas.repaint();
        }
    }
    public float getAlphaValue(){
    	return alphaValue;
    }
}
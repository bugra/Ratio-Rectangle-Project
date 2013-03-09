package com.github.bugra.MeasureRectangle;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
class RectanglesCanvas extends Canvas {
        Rectangle2D topRectangle, bottomRectangle;
        
        public static final int posXTopRectangle = 10;
        public static final int posYTopRectangle = 10;
        private int widthTopRectangle = 50;
        private int heightTopRectangle = 50;
        
        public static final int posXBottomRectangle = 10;
        public static final int spacingBetweenBottomToRectangle = 10;
        private final int posYBottomRectangle = 160;
        private int widthBottomRectangle = 50;
        private int heightBottomRectangle = 50;
        
        private final int precisionSpacingBetweenRectangles = 30;
        private int spacingBetweenRectangles = 10;
        
        private int widthCanvas;
        private int heightCanvas;
        
        private double topRectangleValue = MeasureRectangle.INITIAL_BOTTOM_RECTANGLE_VALUE;
        private double bottomRectangleValue = MeasureRectangle.INITIAL_TOP_RECTANGLE_VALUE;
        
        // Constructor
        RectanglesCanvas() {

            // Create instances of 2D rectangles

            topRectangle = new Rectangle2D.Float(posXTopRectangle, posYTopRectangle,
            									widthTopRectangle, heightTopRectangle);
            bottomRectangle = new Rectangle2D.Float(posXBottomRectangle, posYBottomRectangle,
            										widthBottomRectangle, heightBottomRectangle);
            setBackground(Color.white);
        }

        public void paint(Graphics g) {

            // 12. Set up the 2D graphics context

            Graphics2D g2D = (Graphics2D) g;

            // 14. Create a stroke object with the prescibed with
            // and assign it to the graphics context. 

            g2D.setStroke(new BasicStroke(5.0f));

            // 15. Draw rectangle1.

            g2D.draw(topRectangle);
            g2D.draw(bottomRectangle);
        }
        
        public void setTopRectangleWidth(int width){
        	topRectangle.setRect(posXTopRectangle, posYTopRectangle,
        						width, heightTopRectangle);
        }
        
        public int getTopRectangleWidth(){
        	return (int) topRectangle.getWidth();
        }
        
        public void setTopRectangleHeight(int height){
        	topRectangle.setRect(posXTopRectangle, posYTopRectangle,
        						widthTopRectangle, height);
        }
        
        public void setBottomRectangleWidth(int width){
        	bottomRectangle.setRect(posXBottomRectangle, posYBottomRectangle,
        							width, heightBottomRectangle);
        }
        
        public int getBottomRectangleWidth(){
        	return (int) bottomRectangle.getWidth();
        }
        
        public void setBottomRectangleHeight(int height){
        	bottomRectangle.setRect(posXBottomRectangle, posYBottomRectangle,
        							widthBottomRectangle, height);
        }
        
        public void setBottomRectangleXPosition(int xPosition){
        	bottomRectangle.setRect(xPosition, posYBottomRectangle, 
        							widthBottomRectangle, heightBottomRectangle);
        }
        
        public void setBottomRectangleYPosition(int yPosition){
        	bottomRectangle.setRect(posXBottomRectangle, yPosition, 
					widthBottomRectangle, heightBottomRectangle);
        }
        
        public void setSizeCanvas(Dimension d){
        	this.setSize(d);
        	this.setHeightCanvas(d.height);
        	this.setWidthCanvas(d.width);
        	// To prevent negative values
        	spacingBetweenRectangles = Math.abs((int) (d.height / precisionSpacingBetweenRectangles));        	
        	//paint();
        }
        
        public void setWidthCanvas(int width){
        	widthCanvas = width;
        	this.setSize(width, heightCanvas);
        }
        
        public void setHeightCanvas(int height){
        	heightCanvas = height;
        	this.setSize(widthCanvas, height);
        }
        
        public int getWidthCanvas(){
        	return widthCanvas;
        }
        
        public int getHeightCanvas(){
        	return heightCanvas;
        }
        
        public void setSpacingBetweenRectangles(int spacing){
        	spacingBetweenRectangles = spacing;
        }
        
        public int getSpacingBetweenRectangles(){
        	return spacingBetweenRectangles;
        }
        
     // Return the sum of input text values
        public double getSumOfRectangleValues(){
        	return getTopRectangleValue() + getBottomRectangleValue();
        }
        
        // Calculate space available for both rectangles
        public int getAvailableSpaceForRectangles(){
        	return  getHeightCanvas() - 
        			posYTopRectangle - 
        			spacingBetweenBottomToRectangle - 
        			getSpacingBetweenRectangles();
        }
        
        public int getTopRectangleHeight(){
        	return (int) Math.floor((getAvailableSpaceForRectangles() / 
        								getSumOfRectangleValues()) 
        								* getTopRectangleValue());
        }
        
        public int getBottomRectangleHeight(){
        	return getAvailableSpaceForRectangles() - getTopRectangleHeight();
        }
        
        public double getTopRectangleValue(){
        	return topRectangleValue;
        }
        
        public void setTopRectangleValue(double value){
        	topRectangleValue = value;
        }
        
        public double getBottomRectangleValue(){
			return bottomRectangleValue;
        }
        
        public void setBottomRectangleValue(double value){
        	bottomRectangleValue = value;
        }
        
        public void updateHeights(){
        	setTopRectangleHeight((getTopRectangleHeight()));
        	setBottomRectangleHeight(getBottomRectangleHeight());
        }
        
        public void updateWidths(){
        	setTopRectangleWidth(getTopRectangleWidth());
        	setBottomRectangleWidth(getBottomRectangleWidth());
        }
        
    }
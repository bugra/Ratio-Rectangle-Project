package com.github.bugra.MeasureRectangle;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
class RectanglesCanvas extends Canvas {
        Rectangle2D topRectangle, bottomRectangle;
        
        public static final int posXTopRectangle = 10;
        private final int posYTopRectangle = 10;
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

        // Constructor
        RectanglesCanvas() {

            // Create instances of 2D rectangles

            topRectangle = new Rectangle2D.Float(posXTopRectangle, posYTopRectangle,
            									widthTopRectangle, heightTopRectangle);
            bottomRectangle = new Rectangle2D.Float(posXBottomRectangle, 410, 50, 50);
            System.out.println(getHeightCanvas());
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

            // 16. Create a gradient paint object and assign it
            // to the graphics context. Next, call the fill() method
            // to draw the filled rectangle2.

            GradientPaint gp = new GradientPaint(125f, 25f,
                                                 Color.yellow,
                                                 225f, 100f,
                                                 Color.blue);
            g2D.setPaint(gp);
            g2D.fill(bottomRectangle);

            // 17. Create a buffered image object, and create
            // the texture paint. Assign the texture paint to
            // the graphics context. Next, call the fill() method
            // to draw the filled rectangle.

            BufferedImage bi = new BufferedImage(5,5,
                                   BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.magenta);
            big.fillRect(0, 0, 5, 5);
            big.setColor(Color.black);
            big.drawLine(0, 0, 5, 5);
            Rectangle r = new Rectangle(0, 0, 5, 5);
            
            
        }
        
        public void setTopRectangleWidth(int width){
        	topRectangle.setRect(posXTopRectangle, posYTopRectangle,
        						width, heightTopRectangle);
        }
        
        public void setTopRectangleHeight(int height){
        	topRectangle.setRect(posXTopRectangle, posYTopRectangle,
        						widthTopRectangle, height);
        }
        
        public void setBottomRectangleWidth(int width){
        	bottomRectangle.setRect(posXBottomRectangle, posYBottomRectangle,
        							width, heightBottomRectangle);
        }
        
        public void setBottomRectangleHeight(int height){
        	bottomRectangle.setRect(posXBottomRectangle, posYBottomRectangle,
        							widthBottomRectangle, height);
        }
        
        public void setBottomRectangleXPosition(int xPosition){
        	bottomRectangle.setRect(xPosition, posYBottomRectangle, 
        							widthBottomRectangle, widthBottomRectangle);
        }
        
        public void setBottomRectangleYPosition(int yPosition){
        	bottomRectangle.setRect(posXBottomRectangle, yPosition, 
					widthBottomRectangle, widthBottomRectangle);
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
        
        
    }
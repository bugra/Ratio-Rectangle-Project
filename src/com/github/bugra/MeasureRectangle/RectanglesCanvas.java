package com.github.bugra.MeasureRectangle;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

class RectanglesCanvas extends Canvas {
        Rectangle2D rec1, rec2, rec3, rec4, rec5;

        // 11. Constructor

        RectanglesCanvas() {

            // Create instances of 2D rectangles

            rec1 = new Rectangle2D.Float(25, 25, 75, 150);
            rec2 = new Rectangle2D.Float(125, 25, 100, 75);
            rec3 = new Rectangle2D.Float(75, 125, 125, 75);
            rec4 = new Rectangle2D.Float(225, 125, 125, 75);
            rec5 = new Rectangle2D.Float(150, 50, 125, 175);

            setBackground(Color.white);
        }

        public void paint(Graphics g) {

            // 12. Set up the 2D graphics context

            Graphics2D g2D = (Graphics2D) g;

            // 13. Create an instance of alpha composite and assign
            // to the graphics context.

            AlphaComposite ac = AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER, 0.5f);
            g2D.setComposite(ac);
                                                      
            // 14. Create a stroke object with the prescibed with
            // and assign it to the graphics context. 

            g2D.setStroke(new BasicStroke(5.0f));

            // 15. Draw rectangle1.

            g2D.draw(rec1);

            // 16. Create a gradient paint object and assign it
            // to the graphics context. Next, call the fill() method
            // to draw the filled rectangle2.

            GradientPaint gp = new GradientPaint(125f, 25f,
                                                 Color.yellow,
                                                 225f, 100f,
                                                 Color.blue);
            g2D.setPaint(gp);
            g2D.fill(rec2);

            // 17. Create a buffered image object, and create
            // the texture paint. Assign the texture paint to
            // the graphics context. Next, call the fill() method
            // to draw the filled rectangle.

            BufferedImage bi = new BufferedImage(5,5,
                                   BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.magenta);
            big.fillRect(0,0,5,5);
            big.setColor(Color.black);
            big.drawLine(0,0,5,5);
            Rectangle r = new Rectangle(0,0,5,5);

            // Create the texture using the buffered image and rectangle.

            TexturePaint tp = new TexturePaint(bi, r);

            g2D.setPaint(tp);
            g2D.fill(rec3);

            // 18. Finally, assign different colors to the graphics
            // context and draw the filled rectangles rectangle4 and
            // rectangle5.

            g2D.setColor(Color.green);
            g2D.fill(rec4);
            g2D.setColor(Color.red);
            g2D.fill(rec5);
        }
    }
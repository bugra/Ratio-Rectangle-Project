package com.github.bugra.MeasureRectangle;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;

@SuppressWarnings("serial")
class Grids extends Canvas {
	  int width, height, rows, columns;
	  boolean wInvisible, hInvisible;

	  Grids(int w, int h, int r, int c) {
	    setSize(width = w, height = h);
	    rows = r;
	    columns = c;
	    this.wInvisible = false;
	    this.hInvisible = false;
	  }
	  
	    public void paint(Graphics g) {
	    int k;
	    
	    width = getSize().width;
	    height = getSize().height;

	    int htOfRow = height / (rows);
	    
	    if(!hInvisible){
	    	for (k = 0; k < rows; k++)
	    		g.drawLine(0, k * htOfRow , width, k * htOfRow );
	    }
	    if(!wInvisible){
	    int wdOfRow = width / (columns);
	    for (k = 0; k < columns; k++)
	      g.drawLine(k*wdOfRow , 0, k*wdOfRow , height);
	    }
	  }
		public void setHInvisible(boolean h){this.hInvisible = h;}
		public void setWInvisible(boolean w){this.wInvisible = w;}
	}
	

	@SuppressWarnings("serial")
	class DrawGrids extends Frame {
	  DrawGrids(String title, int w, int h, int rows, int columns) {
	    setTitle(title);
	    Grids grid = new Grids(w, h, rows, columns);
	    add(grid);
	}
	public static void main(String[] args) {
	    new DrawGrids("Draw Grids", 200, 200, 2, 10).setVisible(true);
	  }
	}

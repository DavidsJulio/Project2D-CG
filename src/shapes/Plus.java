package shapes;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Plus implements Shape{

	GeneralPath path;
	
	public Plus(float x, float y, float w, float h) {
		
		path = new GeneralPath();
		
		float x0 = x + w;
		float y0 = y + (0.33f * h);
		
		float x1 = x + w;
		float y1 = y + (0.66f * h);
		
		float x2 = x + (0.66f * w); 
		float y2 = y + (0.66f * h);
		
		
		float x3 = x + (0.66f * w);
		float y3 = y + h;
		
		
		float x4 = x + (0.33f * w);
		float y4 = y + h; 
		
		float x5 = x + (0.33f * w);
		float y5 = y + (0.66f * h);
		
		float x6 = x;
		float y6 = y + (0.66f * h);
		
		float x7 = x;
		float y7 = y + (0.33f * h);
		
		float x8 = x + (0.33f * w);
		float y8 = y + (0.33f * h);
		
		float x9 = x + (0.33f * w);
		float y9 = y;
		
		float x10 = x + (0.66f * w);
		float y10 = y;
		
		float x11 = x + (0.66f * w);
		float y11 = y + (0.33f * h);
		
		
		path.moveTo(x0, y0);
	    path.lineTo(x1, y1);
	    path.lineTo(x2, y2);
	    path.lineTo(x3, y3);
	    path.lineTo(x4, y4);
	    path.lineTo(x5, y5);
	    path.lineTo(x6, y6);
	    path.lineTo(x7, y7);
	    path.lineTo(x8, y8);
	    path.lineTo(x9, y9);
	    path.lineTo(x10, y10);
	    path.lineTo(x11, y11);
	    //path.lineTo(x0, y0);
	    path.closePath();
		
	}
	
	public boolean contains(Rectangle2D rect) {
	    return path.contains(rect);
	  }

	  public boolean contains(Point2D point) {
	    return path.contains(point);
	  }

	  public boolean contains(double x, double y) {
	    return path.contains(x, y);
	  }

	  public boolean contains(double x, double y, double w, double h) {
	    return path.contains(x, y, w, h);
	  }

	  public Rectangle getBounds() {
	    return path.getBounds();
	  }

	  public Rectangle2D getBounds2D() {
	    return path.getBounds2D();
	  }

	  public PathIterator getPathIterator(AffineTransform at) {
	    return path.getPathIterator(at);
	  }

	  public PathIterator getPathIterator(AffineTransform at, double flatness) {
	    return path.getPathIterator(at, flatness);
	  }

	  public boolean intersects(Rectangle2D rect) {
	    return path.intersects(rect);
	  }

	  public boolean intersects(double x, double y, double w, double h) {
	    return path.intersects(x, y, w, h);
	  }

	
}

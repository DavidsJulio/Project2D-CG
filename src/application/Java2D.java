package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import shapes.Star;

public class Java2D extends JFrame{

	public static void main(String[] args) {
		
		JFrame frame = new Java2D();
		frame.setTitle("Java 2D - David Júlio");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyPanel panel = new MyPanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

}

class MyPanel extends JPanel{

	int panelWidth = 600;
	int panelHeight = 400;
	
	int scale = 15;
	
	AffineTransform at = new AffineTransform();
	
	//Player - Using ellipse
	Shape player;
	int translationX = scale * 2;
	int translationY = panelHeight - scale * 2;
	
	//Star 
	Shape star1 = new Star( -scale, -scale, scale * 2, scale * 2 );
	
	//Obstacles
	Shape wall_1 = new Rectangle2D.Double(-scale * 2, -scale * 6, scale * 4, scale * 12);
	Shape wall_2 = new Rectangle2D.Double(-scale * 2, -scale * 4, scale * 4, scale * 8); //edit

	
	public MyPanel() {
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(Color.LIGHT_GRAY); 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//player
		player = new Ellipse2D.Double( -scale, -scale, 2 * scale, 2 * scale );
		at.setToTranslation( translationX, translationY );
		player = at.createTransformedShape( player );
		g2.setColor( Color.RED );
		g2.fill( player );
		
		//Star
		at.setToTranslation( panelWidth / 2, panelHeight / 2 );
		star1 = at.createTransformedShape( star1 );
		g2.setColor( Color.YELLOW );
		g2.fill( star1 );
		
		
		//Obstacles
		//Wall - 1
		at.setToTranslation( scale * 6, panelHeight - scale * 6 );
		wall_1 = at.createTransformedShape( wall_1 );
		g2.setColor( Color.BLUE );
		g2.fill( wall_1 );
		
		//Wall - 2
		at.setToTranslation( scale * 2, panelHeight - (panelHeight - scale * 2) );
		wall_2 = at.createTransformedShape( wall_2 );
		g2.setColor( Color.BLUE );
		g2.fill( wall_2 );
		
	}
	
}

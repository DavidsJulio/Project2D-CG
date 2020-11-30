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
	
	AffineTransform at = new AffineTransform();
	
	//Player - Using ellipse
	int radius = 15;
	Shape player;
	int translationX = radius * 2;
	int translationY = panelHeight - radius * 2;
	
	//Star 
	Shape star1 = new Star(-radius, -radius, radius*2, radius*2);
	
	//Obstacles
	Shape obs1 = new Rectangle2D.Double(-30, -100, 60, 200);
	
	public MyPanel() {
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(Color.LIGHT_GRAY); 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//player
		player = new Ellipse2D.Double(-radius, -radius, 2*radius, 2*radius);
		at.setToTranslation(translationX, translationY);
		player = at.createTransformedShape(player);
		g2.setColor(Color.RED);
		g2.fill(player);
		
		//Star
		at.setToTranslation(300, 200);
		star1 = at.createTransformedShape(star1);
		g2.setColor(Color.YELLOW);
		g2.fill(star1);
		
		
		//Obstacles
		at.setToTranslation(100, panelHeight - 100);
		obs1 = at.createTransformedShape(obs1);
		g2.setColor(Color.BLUE);
		g2.fill(obs1);
	}
	
}

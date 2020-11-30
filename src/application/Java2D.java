package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	int translationX = 0 + 2*radius;
	int translationY = panelHeight - 2*radius;
	
	
	public MyPanel() {
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(Color.LIGHT_GRAY); 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		
		player = new Ellipse2D.Double(-radius, -radius, 2*radius, 2*radius);
		at.setToTranslation(translationX, translationY);
		player = at.createTransformedShape(player);
		g2.setColor(Color.RED);
		g2.fill(player);
	
		
	}
	
}

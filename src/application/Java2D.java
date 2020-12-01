package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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

class MyPanel extends JPanel implements Runnable, KeyListener, MouseListener{

	int panelWidth = 600;
	int panelHeight = 400;
	
	int scale = 15;
	int originX = scale * 2;
	int originY = panelHeight - scale * 2;
	
	AffineTransform at = new AffineTransform();
	
	//Player - Using ellipse
	Shape player = null;
	
	int translationX = scale * 2;
	int translationY = panelHeight - scale * 2;
	int vxPlayer = 0;
	int vyPlayer = 0;
	
	//Obstacles
	Shape wall_1 = null;
	Shape wall_2 = null;
	
	//Star 
	Shape star1 = null;
	
	//Mouse aux
	int firstX = 0;
	int firstY = 0;
	boolean selected = false;
	
	BufferedImage img = utils.Utils.readImage(this, "images/Brick_Wall.jpg");
	
	public MyPanel() {
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(Color.DARK_GRAY); 
		
		//Key
		this.addKeyListener(this);
		this.setFocusable(true);
		
		//Mouse
		this.addMouseListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Star
		star1 = new Star( -scale, -scale, scale * 2, scale * 2 );
		
		at.setToTranslation( panelWidth / 2, panelHeight / 2 );
		star1 = at.createTransformedShape( star1 );
		g2.setColor( Color.YELLOW );
		g2.fill( star1 );
		
		
		//Obstacles
		//Wall - 1
		wall_1 = new Rectangle2D.Double(-scale * 2, -scale * 6, scale * 4, scale * 12);
		
		at.setToTranslation( scale * 6, panelHeight - scale * 6 );
		wall_1 = at.createTransformedShape( wall_1 );
		g2.setPaint(new TexturePaint(img, wall_1.getBounds2D()));
		g2.fill( wall_1 );
		
		//Wall - 2
		wall_2 = new Rectangle2D.Double(-scale * 2, -scale * 4, scale * 4, scale * 8); //edit
		
		at.setToTranslation( scale * 2, scale * 4);
		wall_2 = at.createTransformedShape( wall_2 );
		//g2.setColor( Color.BLUE );
		g2.setPaint(new TexturePaint(img, wall_2.getBounds2D()));
		g2.fill( wall_2 );
				
		//Player
		player = new Ellipse2D.Double( -scale, -scale, 2 * scale, 2 * scale );
		
		at.setToTranslation( translationX, translationY );
		player = at.createTransformedShape( player );
		g2.setColor( Color.RED );
		g2.fill( player );
	}

							//Runnable
	@Override
	public void run() {
		
		while(true) {
			repaint();
			translationX += vxPlayer;
			translationY += vyPlayer;
			
			collisionWalls();

			
			try {
				Thread.sleep(100);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
							//Teclado - KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		switch (keyCode) {
		case KeyEvent.VK_UP:
			vxPlayer = 0;
			vyPlayer = -5;
			break;

		case KeyEvent.VK_RIGHT:
			vxPlayer = 5;
			vyPlayer = 0;
			break;
			
		case KeyEvent.VK_DOWN:
			vxPlayer = 0;
			vyPlayer = 5;
			break;
			
		case KeyEvent.VK_LEFT:
			vxPlayer = -5;
			vyPlayer = 0;
			break;		
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		vxPlayer = 0;
		vyPlayer = 0;
	}

	
	//Rato - MouseListener - See if player is selected or not
	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Verifica se o jogador está selecionado ou não
		if(player.contains(e.getX(), e.getY())) {
			firstX = e.getX();
			firstY = e.getY();
			selected = true;
			//System.out.println("Selected: "+selected);
		}else {
			selected = false;
			//System.out.println("Selected: "+selected);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
												//AUX
	
	public void collisionWalls() { //colisao com as paredes
		if(translationX - scale < 0) { //Colisao parede esquerda
			
			translationX = originX;
			translationY = originY;
			
		}else if(translationX + scale > panelWidth) { //colisao parede direita
			
			translationX = originX;
			translationY = originY;
			
		}else if(translationY - scale < 0) { //colisao parede superior
			
			translationX = originX;
			translationY = originY;
		
		}else if(translationY + scale > panelHeight){ //colisao parede inferior
		
			translationX = originX;
			translationY = originY;
		
		}
	}
}

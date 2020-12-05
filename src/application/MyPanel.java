package application;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import shapes.Plus;
import shapes.Star;

public class MyPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	int panelWidth = 600;
	int panelHeight = 400;
	
	int scale = 15;
	int originX = scale * 2;
	int originY = panelHeight - scale * 2;
	Color backColor = (Color.DARK_GRAY);
	boolean win = false;
	boolean collision = false;
	public static boolean STOP = false;
	boolean bonus = false;
	

	Font fontWin = new Font("Monospaced", Font.BOLD, 50);
	String strWin = "Victory";
	
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
	Shape wall_3 = null;
	Shape wall_4 = null;
	Shape wall_5 = null;
	Shape wall_6 = null;
	Shape wall_7 = null;
	Shape plus = null;
	//Moving obstacles
	Shape move1 = null;
	int tx = panelWidth - scale*2;
	int ty = panelHeight - scale * 4;
	
	
	//Star 
	Shape star1 = null;
	//Stroke stroke = new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	//Goal
	Font font = new Font("Monospaced", Font.BOLD, 20);
	String goal = "Goal";
	Shape goalBox = null;
	
	//Mouse aux
	int firstX = 0;
	int firstY = 0;
	boolean selected = false;
	
	BufferedImage img = utils.Utils.readImage(this, "images/Brick_Wall.jpg");
	
	
	Thread thread;
	
	public MyPanel() {	
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(backColor); 
		
		//Key
		this.addKeyListener(this);
		this.setFocusable(true);
		
		//Mouse
		this.addMouseListener(this);
		
		thread = new Thread(this);
		thread.start();	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		drawAll(g2);
		if(winningCondition()){
			//setBackground(Color.WHITE);
		
			g2.setFont(fontWin);
			g2.setColor(Color.GREEN);
			g2.drawString(strWin, panelWidth / 3, panelHeight / 2);
			//cancel();
		}
		
		if(collision) {
			g2.setFont(fontWin);
			g2.setColor(Color.RED);
			g2.drawString("Game Over", panelWidth / 4, panelHeight / 2);
		}
		

	}
	
	public void drawAll(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Goal
		g2.setFont(font);
		g2.setColor(Color.YELLOW);
		g2.drawString(goal, panelWidth - 50, panelHeight - scale);

		g2.setPaint(new TexturePaint(img, new Rectangle2D.Double(0, 0, panelWidth, panelHeight)));
		
		//Obstacles
		//Wall - 1
		wall_1 = new Rectangle2D.Double(-scale * 2, -scale * 6, scale * 4, scale * 12);
		at.setToTranslation( scale * 6, panelHeight - scale * 6 );
		wall_1 = at.createTransformedShape( wall_1 );
		g2.fill( wall_1 );
		
		//Wall - 2
		wall_2 = new Rectangle2D.Double(-scale * 2, -scale * 4, scale * 4, scale * 8); //edit
		at.setToTranslation( scale * 2, scale * 4);
		wall_2 = at.createTransformedShape( wall_2 );	
		g2.fill( wall_2 );
						
		//Wall - 4
		wall_4 = new Rectangle2D.Double(- scale * 2, -scale * 4, scale * 4, scale * 8);
		at.setToTranslation(scale * 14, panelHeight - scale * 4);
		wall_4 = at.createTransformedShape(wall_4);
		g2.fill(wall_4);
		
		//Wall - 5 
		wall_5 = new Rectangle2D.Double(- scale * 2, -scale * 9, scale * 4, scale * 18);
		at.setToTranslation(panelWidth - scale * 14, panelHeight - scale * 12);
		wall_5 = at.createTransformedShape(wall_5);
		g2.fill(wall_5);
		
		
		//Wall - 6
		wall_6 = new Rectangle2D.Double(- scale * 4, -scale * 3, scale * 6, scale * 4);
		at.setToTranslation(panelWidth - scale * 9, panelHeight - scale * 12);
		wall_6 = at.createTransformedShape(wall_6);
		g2.fill(wall_6);
		
		//Wall - 7
		wall_7 = new Rectangle2D.Double(-scale * 4, -scale * 2, scale * 8, scale * 4);
		at.setToTranslation(panelWidth - scale * 4, scale * 2);
		wall_7 = at.createTransformedShape(wall_7);
		g2.fill(wall_7);
			
		
		//Plus
		plus = new Plus(-scale * 6, -scale * 6, scale * 12, scale * 12);
		at.setToTranslation(panelWidth / 3 + scale, panelHeight / 3);
		plus = at.createTransformedShape(plus);
		g2.fill(plus);
		
		
		g2.setColor(Color.GREEN);
		//Move - 1
		move1 = new Rectangle2D.Double(-20, -5, 40, 10);
		//at.setToTranslation(panelWidth - scale*2, panelHeight - scale * 4);
		at.setToTranslation(tx, ty);

		move1 = at.createTransformedShape(move1);
		g2.fill(move1);
	
		
		//Star
		star1 = new Star( -scale, -scale, scale * 2, scale * 2 );	
		at.setToTranslation( panelWidth - scale * 10, panelHeight - scale * 9 );
		star1 = at.createTransformedShape( star1 );
		g2.setColor( Color.YELLOW );
		g2.fill( star1 );
		
		//Player
		player = new Ellipse2D.Double( -scale, -scale, 2 * scale, 2 * scale );
		at.setToTranslation( translationX, translationY );
		player = at.createTransformedShape( player );
		
		g2.setColor( Color.RED );
		g2.fill( player );
		
		//Wall - 3
		wall_3 = new Rectangle2D.Double(-scale * 2, - scale * 9, scale * 4, scale * 18);	
		at.setToTranslation(panelWidth - scale * 6, panelHeight - scale * 9);
		wall_3 = at.createTransformedShape(wall_3);		
		
		if(bonus) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f); 
			g2.setComposite(ac);
		}
		
		g2.setPaint(new TexturePaint(img, new Rectangle2D.Double(0, 0, panelWidth, panelHeight)));
		g2.fill(wall_3);

	}

							//Runnable
	@Override
	public void run() {
		
		while(!STOP) {
			repaint();
			

			
			translationX += vxPlayer;
			translationY += vyPlayer;
			
			collisionBorders();
			if(star1 != null) {
				bonusStar();
			}
			
			
//			if(wall_1 != null) {
				collisionWalls();
//			}
			
			
			try {
				Thread.sleep(50);
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
			System.out.println("Selected: "+selected);
		}else {
			selected = false;
			System.out.println("Selected: "+selected);
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
	
												//MouseMotion
	@Override
	public void mouseDragged(MouseEvent e) {
//		if(selected) {
		
			vxPlayer = e.getX() - firstX;
			System.out.print(vxPlayer);
			vyPlayer = e.getY() - firstY;
			System.out.print(vyPlayer);
			
			at.setToTranslation(vxPlayer, vyPlayer);
			player = at.createTransformedShape(player);
			firstX += vxPlayer;
			firstY += vyPlayer;
			repaint();
//		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
												//AUX
	
	public void collisionBorders() { //colisao com as paredes
		if(translationX - scale < 0) { //Colisao parede esquerda			
			translationX = originX;
			translationY = originY;		
			collision = true;
			STOP = true;
		}else if(translationX + scale > panelWidth) { //colisao parede direita			
			translationX = originX;
			translationY = originY;			
			collision = true;
			STOP = true;
		}else if(translationY - scale < 0) { //colisao parede superior			
			translationX = originX;
			translationY = originY;
			collision = true;
			STOP = true;
		}else if(translationY + scale > panelHeight){ //colisao parede inferior
		
			translationX = originX;
			translationY = originY;
			collision = true;
			STOP = true;
	
		}
	}
	
	public void collisionWalls() {
	
		if(wall_1 != null) {
			if(player.intersects(wall_1.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
		if(wall_2 != null) {
			if(player.intersects(wall_2.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
//		if(wall_3 != null) {
//			if(player.intersects(wall_3.getBounds())) {
//				collision = true;
//				STOP = true;
//			}
//		}
		
		if(wall_4 != null) {
			if(player.intersects(wall_4.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
		if(wall_5 != null) {
			if(player.intersects(wall_5.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
		if(wall_6 != null) {
			if(player.intersects(wall_6.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
		if(wall_7 != null) {
			if(player.intersects(wall_7.getBounds())) {
				collision = true;
				STOP = true;
			}
		}
		
	
	}
	
	public void bonusStar() {
		if(star1.contains(translationX, translationY - scale ))
			bonus = true;
	}
	
	public boolean winningCondition() {
		if(player.intersects(panelWidth - scale *2, panelHeight - scale * 2, 50, 50)){
			win = true;	
			STOP = true;
			return win;
		}
		return false;
	}

	
	
	
}

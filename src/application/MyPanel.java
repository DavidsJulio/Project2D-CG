package application;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

import shapes.Plus;
import shapes.Star;

public class MyPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, Printable{

	int panelWidth = 600;
	int panelHeight = 400;
	
	int scale = 15;
	int originX = scale * 2;
	int originY = panelHeight - scale * 2;
	Color backColor = (Color.DARK_GRAY);
	BufferedImage img = utils.Utils.readImage(this, "images/Brick_Wall.jpg");

	boolean win = false;
	boolean collision = false;
	public static boolean STOP = false;
	boolean bonus = false;
	boolean transparency = false;
	//reset
	protected static boolean RESET = false;
//	boolean RESET = false;
	

	Font fontCondition = new Font("Monospaced", Font.BOLD, 50);
	String strWin = "Victory";
	String strGameOver = "Game Over";
	
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
	int move1tx = panelWidth + scale * 8; //600 + 120
	int move1OriginTx = panelWidth + scale * 8;
	int move1ty = panelHeight - scale * 4;
	
	Shape move2 = null;
	int move2tx = panelWidth - scale * 6; //300 - 90
	int move2ty = scale * 3;
	int move2OriginTy = scale * 3;	
	
	Shape move3 = null;
	int move3tx = panelWidth/2 - scale*2; //300 - 30
	int move3OriginTx = panelWidth/2 - scale*2;	
	int move3ty = panelHeight/3;
	
	Shape move4 = null;
	int move4tx = panelWidth/3 + scale; //200 + 15
	int move4ty = panelHeight/4;
	int move4OriginTy = panelWidth/4;	
	
	//Star 
	Shape star1 = null;
	
	//Goal
	Font font = new Font("Monospaced", Font.BOLD, 20);
	String goal = "Goal";
	Shape goalBox = null;
	
	//Mouse aux
	boolean selected = false;
	
	//Stroke
	Stroke stroke;
	public static final int NONE = 0;
	public static final int JOIN_MITER = 2;
	public static final int JOIN_ROUND = 1;

	public static final int WITH_DASH = 3;
	
	
	public static int strokeType;
	
	Thread thread;
	
	public MyPanel() {	
		setPreferredSize( new Dimension( panelWidth, panelHeight ) ); 
		setBackground(backColor); 
		
		//Key
		this.addKeyListener(this);
		this.setFocusable(true);
		
		//Mouse
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		thread = new Thread(this);
		thread.start();		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		drawAll(g2);

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f); 
		g2.setComposite(ac);
		
		
		if(RESET) {
			reset();
			RESET = false;
		}
		
		if(winningCondition()){
			g2.setFont(fontCondition);
			g2.setColor(Color.GREEN);
			g2.drawString(strWin, panelWidth / 3, panelHeight / 2);
		}
		
		if(collision) {
			g2.setFont(fontCondition);
			g2.setColor(Color.RED);
			g2.drawString(strGameOver, panelWidth / 4, panelHeight / 2);
		}
		
		
		
	}
	
	public void drawAll(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	
		//Goal
		g2.setFont(font);
		g2.setColor(Color.YELLOW);
		g2.drawString(goal, panelWidth - 50, panelHeight - scale);
		
		
		g2.setPaint(new GradientPaint(40, 10, new Color(0,0,255), 0, 0, new Color(0,255,255), true));
		
		//Move - 1
		move1 = new Rectangle2D.Double(-20, -5, 40, 10);
		//at.setToTranslation(panelWidth - scale*2, panelHeight - scale * 4);
		at.setToTranslation(move1tx, move1ty);
		move1 = at.createTransformedShape(move1);
		g2.fill(move1);
		
		move2 = new Rectangle2D.Double(-5,-20,10, 40);
		//at.setToTranslation(panelWidth - scale*2, panelHeight - scale * 4);
		at.setToTranslation(move2tx, move2ty);
		move2 = at.createTransformedShape(move2);
		g2.fill(move2);
		
		move3 = new Rectangle2D.Double(-20, -5, 40, 10);
		//at.setToTranslation(panelWidth - scale*2, panelHeight - scale * 4);
		at.setToTranslation(move3tx, move3ty);
		move3 = at.createTransformedShape(move3);
		g2.fill(move3);
		
		move4 = new Rectangle2D.Double(-5,-20,10, 40);
		//at.setToTranslation(panelWidth - scale*2, panelHeight - scale * 4);
		at.setToTranslation(move4tx, move4ty);
		move4 = at.createTransformedShape(move4);
		g2.fill(move4);

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
		
		if(strokeType != 0) {
			stroke(g2);
		}else {
			g2.fill(plus);
		}

	
		if(!bonus) {	
			//Star
			star1 = new Star( -scale, -scale, scale * 2, scale * 2 );	
			at.setToTranslation( panelWidth - scale * 10, panelHeight - scale * 9 );
			star1 = at.createTransformedShape( star1 );
			g2.setColor( Color.YELLOW );
			g2.fill( star1 );
		}
		
		
		//Player
		player = new Ellipse2D.Double( -scale, -scale, 2 * scale, 2 * scale );
		at.setToTranslation( translationX, translationY );
		player = at.createTransformedShape( player );
		GradientPaint gp = new GradientPaint(-scale, -scale, new Color(255,215,0), 2 * scale, 2 * scale, new Color(255,0,0), true);
		//cyclic true, para se repetir
		g2.setPaint(gp);
		g2.fill( player );

		
		//Wall - 3
		wall_3 = new Rectangle2D.Double(-scale * 2, - scale * 9, scale * 4, scale * 18);	
		at.setToTranslation(panelWidth - scale * 6, panelHeight - scale * 9);
		wall_3 = at.createTransformedShape(wall_3);		
		
		
		
		if(bonus) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f); 
			g2.setComposite(ac);
			transparency = true;
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

			
			movingWalls();
			collisionBorders();
			if(star1 != null) {
				bonusStar();
			}
			
			collisionWalls();
		
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

	//Rato - MouseListener - 
	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	//See if player is selected or not
	@Override
	public void mousePressed(MouseEvent e) {
		if( player.contains( e.getX(), e.getY() ) ) {
			translationX = e.getX();
			translationY = e.getY();
			selected = true;
		}else {
			selected = false;
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
		if(selected) {
			vxPlayer = e.getX() - translationX;
			vyPlayer = e.getY() - translationY;
		
			at.setToTranslation(vxPlayer, vyPlayer);
			player = at.createTransformedShape(player);
			
			translationX += vxPlayer;
			translationY += vyPlayer;
			
			//limpar o lixo, para que a bola não continue a andar
			vyPlayer = 0;
			vxPlayer = 0;	
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
												//AUX
	public void movingWalls() {
		
		move1tx = move1tx - 2;
		if( move1tx < panelWidth - scale * 6) {
			move1tx = move1OriginTx;
		}
		
		move2ty = move2ty + 3;
		if( move2ty > panelHeight - scale * 14) {
			move2ty = move2OriginTy;
		}
		
		move3tx = move3tx + 2;
		if(move3tx > panelWidth - scale * 14) {
			move3tx = move3OriginTx;
		}
		
		move4ty = move4ty - 2;
		if(move4ty < -scale * 2) {
			move4ty = move4OriginTy;
		}
				
	}
	
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
		if(player!= null) {
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
			
			if(wall_3 != null) {
				if(player.intersects(wall_3.getBounds()) && !transparency) {
					collision = true;
					STOP = true;
				}
			}
			
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
			
			if(plus != null) {
				if(plus.contains(player.getBounds().getCenterX()+scale, player.getBounds().getCenterY()+scale) ||
						plus.contains(player.getBounds().getCenterX()-scale , player.getBounds().getCenterY()-scale)) {
					collision = true;
					STOP = true;
				}
			}
			
			//moving walls
			if(move1 != null) {
				if(player.intersects(move1.getBounds())) {
					collision = true;
					STOP = true;
				}
			}
			
			if(move2 != null) {
				if(player.intersects(move2.getBounds())) {
					collision = true;
					STOP = true;
				}
			}
			
			if(move3 != null) {
				if(player.intersects(move3.getBounds())) {
					collision = true;
					STOP = true;
				}
			}
			
			if(move4 != null) {
				if(player.intersects(move4.getBounds())) {
					collision = true;
					STOP = true;
				}
			}
		}
		
	}
	
	public void bonusStar() {
		if(star1.contains(player.getBounds().getCenterX(), player.getBounds().getCenterY() - scale))
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

	public void stroke(Graphics2D g2) {

		switch (strokeType) {
			
		case JOIN_MITER:
			stroke = new BasicStroke(20, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
			g2.setStroke(stroke);
			g2.draw(plus);
			break;
			
		case JOIN_ROUND:
			stroke = new BasicStroke(20, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
			g2.setStroke(stroke);
			g2.draw(plus);
			break;

		case WITH_DASH:		
			float[] dashArray = { scale * 2, scale *2 };
			float dashPhase = 8;
			stroke = new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashArray, dashPhase);
			g2.setStroke(stroke);
			g2.draw(plus);
			break;
		default:
			break;
		}
		

		
	}
	
	public void reset() {
			translationX = originX;
			translationY = originY;	
			STOP = false;
			win = false;
			collision = false;
	}
		
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		switch (pageIndex) {
		case 0:
			drawAll(graphics);
			break;
		case 1:
			graphics.translate(-(int)pageFormat.getImageableWidth(), 0);
			drawAll(graphics);
			break;
			
		default:
			return NO_SUCH_PAGE;
		}
		return PAGE_EXISTS;		
	}

}

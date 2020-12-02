package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Java2D extends JFrame implements ActionListener{

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
	
	public static final String GAME = "Game";
	public static final String START = "Start";
	public static final String EXIT = "Exit";
	
	public Java2D() {
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu menu = new JMenu("Game");
		JMenuItem mI = new JMenuItem("Start");
		
		mI.addActionListener(this);
		menu.add(mI);
		
		menu.addSeparator();
		
		mI = new JMenuItem("Exit");
		mI.addActionListener(this); 
		menu.add(mI); 
		mb.add(menu); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(START.equals(cmd)) {
			
		}else if(EXIT.equals(cmd)) {
			System.exit(0);
		}		
	}
}


package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

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
	public static final String RESTART = "Restart Game";
	public static final String PRINT = "Print";
	public static final String EXIT = "Exit";
	
	public static final String STROKE = "Stroke";
	
	public static final String NONE = "Sem Stroke";
	
	public static final String JOIN_MITER = "Join Miter";
	public static final String JOIN_ROUND = "Join Round";
	public static final String WITH_DASH = "With Dash";
	
	PrinterJob pj;
	MyPanel panel2;
	
	public Java2D() {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu menu = new JMenu(GAME);
		
		JMenuItem mI = new JMenuItem(RESTART);
		mI.addActionListener(this);
		menu.add(mI);
		
		menu.addSeparator();
		
		mI = new JMenuItem(PRINT);
		mI.addActionListener(this);
		menu.add(mI);
	
		
		mI = new JMenuItem(EXIT);
		mI.addActionListener(this); 
		menu.add(mI); 
		mb.add(menu); 
		
		menu = new JMenu(STROKE);
		
		mI = new JMenuItem(NONE);
		mI.addActionListener(this); 
		menu.add(mI);
		
		
		mI = new JMenuItem(JOIN_MITER);
		mI.addActionListener(this); 
		menu.add(mI);
		
		mI = new JMenuItem(JOIN_ROUND);
		mI.addActionListener(this); 
		menu.add(mI);
		
		mI = new JMenuItem(WITH_DASH);
		mI.addActionListener(this); 
		menu.add(mI);	
		
		mb.add(menu);

		//Printer
		panel2 = new MyPanel();
		pj = PrinterJob.getPrinterJob();
		pj.setPrintable(panel2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch (cmd) {
		case RESTART:
			MyPanel.RESET = true;
			break;
		
		case PRINT:
			if(pj.printDialog()) {
				try {
					pj.print();
				}catch(PrinterException ex) {
					ex.printStackTrace();
				}
			}
			break;
		case EXIT:
			System.exit(0);
			break;
			
		case NONE:
			MyPanel.strokeType = MyPanel.NONE;
			break;
			
		case JOIN_MITER:
			MyPanel.strokeType = MyPanel.JOIN_MITER;
			break;

		case JOIN_ROUND:
			MyPanel.strokeType = MyPanel.JOIN_ROUND;
			break;

		case WITH_DASH:
			MyPanel.strokeType = MyPanel.WITH_DASH;
			break;
		}	
	}
}


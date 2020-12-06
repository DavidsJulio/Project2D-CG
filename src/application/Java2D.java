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
	public static final String START = "Start";
	public static final String PRINT = "Print";
	public static final String EXIT = "Exit";
	
	PrinterJob pj;
	MyPanel printer;
	
	public Java2D() {
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu menu = new JMenu(GAME);
		
		JMenuItem mI = new JMenuItem(START);
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
		
		
		printer = new MyPanel();
		
		pj = PrinterJob.getPrinterJob();
		pj.setPrintable(printer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch (cmd) {
		case START:
			
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

		}
//		if(START.equals(cmd)) {
//			//MyPanel.STOP = false;
//		}else if()
//		
//		}else if(EXIT.equals(cmd)) {
//			
//		}		
	}
}


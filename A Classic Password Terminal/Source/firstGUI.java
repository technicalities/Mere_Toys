import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorListener;
import javax.swing.text.DefaultCaret;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class firstGUI extends JFrame implements ActionListener
{
	//  JComponent instance variables
		private JTextArea display, entry, boardArea;
		private JPanel up, down, left, right, mid;
		private JLabel upp, downn, leftt, rightt, lotr;
		private JButton min, max, quit, vol, go;
			
	//  Application instance variables
		private String user;
		private String friend;
		private String baba;
		private String myString;
		boolean vologic = true;
		boolean thereYet = false;
		int which = 0;
		MouseListener ml, mg;
		Font big = new Font("Courier", Font.BOLD, 20);
		
		
	/***************************************************************************
	 * GUI constructor. adds layout manager.
	 ***************************************************************************/
	public firstGUI()
	{	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width,dim.height);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation( (dim.width/2 - this.getSize().width/2), (dim.height/2 - this.getSize().height/2));
		this.layoutComponents();
	    this.setUndecorated(true);
		this.layoutComponents();
	
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
	 	this.getRootPane().getActionMap().put("ESCAPE", new AbstractAction()
	 	{ 
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
	        {
	           try {	whoopsHoldOn();} catch (IOException e1) {}
	        }
	    }); 
	}
		
/** Helper method to add components to the frame 		 
 * @throws InterruptedException */
public void layoutComponents()
{
	// top panel is just tiny fillers replacing decoration frame.
	up = new JPanel();
	add(up, BorderLayout.NORTH);
	upp = new JLabel("");
	up.setBackground(Color.black);	up.setForeground(Color.black);
	up.add(upp);
	mid = new JPanel();
	add(mid, BorderLayout.CENTER);
	mid.setBackground(Color.black);	mid.setForeground(Color.black);
	left = new JPanel();
	add(left, BorderLayout.WEST);
	leftt = new JLabel("");
	left.add(leftt);
	left.setBackground(Color.black); left.setForeground(Color.black);
	right = new JPanel();
	add(right, BorderLayout.EAST);
	rightt = new JLabel("");
	right.add(rightt);
	right.setBackground(Color.black); right.setForeground(Color.black);
	down = new JPanel();
	down.setBackground(Color.black); down.setForeground(Color.black);
	add(down, BorderLayout.SOUTH);
	downn = new JLabel("");
	down.add(downn);
	
	display = new JTextArea();	
	add(display, BorderLayout.CENTER);
	display.setBackground(Color.black);
	display.setForeground(Color.GREEN);
	Font myFont = new Font("Courier", Font.PLAIN,14);
	display.setFont(myFont);
	display.setEditable(false);
	display.setBorder(BorderFactory.createMatteBorder(20, 25, 20, 25, Color.black));
	
	entry = new JTextArea(43, 30);
	entry.setFont(myFont);
	entry.setBackground(Color.black);
	entry.setForeground(Color.GREEN);
	entry.requestFocusInWindow();
	entry.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.black));
	entry.setEditable(false);
	entry.addKeyListener(new KeyListener()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
	    	if(e.getKeyCode() == KeyEvent.VK_ENTER)
	    	{
	    		e.consume();	
	    		user = entry.getText();
	    		System.out.println("@102: " + user);;
					System.out.println("@104: " + user);
					try {passwordChecker();} catch (InterruptedException e1) {	}
	    	}
	    }
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	});
	add(entry, BorderLayout.SOUTH);
	
	SquareCaret fc = new SquareCaret();							//  Create my Square caret
	fc.setBlinkRate(600);
	fc.addChangeListener(null);
	entry.setCaret(fc);
	entry.setCaretColor(Color.green);
	
	min = new JButton("_");										//  Utility buttons.
	max = new JButton("[ ]");			
	vol = new JButton("");	
	quit = new JButton(" X ");
	
	go = new JButton("  ");
	go.setBackground(Color.black);
	go.setForeground(Color.green);
	go.setBorder(new LineBorder(Color.BLACK, 2));
	try {
	    Image img = ImageIO.read(getClass().getResource("Resources/LOTR.png"));
	go.setIcon(new ImageIcon(img)); } 
	catch (IOException ex) {  }
	goListener();
	
	min.setBackground(Color.black);
	min.setForeground(Color.GREEN);
	max.setBackground(Color.black);
	max.setForeground(Color.GREEN);
	quit.setBackground(Color.black);
	quit.setForeground(Color.GREEN);
	
	try 
	{	Image img = ImageIO.read(getClass().getResource("Resources/vol2.png"));
		vol.setIcon(new ImageIcon(img)); 
	} 
	catch (IOException ex) {  }
	
	vol.setMargin(new Insets(0, 0, 0, 0));
	vol.setBackground(Color.black);
	vol.setForeground(Color.GREEN);
	vol.setMaximumSize(new Dimension(20,20));
	vol.setHorizontalAlignment(SwingConstants.RIGHT);
	
	min.addActionListener(this);
	quit.addActionListener(this);
	vol.addActionListener(this);
	go.addActionListener(this);
	quit.addMouseListener(new MouseListener() 
	{
	    public void mouseReleased(MouseEvent e) {}
	    public void mousePressed(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) {	quit.setBackground(Color.black);  quit.setForeground(Color.green);     }
	    public void mouseEntered(MouseEvent e) { quit.setBackground(Color.red);  quit.setForeground(Color.white); }
	    public void mouseClicked(MouseEvent e) {}
	});
	
}


// Password access only.	
	public void passwordChecker() throws InterruptedException
	{	
		if (which == 0)
		{				
			if (entry.getText().equals("SWORDFISH")||entry.getText().equals("Swordfish")||entry.getText().equals("swordfish"))
			{
				which++;
				System.out.println("@234: " +which);
				display.setText("");
				entry.setText("");	
				System.out.println("Line 231: " + which );
				
				add(go, BorderLayout.WEST);
				this.ml = new MouseAdapter()
				{  public void mouseReleased(MouseEvent e) {}    public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) 
			    {	
			    	display.setText("");
			    	add(go, BorderLayout.WEST);
			    try {
				    Image img = ImageIO.read(getClass().getResource("Resources/LOTR.png"));
					go.setIcon(new ImageIcon(img)); 
					goListener();}catch (IOException ex) {  }  
			    }
			    public void mouseEntered(MouseEvent e)  {  }  public void mouseClicked(MouseEvent e) {}
				};
				display.addMouseListener(ml);
	
				System.out.println(which + "\n");
				return;
			}
		}
						
		if (which == 1)	
		{			
			if (entry.getText().equals("Mellon")|| entry.getText().equals("mellon"))
			{	
				go.setIcon(null);
				display.removeMouseListener(ml);
				go.removeMouseListener(mg);
				System.out.println("gets to 257: Which is: " + which);
				entry.setText("");
				which++;
				System.out.println("gets to 260: Which is: " + which);
				try { Thread.sleep(500);} catch (InterruptedException e) {}
				
				display.setFont(big);
				display.append("\n   ");
				display.append("He saw the robbers, as soon as they came under the tree, each unbridle his horse and hobble it. All took off ");
				System.out.println("gets to 269");
				display.append("\n   ");
				display.append("their saddlebags, which proved to be full of gold and silver. The man who seemed ");
				display.append("\n   ");
				System.out.println("gets to 272");
				display.append("to be the captain presently pushed forward, load on shoulder, through thorns and thickets: ");
				entry.setCaretPosition(entry.getText().length());
				return;
			}
		}
				
		if (which == 2)
		{	
			if (entry.getText().equals("Open Sesame")||entry.getText().equals("Open, Sesame")||entry.getText().equals("open sesame")||entry.getText().equals("Open, sesame")||entry.getText().equals("Open, sesame!")||entry.getText().equals("Open, Sesame!"))
			{
				entry.setText("");
				display.setText("\n   Quite right. Please come in.");
				launchNext();
				JOptionPane.showMessageDialog(null, "(In future, you can skip all this by pressing escape during the opening sequence and entering '^_^^_^'", "Password skipper.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}//  end of password bit.
	
/**
 * Listen for and react to button press events (uses helper methods 3&4 below)
 * @param e the event
 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == quit)						
			{
				Object [] options = {"I'M OUTTA HERE", "NAH, MORE COOLNESS"};
				int choice = JOptionPane.showOptionDialog(null, "Sure?", "Stop being cool", JOptionPane.YES_NO_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
							if (choice == 0 )
							{
								System.exit(0);
							}
			}
							
			if (e.getSource() == min)                     
			{
				FadeUtilityClass.fade(this, true);
				this.setState(1);
				try { Thread.sleep(250);} catch (InterruptedException x){}
	
			}
			
			if (e.getSource() == vol)                     
			{
				if (vologic)
				{
					try {
						Image img = ImageIO.read(getClass().getResource("Resources/voloff.png"));
						vol.setIcon(new ImageIcon(img)); } 
					catch (IOException ex) {  }
					vologic = false;
					//AudioPlayer.stop();   
				}
				else if (!vologic)
				{
					try {
						Image img = ImageIO.read(getClass().getResource("Resources/vol2.png"));
						vol.setIcon(new ImageIcon(img)); } 
					catch (IOException ex) {  }
					vologic = true;
					//AudioPlayer.loop();   
				}
			}

			if (e.getSource() == go)                     
			{
				
			}
        }
			
		
	public void popupButtons() throws InterruptedException
	{
		try { Thread.sleep(1000);} catch (InterruptedException e) {}
		printSlow("  Hey!");
		try { Thread.sleep(1000);} catch (InterruptedException e) {}
		printSlow(" Don't be afraid!");
		try { Thread.sleep(1000);} catch (InterruptedException e) {}
		printSlow(" Here are some comforting buttons: ");
		try { Thread.sleep(1000);} catch (InterruptedException e) {}
		up.add(min); 	
		display.setText("");
		try { Thread.sleep(400);} catch (InterruptedException e) {}
		up.add(max); 
		display.setText(".");
		try { Thread.sleep(400);} catch (InterruptedException e) {}
		up.add(quit);
		display.setText("");
		try { Thread.sleep(500);} catch (InterruptedException e) {}
		right.add(vol);
		printSlow(" \n   Yeah! ");
		try { Thread.sleep(500);} catch (InterruptedException e) {}
		printSlow(" \n   That is better.");
		try { Thread.sleep(1500);} catch (InterruptedException e) {}
		display.setText("");
		printSlow(" \n   Now, let us begin.");
		try { Thread.sleep(1500);} catch (InterruptedException e) {}
		display.setText("");
		try { Thread.sleep(500);} catch (InterruptedException e) {}
		display.setFont(big);
		printSlow("   Hello stranger! What is the codeword?");
		entry.setEditable(true);
		entry.requestFocusInWindow();
		entry.setCaretPosition(0);
	}
		
	
		
public void printSlow(String in) throws InterruptedException
{
		int stringLength = in.length();		
		for (int i = 0; i< stringLength; i++)
		{ 
			display.append(in.substring(i, (i+1)) );		  	 	// Append a char, string is needed so "" is attached
			display.setCaretPosition(display.getText().length());  	// Set caret position to last for the effect
			Thread.sleep(50);
		}		  
} // end printSlow




public void goListener()
{
	this.mg = new MouseAdapter()
	{
	    public void mouseReleased(MouseEvent e) {}
	    public void mousePressed(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) 
	    {	
	    	add(go, BorderLayout.WEST);
	    try {
		    Image img = ImageIO.read(getClass().getResource("Resources/LOTR.png"));
			go.setIcon(new ImageIcon(img)); }catch (IOException ex) {  }  
	    }
	    public void mouseEntered(MouseEvent e) 
	    { remove(go);
	      display.setText("Ennyn Durin Aran Moria. Pedo mellon a Minno.\n\nIm Narvi hain echant. Celebrimbor o Eregion \n\ntethant. I thiw hin."); 
	    }
	    public void mouseClicked(MouseEvent e) {}
	};
	
	go.addMouseListener(mg);
}



public Action whoopsHoldOn() throws IOException
{
	Object [] options = {"I'M OUTTA HERE", "NAH, MORE COOLNESS"};
	int choice = 1;
	
		if (!thereYet)
		{
			final JPasswordField pwd = new JPasswordField(10);  
			JOptionPane pane = new JOptionPane(pwd,JOptionPane.OK_CANCEL_OPTION);
			JDialog dialog = pane.createDialog("To skip this, enter Super Password");
		    
		    dialog.addComponentListener(new ComponentListener(){
		        @Override public void componentShown(ComponentEvent e) {
		            pwd.requestFocusInWindow();}
		        @Override public void componentHidden(ComponentEvent e) {}
		        @Override public void componentResized(ComponentEvent e) {}
		        @Override public void componentMoved(ComponentEvent e) {}
		        });
		    dialog.setVisible(true);
		    
			char[] izzit = pwd.getPassword();
			String isIt =  new String(izzit);
		   System.out.println(izzit);
		   System.out.println(isIt);
			if (isIt.equals("^_^^_^"))
			{
				System.out.println("YAY");
				launchNext();
			}
			else 
			choice = JOptionPane.showOptionDialog(this,"Nope! Quit?", isIt, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			if (choice == 0)
			{
				System.exit(0);
			}
		}
		
		
		else
		{
			choice = JOptionPane.showOptionDialog(null, "Sure?", "Stop being cool", JOptionPane.YES_NO_OPTION, 
			JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
				if (choice == 0 )
				{
					System.exit(0);
				}
				return null;
		}
		return null;
}


public void launchNext()
{
	titleGUI realBegin;
	try {
		realBegin = new titleGUI();
		thereYet = true;
		realBegin.setVisible(true);	
} 
	catch (IOException e) {

	}		
	this.dispose();
}

}









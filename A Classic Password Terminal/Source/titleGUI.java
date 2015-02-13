import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

public class titleGUI extends JFrame implements ActionListener
{
//  JComponent instance variables
		private JTextArea display, entry;
		private JPanel up, down, left, right, mid;
		private JLabel upp, downn, leftt, rightt;
		private JButton min, max, quit, vol;
		
		
	//  Application instance variables
		private String kristi;
		private String friend;
		private String baba;
		private String myString;
		boolean vologic = true;
		boolean thereYet = false;
		
		
	/** GUI constructor. adds layout manager to the frame
	 * @throws IOException 
	 */
public titleGUI() throws IOException
{
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setSize(dim.width,dim.height);
	this.setTitle("Cipher GUI");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setLocation( (dim.width/2 - this.getSize().width/2), (dim.height/2 - this.getSize().height/2));
	this.layoutComponents();
    this.setUndecorated(true);

	KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
	this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
 	this.getRootPane().getActionMap().put("ESCAPE", new AbstractAction()
 	{ 
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e)
        {
           whoopsHoldOn();
        }
    });
 	
 	addPic();
}

public void layoutComponents() throws IOException
{
	// top panel is just tiny fillers replacing decoration frame.
	up = new JPanel();
	add(up, BorderLayout.NORTH); 	upp = new JLabel("");
	up.setBackground(Color.black);	up.setForeground(Color.black);	up.add(upp);

	
	left = new JPanel();
	add(left, BorderLayout.WEST);	leftt = new JLabel("");
	left.add(leftt);	left.setBackground(Color.black); left.setForeground(Color.black);
	
	right = new JPanel();
	add(right, BorderLayout.EAST);	rightt = new JLabel("");
	right.add(rightt);	right.setBackground(Color.black); right.setForeground(Color.black);
	
	down = new JPanel();	down.setBackground(Color.black); down.setForeground(Color.black);
	add(down, BorderLayout.SOUTH);	downn = new JLabel("");	down.add(downn);
	
	display = new JTextArea();
	display.setBackground(Color.black);	display.setForeground(Color.GREEN);
	Font myFont = new Font("Courier", Font.PLAIN,14);	display.setFont(myFont);
	display.setEditable(false);
	
	entry = new JTextArea(15, 30);
	entry.setFont(myFont);	add(entry, BorderLayout.SOUTH);
	entry.setBackground(Color.black);	entry.setForeground(Color.GREEN);
	
	SquareCaret sc = new SquareCaret();
	sc.setBlinkRate(600);
	sc.addChangeListener(null);
	display.setCaret(sc);
	entry.setCaret(sc);
	display.setCaretColor(Color.green);
	
	min = new JButton("_");										//  Utility buttons.
	max = new JButton("[ ]");			
	vol = new JButton("");	
	quit = new JButton(" X ");
	min.setBackground(Color.black);
	min.setForeground(Color.GREEN);
	max.setBackground(Color.black);
	max.setForeground(Color.GREEN);
	quit.setBackground(Color.black);
	quit.setForeground(Color.GREEN);
	
	try {
	    Image img = ImageIO.read(getClass().getResource("Resources/vol2.png"));
	vol.setIcon(new ImageIcon(img)); } 
	catch (IOException ex) {  }
	vol.setMargin(new Insets(0, 0, 0, 0));
	vol.setBackground(Color.black);
	vol.setForeground(Color.GREEN);
	vol.setMaximumSize(new Dimension(20,20));
	vol.setHorizontalAlignment(vol.RIGHT);
	
	min.addActionListener(this);
	quit.addActionListener(this);
	vol.addActionListener(this);
	
	up.add(min); 	
	up.add(max); 
	up.add(quit);
	right.add(vol);
}

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
		
}

// Method to pause after printing each character of a string.
public void printSlow(String in) throws InterruptedException
{
		int stringLength = in.length();		
		char[] inArr = in.toCharArray();								//  Convert to array for coding. 
		for (int i = 0; i< stringLength; i++)
		{ 
			display.append(in.substring(i, (i+1)) );		  	 		// Append a char, string is needed so "" is attached
			display.setCaretPosition(display.getText().length());  		// Set caret position to last for the effect
			Thread.sleep(60);
		}		  
} // end printSlow


	
public Action whoopsHoldOn()
{
	Object [] options = {"I'M OUTTA HERE", "NAH, MORE COOLNESS"};
	
			int choice = JOptionPane.showOptionDialog(null, "Sure?", "Stop being cool", JOptionPane.YES_NO_OPTION, 
			JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
				if (choice == 0 )
				{
					System.exit(0);
				}
				return null;
}


public void addPic()
{
	/*BufferedImage myPicture;
	try { myPicture = ImageIO.read(new File("Resources/Screens/Backstage.png"));
	JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	add(picLabel, BorderLayout.CENTER); 
	} 
	catch (IOException e) {
	}*/
	
	JButton go = new JButton("  ");
	go.setBackground(Color.black);
	go.setForeground(Color.green);
	go.setBorder(new LineBorder(Color.BLACK, 2));
	try {
	    Image img = ImageIO.read(getClass().getResource("Resources/Screens/Backstage.png"));
	go.setIcon(new ImageIcon(img)); } 
	catch (IOException ex) {  }
	add(go, BorderLayout.CENTER);
	
}




}

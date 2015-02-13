package GoL;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.util.List;


public class GameOfLife extends JFrame implements ActionListener {

	private LifePanel lp;
	private JPanel northPanel, centerPanel, southPanel, westPanel;
	private LifeWorld lw;
	private LifeTask lt;
	private JButton startButton,stopButton,resetButton,randomButton, quitButton, colorButton;
	private JTextField randomField,startField,progressField;
	private JLabel titleLabel, periodLabel, crowdLabel, progressLabel;
	private int currentColour = 0;
	private Color offColor = Color.white;
	private Color onColor = Color.black;
	private Color lineColor = Color.black;

	/********************************************************************************************
	 *   Constructor
	 ********************************************************************************************/
	GameOfLife() 
	{
		northPanel = new JPanel();
		westPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(5,2));
		Border north = new BevelBorder(0, Color.BLACK, Color.GRAY);
		northPanel.setBorder(north);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(westPanel, BorderLayout.WEST);
		
	//  Gets rid of the ugly JFrame surrounding frame.
	this.setUndecorated(true);
	getContentPane().setBackground(Color.black);
	Border bevel = new BevelBorder(0, Color.BLACK, Color.GRAY);
	Border line = BorderFactory.createLineBorder(Color.BLACK, 3);
	Border undecorBorder = BorderFactory.createCompoundBorder(bevel, line);
	((JComponent) this.getContentPane()).setBorder(undecorBorder);
	
	//  Centers GUI using two methods. 		
	setSize(800,600);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	
	this.setLocation(getCentralWidth(dim), getCentralHeight(dim));	
	
	/********************************************************************************************
	 *  Components and formatting.
	 ********************************************************************************************/
		// northPanel
		titleLabel = new JLabel("Technicalities' Game of Life.");
		titleLabel.setFont(new Font("Courier", Font.BOLD, 24));
		northPanel.add(titleLabel);
		
		JLabel titleFiller = new JLabel(String.format("%100s",  " "));
		northPanel.add(titleFiller);
	
		quitButton = makeButton(" X ");	
		quitButton.addMouseListener(new MouseListener() 
		{	public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {	quitButton.setBackground(Color.black);  quitButton.setForeground(Color.green);     }
		public void mouseEntered(MouseEvent e) { quitButton.setBackground(Color.red); quitButton.setForeground(Color.white); }
		public void mouseClicked(MouseEvent e) {}
		});
		northPanel.add(quitButton);
		
		// westPanel
		startButton = makeButton("start");
		stopButton = makeButton("stop");
		resetButton = makeButton("clear");
		randomButton = makeButton("random");
		colorButton = makeButton("colour");
		stopButton.setEnabled(false);
		
		// southPanel
		JLabel southFiller = new JLabel(String.format("%100s",  " "));
		southPanel.add(southFiller);
		JLabel southFiller2 = new JLabel(String.format("%100s",  " "));
		southPanel.add(southFiller2);
		JLabel periodFiller = new JLabel("Length of geological era:    ",SwingConstants.RIGHT);
		southPanel.add(periodFiller);
		startField = makeField("100");

		
		JLabel crowdFiller = new JLabel("Crowdedness:    ", SwingConstants.RIGHT);
		southPanel.add(crowdFiller);
		randomField = makeField("0.2");

		
		progressLabel = new JLabel("Generations so far:    ",SwingConstants.RIGHT);
		southPanel.add(progressLabel);
		progressField = makeField("0");
		progressField.setEditable(false);
		

		JLabel filler = new JLabel(String.format("%30s",  " "));
		northPanel.add(filler);
		
		


/********************************************************************************************
 *  Define LifeWorld
 ********************************************************************************************/	
		int width = 50;
		lw = new LifeWorld(width);
		lw.randomise();
		lp = makePanel(width);

		pack();
		setVisible(true);
	}

	
	// Make the text fields
	private JTextField makeField(String initText) {
		JTextField a = new JTextField(5);
		a.setText(initText);
		southPanel.add(a);
		return a;
	}
	// Make the panel
	private LifePanel makePanel(int width) {
		LifePanel a = new LifePanel(width);
		a.setPreferredSize(new Dimension(400,400));
		a.setBackground(Color.BLACK);
		a.setForeground(Color.GREEN);
		centerPanel.add(a);
		return a;
	}
	
	// Make the buttons
	private JButton makeButton(String caption) {
		JButton b = new JButton(caption);
		b.setActionCommand(caption);
		b.addActionListener(this);
		b.setBackground(Color.BLACK);
		b.setForeground(Color.GREEN);
		westPanel.add(b);
		return b;
	}
	
	
	//  Methods to center screen.
	private int getCentralWidth(Dimension dim) 
	{ return (dim.width/2 - this.getSize().width/2); }
	
	private int getCentralHeight(Dimension dim) 
	{ return (dim.height/2 - this.getSize().height/2); }
	
/********************************************************************************************
 *    Implement the action listener code
 ********************************************************************************************/
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand() == "start") 
		{
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			resetButton.setEnabled(false);
			randomButton.setEnabled(false);
			(lt = new LifeTask()).execute();
		}
		
		else if (e.getActionCommand() == "stop") 
		{
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			resetButton.setEnabled(true);
			randomButton.setEnabled(true);
			lt.cancel(true);
			lt = null;
		}
		
		else if (e.getActionCommand() == "random") 
		{
			Double probability = Double.parseDouble(randomField.getText());
			lw.setProbability(probability);
			lw.randomise();
			lp.repaint();
			progressField.setText("0");
		}
		
		else if (e.getActionCommand() == "clear") 
		{
			lw.clear();
			lp.repaint();
			progressField.setText("0");
		}
		
		else if (e.getActionCommand() == "colour") 
		{
			lp.toggleColour();
		}
		
		else if (e.getActionCommand() == " X ") 
		{	
			Object [] options = {"Leave", "More Life"};
			int choice = JOptionPane.showOptionDialog(null, "Are you sure?", "Stop being cool", JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
			if ( choice == 0 )
			{
				System.exit(0);
			}
		}
	}


/********************************************************************************************
 *    Create the worker that will run the task
 ********************************************************************************************/
	private class LifeTask extends SwingWorker<Void,Integer> 
	{
		protected Void doInBackground() 
		{
			try {
				Integer nSteps = Integer.parseInt(progressField.getText());
				Integer sleepTime = Integer.parseInt(startField.getText());
				while(!isCancelled()) {
					int a= lw.update(1);
					nSteps++;
					lp.repaint();
					if(a==0) 
						break;
					publish(nSteps);
					Thread.sleep(sleepTime);
				}
			}catch(InterruptedException e) {

			}
			return null;
		}
		protected void process(List<Integer> reports) {
			Integer lastReport = reports.get(reports.size()-1);
			progressField.setText(""+lastReport);
		}
		protected void done() {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			resetButton.setEnabled(true);
			randomButton.setEnabled(true);
		}
	}
	

/********************************************************************************************
 *    The interactive Game of Life screen.
 ********************************************************************************************/
private class LifePanel extends JPanel implements MouseListener 
{
	private int width;
	
	LifePanel(int width) 
	{	super();
		this.width = width;
		addMouseListener(this);
	}
	
	//  Block of mouselistener methods...//////////////////////
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) 
	{
		int clickX = e.getX();
		int clickY = e.getY();
		int dX = getWidth()/width;
		int posX,posY;
		posX = clickX/dX;
		posY = clickY/dX;
		lw.flip(posX,posY);
		repaint();
	}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	//////////////////////////////////////////////////////////
	
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawWorld(g2, onColor, offColor, lineColor);
	}
	
	private void drawWorld(Graphics2D g2, Color off, Color on, Color line) 
	{
		int dX = this.getWidth()/width;
		for(int i=0; i< width; i++) 
		{
			for(int j=0;j<width;j++) 
			{
				if (lw.get(i,j) == 0) 
					{ g2.setColor(offColor);	}
				else 
					{ g2.setColor(onColor); }
				
				g2.fillRect(i*dX,j*dX,dX,dX);
				g2.setColor(lineColor);
				g2.drawRect(i*dX,j*dX,dX,dX);
			}
		}
	}
	
	public void toggleColour()
	{
		if (currentColour == 0)
		{
			offColor = Color.BLACK; 
			onColor = Color.GREEN;
			lineColor = Color.BLACK;
			currentColour++;
		}
		else if (currentColour == 1)
		{
			offColor = Color.BLACK; 
			onColor = Color.GREEN;
			lineColor = Color.GREEN;
			currentColour++;
		}
		else if (currentColour == 2)
		{
			offColor = Color.WHITE; 
			onColor = Color.RED;
			lineColor = Color.BLACK;
			currentColour++;
		}
		else if (currentColour == 3)
		{
			offColor = Color.YELLOW; 
			onColor = Color.BLACK;
			lineColor = Color.BLACK;
			currentColour++;
		}
		else if (currentColour == 4)
		{
			offColor = Color.WHITE; 
			onColor = Color.BLACK;
			lineColor = Color.BLACK;
			currentColour = 0;
		}
		
	}
}


/********************************************************************************************
 *   Main
 ********************************************************************************************/
public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			new GameOfLife();
		}
	});
}

}


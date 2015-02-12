// Computes the primes in an inefficient way

	import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;

public class Primes extends JFrame implements ActionListener 
{
		private JLabel filler, numPrimes, otherPrime;
		private JButton startButton,stopButton,resetButton;
		private JTextField searchPoint,noFound;
		private JTextArea foundPrimes;
		private JScrollPane scrollPane;
		private JPanel dataPanel, midPanel, scrollPanel;
		private PrimeFinder pf;
		private int nFound = 0;
		
		public Primes() 
		{
			//super("Prime finder");
			dataPanel = new JPanel();
			midPanel = new JPanel(new FlowLayout());
			scrollPanel = new JPanel();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(dataPanel, BorderLayout.NORTH);
			add(midPanel, BorderLayout.SOUTH);
			add(scrollPanel, BorderLayout.CENTER);
			setSize(300,400);

		//  Centers GUI perfectly using two methods. 		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		
			this.setLocation(getCentralWidth(dim), getCentralHeight(dim));	

			numPrimes = new JLabel("  NO. PRIMES SO FAR!  ");
			otherPrime = new JLabel("  CURRENTLY CHECKING: ");
			filler = new JLabel(String.format("%30s","\n"));
			
			startButton = makeButton("Start");
			stopButton = makeButton("Stop");
			stopButton.setEnabled(false);
			resetButton = makeButton("Reset");
			resetButton.setEnabled(false);

			// This will display the number of primes I've found
			
			midPanel.add(numPrimes);
			noFound = new JTextField(10);
			noFound.setForeground(Color.BLACK);
			noFound.setText("0");
			midPanel.add(noFound);
			midPanel.add(filler);

			// This will display which integer I'm currently testing 
			midPanel.add(otherPrime);
			searchPoint = new JTextField(10);
			searchPoint.setText("0");
			searchPoint.setForeground(Color.BLACK);
			midPanel.add(searchPoint);

			// A scrollable text area to show all the ones found so far
			foundPrimes = new JTextArea(20,40);
			scrollPane = new JScrollPane(foundPrimes);
			scrollPanel.add(scrollPane);
			EmptyBorder eb = new EmptyBorder(new Insets(10, 30, 10, 10));        
	        foundPrimes.setBorder(eb);
	        Font font = new Font("Arial", Font.ITALIC, 12);
	        foundPrimes.setFont(font);
	        foundPrimes.setForeground(Color.BLACK);

			pack();
			setVisible(true);
		}

	
		private JButton makeButton(String caption) 
		{
			JButton b = new JButton(caption);
			b.setActionCommand(caption);
			b.addActionListener(this);
			dataPanel.add(b);
			return b;
		}

		
		// Manage the clicks - enabling and unenabling buttons as required
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getActionCommand() == "Start") 
			{
				// This line creates your primefinder object and executes
				(pf = new PrimeFinder()).execute();
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
				resetButton.setEnabled(false);
			}
			else if(e.getActionCommand() == "Stop") 
			{
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				resetButton.setEnabled(true);
				pf.cancel(true);
				pf = null;
			}
			else if(e.getActionCommand() == "Reset") 
			{
				searchPoint.setText("0");
				foundPrimes.setText("");
				noFound.setText("0");
				nFound = 0;
			}
}


/* The worker that does the prime finding. All workers must implement doInBackground() - other methods
   are optional. But publish() and process() if you want to do safe reporting */
private class PrimeFinder extends SwingWorker<Void, PrimePair> 
{
	int index = 1;
	public Void doInBackground() 
	{
		foundPrimes.append("These are the primes: \n");
	
		Integer maybeAPrime  = 2;
		
		while (!isCancelled())	
		{
				if (checkInteger(maybeAPrime))
				{	
					publish(new PrimePair(index, maybeAPrime));
					//System.out.println("Prime #"+ index +" is: "+ maybeAPrime);
					index++;
				}
				maybeAPrime++;	
		}
			return null;
	}

	

	// Displays the most recent current and nFound combination
	public void process(List<PrimePair> visits)
	{
		for (int i = visits.size(); i > 0; i--)
		{
			PrimePair pair = visits.get(visits.size() - i);
			searchPoint.setText(""+ pair.aPrime);
			noFound.setText(""+ pair.currCheck);	
			foundPrimes.append("The "+ pair.currCheck +"th prime is "+ pair.aPrime +".\n");
		}
	}
	

	// To search if N is a prime, loop i from 2 to N/2 and check whether or not N %i ==0 (this asks:
	// ‘is the remainder after division equal to zero?’). If this is the case for any i, then N is not a prime.
	public Boolean checkInteger(int numToCheck) 
	{
		if (numToCheck<=1) 
			{	return false;	}
		else 
		{	for (int i = 2; i <= numToCheck/2; i++) 
			{
				if (numToCheck % i == 0) 
				{	return false;	}
			}
		}
		return true;
	}
}

private int getCentralWidth(Dimension dim) 
{ return (dim.width/2 - this.getSize().width/2); }

private int getCentralHeight(Dimension dim) 
{ return (dim.height/2 - this.getSize().height/2); }

private static class PrimePair 
{
	private final Integer currCheck, aPrime;
    PrimePair(Integer checked, Integer aPrime) 
    {
        this.currCheck = checked;
        this.aPrime = aPrime;
    }
	
}
	
	
}


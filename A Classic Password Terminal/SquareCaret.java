

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;


/*********************************************************************************
 * Changes the 'caret' (typing cursor) from a line to a cool blinking square.
 * Adapted with a few changes from Loy et al's book "Java Swing".
 * 
 * @author technicalities
 *
 *********************************************************************************/

public class SquareCaret extends DefaultCaret 
{
  protected synchronized void damage(Rectangle r) 
  {
    if (r == null)
      return;

    x = r.x;
    y = r.y;
    height = r.height;
  
    if (width <= 0)
    	width = height;
    repaint(); 
  }

  public void paint(Graphics g) 
  {
    JTextComponent comp = getComponent();
    if (comp == null)
      return;

    int dot = getDot();
    Rectangle r = null;
    char dotChar;
    
    try 
    {	r = comp.modelToView(dot);
      	if (r == null)
      		return;
      dotChar = comp.getText(dot, 1).charAt(0);
    } 
    catch (BadLocationException e) 
    {     return;    }

    if ((x != r.x) || (y != r.y)) 
    {	repaint(); 
      	x = r.x; 
      	y = r.y;
      	height = r.height;
    }
    g.setColor(comp.getCaretColor());
    g.setXORMode(comp.getBackground()); 

    if (dotChar == '\n') 
    {	int diam = r.height;
       	if (isVisible())
    	   g.fillRect(r.x - diam / 2, r.y, diam, diam); 
                                    
       width = height;
       return;
    }

    if (dotChar == '\t')
    	try {
        Rectangle nextr = comp.modelToView(dot + 1);
        if ((r.y == nextr.y) && (r.x < nextr.x)) 
        {	width = nextr.x - r.x;
          	if (isVisible())
          		g.fillRoundRect(r.x, r.y, width, r.height, 12, 12);
          	return;
        } 
        else
          dotChar = ' ';
      } 
    	catch (BadLocationException e) 
    	{	dotChar = ' ';	}

    width = g.getFontMetrics().charWidth(dotChar);
    if (isVisible())
    	g.fillRect(r.x, r.y, width, r.height);
  }
  
}

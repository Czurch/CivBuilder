import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ImageViewer {
	
	JFrame frame;
	JLabel lbl;

	
	public ImageViewer(ImageIcon img)
	{
		frame = new JFrame("Civilization Generator");
		lbl = new JLabel();
		
		SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run(){
						
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						lbl.setIcon(img);
						frame.add(lbl);
						frame.pack();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					}
				});
	}
	
	public void LoadImage(ImageIcon img)
	{
		lbl.setIcon(img);
	}
}

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main {
	static int WORLD_HEIGHT = 256;
	static int WORLD_WIDTH = 256;
	static int world_age = 0;
	static int age_step = 1;
	
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Map world_map = new Map(WORLD_HEIGHT,WORLD_WIDTH);
		world_map.seed(40);
		BufferedImage img = GenerateMapImage(world_map);
		ImageIcon icon = new ImageIcon(img.getScaledInstance(WORLD_WIDTH, WORLD_HEIGHT, Image.SCALE_AREA_AVERAGING));
		ImageViewer IV = new ImageViewer(icon);
		while(true){
			world_map.age(age_step);
			world_age += age_step;
			System.out.println("World Age: " + world_age);
			
			img = GenerateMapImage(world_map);
			
			icon = new ImageIcon(img.getScaledInstance(WORLD_WIDTH*2, WORLD_HEIGHT*2, Image.SCALE_AREA_AVERAGING));
			//JOptionPane.showMessageDialog(null, null, "Another", JOptionPane.YES_NO_OPTION, new ImageIcon(img.getScaledInstance(WORLD_WIDTH, WORLD_HEIGHT, Image.SCALE_AREA_AVERAGING)));
			Thread.sleep(31);
			IV.LoadImage(icon);
		}
	}
	
	public static BufferedImage GenerateMapImage(Map world_map)
	{
		BufferedImage img = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		int[] pixels = new int[WORLD_WIDTH * WORLD_HEIGHT];
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				int i = x + y * WORLD_WIDTH;
				if(world_map.getState(x, y) == 0)
				{
					pixels[i] = 0x157516;				//wild
				}
				else if(world_map.getState(x, y) == 1)
				{
					pixels[i] = 0xd7ed34;				//farm
				}
				else if(world_map.getState(x, y) == 2)
				{
					pixels[i] = 0x282821;				//barren
				}
				else if(world_map.getState(x, y) == 3)
				{
					pixels[i] = 0x0046f9;				//village
				}
				else if(world_map.getState(x, y) == 4)
				{
					pixels[i] = 0x9e2f40;				//ruins
				}
				else if(world_map.getState(x, y) == 5)
				{
					pixels[i] = 0x000000;				//evil
				}
			}
		}
		img.setRGB(0, 0, WORLD_WIDTH, WORLD_HEIGHT, pixels, 0, WORLD_WIDTH);
		return img;
	}
}

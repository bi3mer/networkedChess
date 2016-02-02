package GameTest;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import KLD.Game;

public class ImageGame extends Game 
{
	Image im = l.loadImage("res/h.png");
	Image im2 = l.loadImage("res/h.png");

	@Override
	protected void init() 
	{
		BufferedImage resizedImage = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(im, 0, 0, 50, 50, null);
		g.dispose();
	}

	@Override
	protected void draw(Graphics g) 
	{
		//BufferedImage resizedImage = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		g.drawImage(im, 0, 0, 50, 250, null);
	}

	@Override
	protected void update() 
	{
		
	}
}

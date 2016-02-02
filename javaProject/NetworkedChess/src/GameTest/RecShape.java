package GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import KLD.Console;
import KLD.Game;
import KLD.Input;

public class RecShape extends Game {

	int num = 0;

	 
	int length = 102 ;
	boolean s;

	

	@Override
	public void init() {
		Console.setFPS(20);

	}

	@Override
	public void draw(Graphics g) {

		//g.drawString("Angles: " + ((double) num) / 100 * 360, 0, 10);
		//g.drawString("Num: " + num, 0, 20);

		for (int i = 0; i < 8; i += 1)
			rec(g, i * Math.PI / 4, 250, 240, length);

	}

	@Override
	public void update() {
/*
		if (s)
			length += 3;
		else
			length -= 3;

		if (length <= 100 || length >= 200) {
			s = !s;
		}
*/
		num++;

	}

	void rec(Graphics g, double pi, int x, int y, int length) {

		if (length <= 0)
			return;

		g.drawLine(x, y, x + (int) (length * Math.cos(pi)), y + (int) (length * Math.sin(pi)));
		
		rec(g, pi - ((double) num) / 100 * Math.PI, x	+ (int) (length * Math.cos(pi)),y + (int) (length * Math.sin(pi)), length - 1);
		
	}

	
}// end game

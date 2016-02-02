package GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JOptionPane;

import KLD.Game;
import KLD.obj.GameObject;
import KLD.obj.Player;

public class HamzaIceFall extends Game {

	Player player; 
	
	GameObject[] spikes; 
	
	int level; 
	
	int score;
	int number_spikes =2;
	int max_number_spikes = 0; 
	
	@Override
	protected void init() {
		String input = JOptionPane.showInputDialog("Enter level: 1-easy, 2-medium, 3-hard, 4-lagenday");

		while(!input.matches("[1234]"))
			input = JOptionPane.showInputDialog("Please enter a valid level: 1-easy, 2-medium, 3-hard, 4-lagenday");
		
		level = Integer.parseInt(input);
		
		player = new Player(0, maxHeight-50, 30, 50);
		max_number_spikes = maxWidth/20 + 1;
		spikes = new GameObject[max_number_spikes];
		
		for(int i=0; i<spikes.length;i++){
			spikes[i] = new GameObject(20, 30);
			spikes[i].setXY(-20, -30);

		}
		
		player.setXY(20, maxHeight-player.height());
		System.out.println(player.x() +" " +  player.y());
		
		initalizeSpikePosition(spikes);
		
		
		
	}

	private void initalizeSpikePosition(GameObject[] s) {
		int space = 2; 
		for(int i=0; i<s.length; i++){
			s[i].x(i*s[i].width() + space);
			s[i].y(0);
			space+=2;
			
		}

	}

	@Override
	protected void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; 
		for(int i=0; i<spikes.length;i++){
			
			if(spikes[i].y() < 0)
				spikes[i].drawMe(g2d, spikes[i].x(), 0);
			else
				spikes[i].drawMe(g2d);
			
			
		}
	
		g.setColor(Color.BLUE);
		
		player.drawMe(g);
	}

	@Override
	protected void update() {
		
		for(int i=0; i<number_spikes; i++){
			spikes[i].ya(-level);
			
			if(spikes[i].y()>maxHeight){
				score++; 
				if(number_spikes<max_number_spikes)
					number_spikes++; 
				spikes[i].y(-50);
			}
				
		}
		
		

	}//end update
	
	
	int random(int range){
		return new Random().nextInt(range);
	}
	

}

package KLD.cmd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;



import KLD.Game;
import KLD.Input;

public abstract class ButtonCommand extends Command{

	//private Rectangle button;
	
	Color color = Color.lightGray; 
	Color boarder = Color.darkGray.darker();
	Color over = Color.darkGray.brighter().brighter(); 
	Color textColor = Color.black;
	
	protected int x; 
	protected int y; 
	protected int width; 
	protected int height; 
	
	String label; 
	int sx; //string x
	int sy; //string y
	
	static int nxtPos = 0; 
	static int spacing = 1;
	float w =1f;
	
	boolean setSettings = true; 
	boolean click; 
	boolean placeSet = true; 
	protected boolean setNextToAnother = false;
	Font font= new Font(Font.SERIF, Font.PLAIN, 16); 
	
	public boolean isFocused = false;
	
	public ButtonCommand(String text,int x, int y){
		super(text +" Button");
		this.x = x; 
		this.y = y; 
		label = text;
		width = label.length()*font.getSize() ;
		width -= width/8;
		height = font.getSize();
		
	

		setSettings = true; 
		
		init();
		
	}
	
	
	

	public void setColor(Color button, Color border, Color text,Color over){
		color = button;
		boarder = border; 
		textColor = text; 
		this.over = over; 
		
	}
	
	public void setButtonColor(Color c){
		color = c;
	}
	
	public void setBorderColor(Color c){
		boarder = c;
	}
	
	public void setTextColor(Color c){
		textColor = c;
	}
	
	public void setBorderWidth(float f){
		w = f; 
	}
	
	public void createBorder(float width, Color c){
		setBorderWidth(width);
		setBorderColor(c);
	}
	
	public void setText(String s){
		label = s; 
	}
	
	
	public int getX(){
		return x; 
	}
	public int getY(){
		return y; 
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height; 
	}
	
	
	@Override
	public void callAction() {
		
			
		
		if(Game.input.mouseIsClicked(new Rectangle(x,y,width,height))){
			action();
		}
		
	}
	
	public abstract void action();


	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; 
		
		g.setFont(font);
		
		if(setSettings)
			setSettings(g);
		
		g.setColor(color);
		g.fillRect(x,y, width, height);//draw the background
		
		g.setColor(textColor); //draw text
		g.drawString(label, sx,  sy );
		
		g.setColor(boarder);//drawing the border
		//draw frame
		if(w>0f){
			Stroke s = g2d.getStroke(); //save prev settings 
			g2d.setStroke(new BasicStroke(w));
		    g.drawRect(x, y, width, height);
			g2d.setStroke(s);// return the prev stroke 
		}
		
		
	}

	private void setSettings(Graphics g){

		FontMetrics fm = g.getFontMetrics();
		width =  (int) fm.getStringBounds(label, g).getWidth();
		width += width/3;
		height = font.getSize();
		
		if(placeSet && setNextToAnother){
			x = nxtPos;
			nxtPos = x + width + spacing; 
			placeSet = false; 
		}
			
		
		sy = y + fm.getDescent()/2 + fm.getAscent();
		sx = x + width/8 ;
		
		
		
		setSettings = false; 
			
	}
	
	public void removeBorder(){
		w = 0f; 
	}
	
	
}

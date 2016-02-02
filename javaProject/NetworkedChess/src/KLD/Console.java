/**
 * @author KLD
 * 
 *A Console objected is created automatically in the main method, so you don't need to
 *	worry about creating one. But, there are two methods that should be known. 
 * 	setGame(game : Class) and setFPS(fps : int)
 *Console is a Console (which is a JPanel) that's runs games. Console has a static function
 *		setGame(game : Class) that is most used. You passed a game class 
 *		such as etc: Console.setGame(Game.class) . 
 * Console also can set the game's FPS using the function setFPS(fps : int) 
 * 
 * 
 */
package KLD;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public  class Console extends JPanel {
	//ignore this
	private static final long serialVersionUID = 1L;
	
	/**
	 * Holds the current game object
	 */
	private Game game;
	/**
	 * temporary holds a game class then creates an instance and sets it to {@link #game}
	 */
	private static Class<?> gameClass;
	
	/**
	 * Not really needed. TODO Make use of thread 
	 */
	private boolean isLoading = false; 
	 

	private static final int SECOND = 1000; 
	protected static Timer timer;
	//protected int time; 
	protected static int stage = 0; 
	protected static boolean stageChanged = false; 
	public static int maxWidth; 
	public static int maxHeight; 
	private static boolean showFPS = false; 
	public static Input input;
	
	private long delay ;
	private long x; 
 
	//protected KLDList loadingScreen ;
	
	public static final Loader loader = new Loader();
	
	
	
	public Clip backgroundClip;  
	
	
	
	public Console(Class<Game> mainGame ,int FPS){
		JFrame frame = GameFrame.frame;
		
		maxWidth = frame.getContentPane().getWidth();
		maxHeight = frame.getContentPane().getHeight();
		
		input = new Input(frame); 
		input.addListenerTo(this);//extra 
		
		
		delay = System.currentTimeMillis() ;
		x = System.currentTimeMillis() ;
		
		
		 timer = new Timer(SECOND/FPS,new ActionListener() {
			
			//calls actionPerformed using the timer object//independent of constructor
			@Override
			public void actionPerformed(ActionEvent e) {
				delay =  System.currentTimeMillis() -x;//TODO delay instead of x
				
			    	update();
					repaint(); 
				x = System.currentTimeMillis() ;
			}
		});
		 

	
		
		
		if(mainGame!=null){
			try {
				game = (Game) mainGame.newInstance();
				game.init();
				System.out.println("Main game is set");
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} 
		}	
		
		System.out.println("Console object is created");
	
	
	}
	
	public Console(int FPS) {
		this(null, FPS);
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	
		draw(g); //draw the Console 
		
		
		g.setColor(Color.BLACK);
		 if(showFPS)
			 g.drawString("FPS: " + SECOND/(delay), 10,10);
	}
	
	
	
	
	public void drawLoading(Graphics g){
		//TODO
	}
	
	
	
	
	public void run(){
		System.out.println("Console Running with "+getFPS()+" FPS");
		timer.start();
		
		
	}
	public void stop(){
		timer.stop();
	}
		
	public static void setFPS(int fps){	
		System.out.println("FPS set to " + fps);
		timer.setDelay(SECOND/fps);
		timer.restart();//TODO
	}
	
	public static int getFPS(){
		return 	1000/timer.getDelay();
	}

	public Timer getTimer(){
		return timer; 
	}

	
	//TODO remove
	public  String getRealTime(int time){
		return String.format("%2d:%2d", time/(60*SECOND/timer.getDelay()),((time/(SECOND/timer.getDelay()))%60)).replace(' ', '0');
	}




	public static void showFPS() {
		showFPS = true; 
	}


	public void draw(Graphics g) {
		if(isLoading || game==null){
			//loadingScreen.callAll(g);TODO
			g.drawString("Loading new game...", 50,50);
			System.out.println("Loading...");
		}
		else {
			game.callAllDraw(g);//calls the game draws
			//System.out.println(game.gameObjects.name);
		}
		
		
	}

	
	 
	public void update() {
	if(!isLoading && (game != null)){	
		game.callAllAction();//calls the game's actions
	}
	
		if(Console.stageChanged)
			switchPanel();
	
	
	}//end action

	

	private void switchPanel(){
		isLoading = true; 
		
		new Thread(){
			@Override
			public void run(){
				
				if(Console.stageChanged){
					System.out.println("Changing game...");
					Console.stageChanged = false;
				try {
					game = (Game) gameClass.newInstance();
					game.init();
					System.out.println("new Game is set. Name: " + ( (game.name==null)? "Unnamed" : game.name));
					} catch (InstantiationException	| IllegalAccessException e) {
						System.out.println("Cannot create " + gameClass.toString());
					}
				 stageChanged = false;
					isLoading = false;
				
					
				}
			}
			
		}.start();
		
	}

	public static void setGame(Class<?> c){
		stageChanged = true; 
		gameClass = c; 
	}
	
	
	
	
	//TODO setLoadingScreen(){} 
	
	
}

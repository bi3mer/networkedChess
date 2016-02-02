/**
 * @author KLD
  >Game: is an abstract class which has three abstract methods: 
  1- init():void; which gets called once when the game is lunched. 
  2- draw(g : Graphics):void; responsible for drawing on the screen and gets called after action.
  3- action():void; used to update variables in it's game and gets called first on every game. 
  
 >Game: Attributes
  -gameActions : ActionList; gets called by Console after calling action() and before Command's actions
  -gameCommands : CommandList ; it's action and draw gets called last separately. 
  -gameDraws : DrawList ; callAllDraws get called after draw() and before Command's draws. 
  -name : String; just a name of the game. 
  -input : Input; input object
  -l  : Loader  ; loads images and animations. 
  
 >Game: Non-Abstract Functions
 	-add(lo : LinkObject) : void; adds a LinkObject[Command, Action, Draw] and adds it to the appropriate list
 	-getCommand() : Command; returns a command object of the game that contains the game's draw and action
 	-getAction() : Action; returns an action object of the game that contains the game's action
 	-getDraw() : Draw; returns a draw object of the game that contains the game's draw
>Game: Static Functions
	-setFade(g : graphics, f : float) : void; sets g to f (out of 1.00) transept (fade)   	
 */
//




/*
 * 
 */
package KLD;

import intf.ActionFace;
import intf.CommandFace;
import intf.DrawFace;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.sql.Date;
import java.text.SimpleDateFormat;

import KLD.cmd.Action;
import KLD.cmd.ActionList;
import KLD.cmd.Command;
import KLD.cmd.CommandList;
import KLD.cmd.Draw;
import KLD.cmd.DrawList;
import KLD.cmd.KLDList;
import KLD.cmd.LinkObject;
import KLD.obj.GameObject;
import KLD.obj.IdObject;
/**
 * Game is an abstract class that represent a game or a state of a game which includes graphics, sound, and objects.
 * Game's abstract methods are:  init(), draw(Graphics), and update(). 
 * 
 * <p>init() : gets calls after the game object it created</p>
 * <p>draw(Graphics) : uses a Graphics to draw on <code>Console</code></p>
 * <p>update() : updates variables every frame </p>
 * 
 * After a game object is created, it may be passed to a <code>Console</code> using <code>Console.setGame(Game)</code>
 * @author KLD
 * @version 0.0
 *
 */
public abstract class Game implements CommandFace, ActionFace, DrawFace {
	
	/*
	 * TODO : 
	 * adding gameObjects to the game
	 * adding background music
	 * 
	 */

	/**
	 * holds a list of commands object. it's only initialized when it's being used.  
	 * @see {@link KLD.cmd.CommandList}
	 */
	protected CommandList gameCommands ;
	/**
	 * holds a list of action object. it's only initialized when it's being used.  
	 * @see {@link KLD.cmd.ActionList}
	 */
	protected ActionList gameActions ;
	/**
	 * holds a list of draw object. it's only initialized when it's being used.  
	 * @see {@link KLD.cmd.DrawList}
	 */
	protected DrawList gameDraws ;
	/**
	 * game name (optional). The name will appear on java's Console when game runs.
	 */
	protected String name; 
	
	/**
	 * holds a Point that represents the view of the game objects
	 */
	protected Point camera; 
	
	public static final Input input = Console.input;
	/**
	 * loader object that loads files which are located in /res
	 */
	public static final Loader l = new Loader();
	
	/**
	 * holds width of frame
	 */
	protected int maxWidth ;
	/**
	 * holds height of frame
	 */
	protected int maxHeight ;
	
	public Game(){
		camera = new Point(0,0);
		maxWidth = Console.maxWidth;
		maxHeight = Console.maxHeight;
		name = "Unnamed";
	}
	
	public Game(String name){
		this();
		this.name = name;
	}
	
	
	
	/**
	 * initialize the game once before starting it
	 */
	protected abstract void init() ;
	/**
	 * draws on the screen each frame after action()
	 * @param g frame's graphics
	 * @see #action()
	 */
	protected abstract void draw(Graphics g);
	/**
	 * updates variables each frame before draw()
	 * @see #draw(Graphics) 
	 */
	protected abstract void update() ;
	
	/**
	 * gets called before getting the Console switches to another game.
	 */
	public void end(){
		System.out.printf("%s game has ended",this.name);
	}
	
	/**
	 * adds a command, action, or draw to the appropriate list.  
	 * @param lo linkObject of instance Command, Action, or Draw 
	 */
	public void add(LinkObject lo){
		if(lo instanceof Command)
			addCommand((Command)lo);
		else if(lo instanceof Action)
			addAction((Action)lo); 
		else if(lo instanceof Draw)
			addDraw((Draw)lo); 
		else
			System.out.printf("LinkObject %s cannot be added", (lo.name==null)? "Unnamed" : lo.name);
		
		
	}
	
	public void addCommand(Command cmd){
		if(gameCommands==null)
			createCommandList();
		gameCommands.addLinkObject(cmd);
	}
	public void addAction(Action a){
		if(gameActions==null)
			createActionList();
		gameActions.addLinkObject(a);
	}
	
	
	public void addDraw(Draw d){
		if(gameDraws==null)
			createDrawList();
		gameDraws.addLinkObject(d);
	}
	
	//copy of action used for getAction and getCommand
	private void updateC(){ 
		update(); 
	}
	//copy of draw used for getDraw and getComand
	private void drawC(Graphics g){
		draw(g);
	}
	
	
	
	public Draw getDraw() throws NullPointerException{
		return new Draw(name+"Draw") {
			
			@Override
			public void draw(Graphics g) {
				drawC(g);
				}
		};
	}
	
	public Action getAction()throws NullPointerException{
		return new Action(name+"Action") {
			
			@Override
			public void action() {
				updateC();
				}};
			}
	
	public Command getCommand() throws NullPointerException{
		return new Command(name+"Command") {
			
			@Override
			public void draw(Graphics g) {
				drawC(g);
			}
			
			@Override
			public void action() {
				updateC();
			}};
	}
	
	public void setFade(Graphics g, float f){
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, f));
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	}





	@Override
	public void callAllAction() {
		maxWidth = Console.maxWidth;
		maxHeight = Console.maxHeight;
		update();
		if(gameActions!=null)
			gameActions.callAllAction();
		if(gameCommands!=null)
			gameCommands.callAllAction();
		
	}



	@Override
	public void callAllDraw(Graphics g) {
		draw(g);
		if(gameDraws!=null)
			gameDraws.callAllDraw(g);
		if(gameCommands!=null)
			gameCommands.callAllDraw(g);
	}
	
	
	protected void drawTime(Graphics g, int x, int y){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		Date date = new Date(System.currentTimeMillis());
		
		g.drawString(format.format(date),x,y);
	}

	@Override
	public void removeDraw(int id) {
		gameDraws.removeLinkObject(id);
	}

	@Override
	public void removeAction(int id) {
		gameActions.removeLinkObject(id);
		
	}
	
	@Override
	public void removeCommand(int id) {
		removeCommand(id);
	}



	@Override
	public void createDrawList() {
		gameDraws = new DrawList(this.name+"'s drawList");
	}



	@Override
	public void createActionList() {
		gameActions = new ActionList(this.name+"'s actionList");
	}

	@Override
	public void createCommandList() {
		gameCommands = new CommandList(this.name+"'s commandList");
	}
	
}//end Game

/*	GameObject
 *  @author KLD
 *  GameObject is anyObject that was created in the game. It has a x and y coordinates,
 *   and a width and height. 
 *  
 * 
 * Constructors:
 * ------------------------ 
 * 	A private constructor that sets the x and y  to 0 as well as creating a command,
 * 		 action,and draw lists.  
 * 	Three public constructors: 
 * 
 *  1) GameObject(width, height) sets the width and height. 
 * 	2) GameObject(image) sets an image, width and height of the image. 
 *  3) GameObject(animation) sets an animation, also sets the width and height to the
 * 	 	first frame of the animation. 
 * <soon>
 *  4)GameObject(shape) sets a shape, and width and height to maximum. 
 *  Features:
 *  ------------------------
 *   >DrawMe(g) : draws the objects animation if existed, else it's image, else a rectangle.
 *   >callAllAction(): calls actions and action commands of the object, as well as setting prevX and Y
 *   >callAllDraw(): calls draws and draw commands that was added to object. 
 *   >isOnTopOf, isUnder, isRightTo, isLeftTo (GameObject): returns true when a object is next to
 *     another object; You could also add bounds to increase the area of interference.  
 *  >isMoving(), 
 * 
 */

//[x] get functions for action, draw and command DONE
//TODO remove orbit to a new class called : Install

package KLD.obj;

import intf.ActionFace;
import intf.CommandFace;
import intf.DrawFace;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import KLD.Animation;
import KLD.Input;
import KLD.cmd.Action;
import KLD.cmd.ActionList;
import KLD.cmd.Command;
import KLD.cmd.CommandList;
import KLD.cmd.Draw;
import KLD.cmd.DrawList;

/**
 * 
 * 
 * This class was created to easily control, and draw objects in games. This
 * class uses commands, actions, and draws.
 * 
 * @author KLD
 * @version none
 * @see GameObject#callAllAction()
 * @see #callAllDraw(Graphics)
 * 
 * 
 */
public class GameObject extends IdObject implements ActionFace, DrawFace,
		CommandFace {

	/**
	 * holds the x position
	 */
	protected double x;
	/**
	 * holds the y position
	 */
	protected double y;

	/**
	 * holds the previous x position.
	 * 
	 */
	protected double prevX;
	/**
	 * holds the previous x position.
	 */
	protected double prevY;
	/**
	 * holds the width
	 */
	protected double width;
	/**
	 * holds the height
	 */
	protected double height;

	/**
	 * animation of the object. Can be set through the constructor
	 * 
	 * @see #GameObject(Animation)
	 * @see #getAnimationImage()
	 */
	public Animation animation;
	/**
	 * images of the object. Can be set through the constructor
	 */
	public Image image;
	/**
	 * Shape. Not very useful yet, so don't use it.
	 */
	public Shape shape;

	/**
	 * Holds command list
	 * 
	 * @see KLD.cmd.CommandList
	 */
	protected CommandList command; // commands <:)
	/**
	 * Holds an action list that is responsible for updating(setting) variables.
	 * 
	 * @see KLD.cmd.ActionList
	 */
	protected ActionList action; // actions <:)
	/*
	 * Holds an action list that is responsible for checking(if statements)
	 * changes.
	 * 
	 * @see KLD.cmd.ActionList
	 * 
	 * protected ActionList rule; // READ ME : rules are actions that doesn't
	 */// control the player(or any object), but they can decide if
		// the player is dead, immune, flying etc.
	// TODO create priorities
	/**
	 * Holds a draw list
	 * 
	 * @see KLD.cmd.DrawList
	 */
	protected DrawList draw; // draws <:)

	/**
	 * sets x,y to 0, width and height to 20, and creates new command, action,
	 * and draw lists. Then calls init()
	 * 
	 * @see #x
	 * @see #y
	 * @see #width
	 * @see #height
	 * @see #command
	 * @see #action
	 * @see #draw
	 * @see #init()
	 */
	protected GameObject() {
		x = 0;
		y = 0;
		width = 20;
		height = 20;

		init();
	}

	/**
	 * Calls a private constructor which : calls a private constructor ,then
	 * sets width to w and height to h
	 * 
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @see #GameObject()
	 * @see #init()
	 */
	public GameObject(double w, double h) {
		this();
		width = w;
		height = h;
	}

	/**
	 * @param im
	 *            sets the objects image, width, and height to the images
	 * @see #GameObject(double, double)
	 */
	public GameObject(Image im) {
		this(im.getWidth(null), im.getHeight(null));
		image = im;
	}

	/**
	 * 
	 * @param ani
	 *            is set to the object. And the first frame of ani will be the
	 *            width and height.
	 * @see #GameObject(double, double)
	 */

	public GameObject(Animation ani) {
		this(ani.getImageAt(0).getWidth(null), ani.getImageAt(0)
				.getHeight(null));
		animation = ani;
	}

	// end of constructors
	/**
	 * Created for overriding. This method is called by all constructors.
	 */
	public void init() {
		// empty method. used only for overriding
		// it gets called at the end of constructor
	}

	// get methods

	/**
	 * @return an integer of x position
	 */
	public int x() {
		return (int) x;
	}

	/**
	 * set x to the parameter
	 * 
	 * @param n
	 *            number
	 */
	public void x(double n) {
		prevX = x;
		this.x = n;
	}

	/**
	 * adds a number to x position
	 * 
	 * @param delta
	 *            step
	 */
	public void xa(double d) {
		x(x() + d);
	}

	/**
	 * subtract
	 * 
	 * @param d
	 *            number
	 */
	public void xs(double d) {
		x(x() - d);
	}

	/**
	 * multiplication
	 * 
	 * @param d
	 */
	public void xm(double d) {
		x(x() * d);
	}

	/**
	 * divide
	 * 
	 * @param d
	 */
	public void xd(double d) {
		x(x() / d);
	}

	/**
	 * remainder
	 * 
	 * @param d
	 */
	public void xr(double d) {
		x(x() % d);
	}

	/**
	 * return x position relative to a point
	 * 
	 * @param p
	 *            Point
	 * @return int
	 */
	public int x(Point p) {
		return x() + p.x;
	}

	/**
	 * @return x + width
	 */
	public int xw() {
		return width() + x();
	}

	/**
	 * @return an integer of y position
	 */
	public int y() {
		return (int) y;
	}

	/**
	 * sets y to the parameter
	 * 
	 * @param n
	 */
	public void y(double n) {
		prevY = y;
		y = n;
	}

	/**
	 * return y position relative to Pointu
	 * 
	 * @param p
	 *            Pointu
	 * @return intu
	 */
	public int y(Point p) {
		return y() + p.y;
	}

	/**
	 * adds a number to y position
	 * 
	 * @param delta
	 *            step
	 */
	public void ya(double d) {
		y(y() + d);
	}

	/**
	 * subtract y position 
	 * 
	 * @param d
	 *            number
	 */
	public void ys(double d) {
		y(y() - d);
	}

	/**
	 * multiply y position 
	 * 
	 * @param d
	 */
	public void ym(double d) {
		y(y() * d);
	}

	/**
	 * divide y position 
	 * 
	 * @param d
	 */
	public void yd(double d) {
		y(y() / d);
	}

	/**
	 * remainder y position 
	 * 
	 * @param d
	 */
	public void yr(double d) {
		y(y() % d);
	}

	/**
	 * @return y + height
	 */
	public int yh() {
		return y() + height();
	}

	/**
	 * sets x and y
	 * 
	 * @param x
	 *            number
	 * @param y
	 *            number
	 */

	public void setXY(int x, int y) {
		prevX = this.x;
		prevY = this.y;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return an integer of width
	 */
	public int width() {
		return (int) width;
	}

	/**
	 * sets the width to n
	 * 
	 * @param n
	 */
	public void width(double n) {
		width = n;
	}

	/**
	 * @return an integer of height
	 */
	public int height() {
		return (int) height;
	}

	/**
	 * sets height to n
	 * 
	 * @param n
	 *            double
	 */
	public void height(double n) {
		height = n;
	}

	public void setWH(double w, double h) {
		width = w;
		height = h;
	}

	/**
	 * @return an image of the next image frame of animation
	 */
	public Image getNextFrame() {
		return animation.getNextFrame();
	}

	/**
	 * @return shape if exist, else returns a rectangle of the object
	 */
	public Shape getShape() {
		if (shape != null)
			return shape;
		return new Rectangle(x(), y(), width(), height());
	}
	
	/**
	 * 
	 * @param x 
	 * @param y
	 * @return a rectangle shape at x and y with the objects width and height
	 */
	public Shape getShape(int x, int y) {
		if (shape != null)
			return shape;
		return new Rectangle(x, y, width(), height());
	}
	
	

	// drawMeh!

	/**
	 * Draws: animation of exist, else image if exist. Else, calls
	 * <code>getShape()</code> and fills it.
	 * 
	 * @param g Graphics
	 * @param p relative to p 
	 * @see #getShape()
	 */
	public void drawMe(Graphics g, Point p) {
		Graphics2D g2d = (Graphics2D) g;
		if (!(animation == null)) // draw animation if exist
			g.drawImage(animation.getNextFrame(), x(p), y(p), null);
		else if (!(image == null)) // draw image if exist
			g.drawImage(image, x(p), y(p), null);
		else
			// draw shape.
			g2d.fill(getShape());
	}
	
	
/**
 * draws the object. Prioritizes animation then image then shape, and default shape is rectangle
 * @param g Graphics
 */
	public void drawMe(Graphics g) {
		drawMe(g, new Point(0, 0));
	}
	/** Draws the object at x y 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawMe(Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		if (!(animation == null)) // draw animation if exist
			g.drawImage(animation.getNextFrame(), x, y, null);
		else if (!(image == null)) // draw image if exist
			g.drawImage(image, x, y, null);
		else
			g2d.fill(getShape(x,y ));
	}

	/**
	 * calls <code>action.callAllAction</code> then calls
	 * <code>command.callAllAction</code>
	 */
	public void callAllAction() {
		if (action != null)
			action.callAllAction();
		if (command != null)
			command.callAllAction();
	}

	/**
	 * Draws <code>draw.callAllDraw(g)</code> then calls
	 * <code>command.callAllDraw(g)</code>
	 * 
	 * @see {@link KLD.cmd.Draw}
	 * @see {@link KLD.cmd.DrawList}
	 * @see {@link KLD.cmd.Command}
	 * @see {@link KLD.cmd.Command}
	 */
	public void callAllDraw(Graphics g) {
		if (draw != null)
			draw.callAllDraw(g);
		if (command != null)
			command.callAllDraw(g);
	}

	private boolean inBetween(int x, int a, int b) {
		return (x >= a && x <= b);
	}

	// location methods
	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @param leftBound
	 *            adds more width on the left side of this game object
	 * @param rightBound
	 *            adds more width to the right side of this game object
	 * @param upperBound
	 *            adds more height to the top of this game object
	 * @param bottomBound
	 *            adds more height to the bottom of this game object
	 * @return returns <code>true</code> if the object is on top of
	 *         <code>go</code>
	 */
	public boolean isOnTopOf(GameObject go, int leftBound, int rightBound,
			int upperBound, int bottomBound) {
		if (this.width() <= go.width()) {
			if ((inBetween(this.x(), go.x() - leftBound, go.x() + go.width()
					+ rightBound) || inBetween(this.x() + this.width(), go.x()
					- leftBound, go.x() + go.width() + rightBound))
					&& inBetween(this.y() + this.height(),
							go.y() - bottomBound, go.y() + upperBound))
				return true;
			return false;
		}// end if(this.width...)
		else
			return go.isUnder(this, leftBound, rightBound, upperBound,
					bottomBound);

	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @return true if this object is on top of the game object.
	 * @see #isOnTopOf(GameObject, int, int, int, int)
	 */
	public boolean isOnTopOf(GameObject go) {
		return isOnTopOf(go, 0, 0, 0, 0);
	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @param leftBound
	 *            adds more width on the left side of this game object
	 * @param rightBound
	 *            adds more width to the right side of this game object
	 * @param upperBound
	 *            adds more height to the top of this game object
	 * @param bottomBound
	 *            adds more height to the bottom of this game object
	 * @return returns <code>true</code> if the object is under <code>go</code>
	 */
	public boolean isUnder(GameObject go, int leftBound, int rightBound,
			int upperBound, int bottomBound) {
		if (this.width() <= go.width()) {
			if ((inBetween(this.x(), go.x() - leftBound, go.x() + go.width()
					+ rightBound) || inBetween(this.x() + this.width(), go.x()
					- leftBound, go.x() + go.width() + rightBound))
					&& inBetween(this.y(), go.y() + go.height() - bottomBound,
							go.y() + go.height() + upperBound))
				return true;
			return false;
		} else
			return go.isOnTopOf(this, leftBound, rightBound, upperBound,
					bottomBound);

	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @return true if this object is under go .
	 * @see #isUnder(GameObject, int, int, int, int)
	 */
	public boolean isUnder(GameObject go) {
		return isUnder(go, 0, 0, 0, 0);
	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @param leftBound
	 *            adds more width on the left side of this game object
	 * @param rightBound
	 *            adds more width to the right side of this game object
	 * @param upperBound
	 *            adds more height to the top of this game object
	 * @param bottomBound
	 *            adds more height to the bottom of this game object
	 * @return returns <code>true</code> if the object is on the left side to
	 *         <code>go</code>
	 */
	public boolean isLeftTo(GameObject go, int upperBound, int bottomBound,
			int leftBound, int rightBound) {
		if (this.height() <= go.height()) {
			if ((inBetween(this.y(), go.y() - upperBound, go.y() + go.height()
					+ bottomBound) || inBetween(this.y() + this.height(),
						go.y() - upperBound, go.y() + go.height() + bottomBound))
					&& inBetween(this.x() + this.width(), go.x() - leftBound,
							go.x() + rightBound))
				return true;
			return false;
		} else
			return go.isRightTo(this, upperBound, bottomBound, leftBound,
					rightBound);

	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @returns <code>true</code> if the object is on the left side to
	 *          <code>go</code>
	 * @see #isRightTo(GameObject, int, int, int, int)
	 */
	public boolean isLeftTo(GameObject go) {
		return isLeftTo(go, 0, 0, 0, 0);
	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @param leftBound
	 *            adds more width on the left side of this game object
	 * @param rightBound
	 *            adds more width to the right side of this game object
	 * @param upperBound
	 *            adds more height to the top of this game object
	 * @param bottomBound
	 *            adds more height to the bottom of this game object
	 * @return returns <code>true</code> if the object is on the right side to
	 *         <code>go</code>
	 */
	public boolean isRightTo(GameObject go, int upperBound, int bottomBound,
			int leftBound, int rightBound) {
		if (this.height() <= go.height()) {
			if ((inBetween(this.y(), go.y() - upperBound, go.y() + go.height()
					+ bottomBound) || inBetween(this.y() + this.height(),
						go.y() - upperBound, go.y() + go.height() + bottomBound))
					&& inBetween(this.x(), go.x() + go.width() - leftBound,
							go.x() + go.width() + rightBound))
				return true;
			return false;
		} else
			return go.isLeftTo(this, upperBound, bottomBound, leftBound,
					rightBound);

	}

	/**
	 * @param go
	 *            the gameObject that is being compared with.
	 * @returns <code>true</code> if the object is on the right side to
	 *          <code>go</code>
	 * @see #isRightTo(GameObject, int, int, int, int)
	 */
	public boolean isRightTo(GameObject go) {
		return isRightTo(go, 0, 0, 0, 0);
	}

	/**
	 * @return true if object is moving in any direction
	 */
	public boolean isMoving() {
		return prevX != x || prevY != y;
	}

	/**
	 * @return true if object is moving left
	 */
	public boolean isMovingLeft() {
		return (prevX > x);
	}

	/**
	 * @return true if object is moving right
	 */
	public boolean isMovingRight() {
		return (prevX < x);
	}

	/**
	 * @return true if object is moving up
	 */
	public boolean isMovingUp() {
		return (prevY > y);
	}

	/**
	 * @return true if object is moving down
	 */
	public boolean isMovingDown() {
		return (prevX < x);
	}

	// Commands
	/**
	 * @param c
	 *            added to command list.
	 * @see #callAllAction()
	 * @see #callAllDraw(Graphics)
	 */
	public void addCommand(Command c) {
		if (command == null)
			createCommandList();
		command.addLinkObject(c);
	}

	/**
	 * prints a message after removing.
	 * 
	 * @param id
	 *            the command object that will be removed
	 */
	public void removeCommand(int id) {
		command.removeLinkObject(id);
	}

	/**
	 * prints a message after removing.
	 * 
	 * @param name
	 *            of the command object that will be removed
	 */
	public void removeCommand(String name) {
		command.removeLinkObject(name);
	}

	/*
	 * i don't think its efficient /**
	 * 
	 * @param c
	 * 
	 * public void removeCommand(Command c){ command.removeLinkObject(c); }
	 */
	/**
	 * creates a new empty command
	 */
	public void clearCommands() {
		command.clear();
	}

	// Action
	/**
	 * @param a
	 *            adds action to the objects action list
	 * @see #callAllAction()
	 * @see {@link KLD.cmd.Action}
	 * @see {@link KLD.cmd.ActionList}
	 */
	public void addAction(Action a) {
		if (action == null)
			createActionList();
		action.addLinkObject(a);
	}

	/**
	 * @param id
	 *            of the action that will be removed.
	 * @see {@link KLD.cmd.Action}
	 * @see {@link KLD.cmd.ActionList}
	 */
	public void removeAction(int id) {
		action.removeLinkObject(id);
	}

	/**
	 * It's only recommended to remove by name when a customized name is set to
	 * an action
	 * 
	 * @param name
	 *            of the action that will be removed.
	 * @see {@link KLD.cmd.Action}
	 * @see {@link KLD.cmd.ActionList}
	 */
	public void removeAction(String name) {
		action.removeLinkObject(name);
	}

	/*
	 * not recommended. use id or name public void removeAction(Action c){
	 * action.removeLinkObject(c); }
	 */
	/**
	 * creates a new action list
	 * 
	 * @see {@link KLD.cmd.ActionList}
	 */
	public void clearActions() {
		action.clear();
	}

	/*
	 * /** Creates a ruleList if doesn't exist, and adds an action object to it.
	 * 
	 * @param a action that will be added to the list
	 * 
	 * public void addRule(Action a) { if (rule == null) createRuleList();
	 * rule.addLinkObject(a); }
	 * 
	 * /**
	 * 
	 * @param ID the id of the rule that will be removed
	 * 
	 * public void removeRule(int ID) { rule.removeLinkObject(ID); }
	 * 
	 * /**
	 * 
	 * @param name of the rule that will be removed
	 * 
	 * public void removeRule(String name) { rule.removeLinkObject(name); }
	 * 
	 * /* public void removeRule(Action c){ rule.removeLinkObject(c); }
	 * 
	 * /** recreate a new list
	 * 
	 * public void clearRules() { rule.clear(); }
	 */
	// Draws
	/**
	 * Adds a draw object to drawList
	 * 
	 * @param d
	 *            draw object
	 * @see #callAllDraw(Graphics)
	 * @see {@link KLD.cmd.Draw}
	 * @see {@link KLD.cmd.DrawList}
	 */
	public void addDraw(Draw d) {
		if (draw == null)
			createDrawList();
		draw.addLinkObject(d);
	}

	/**
	 * removes object with a certain id. Prints a message that states if the
	 * object is removed or not found
	 * 
	 * @param id
	 *            of an object in drawList
	 */
	public void removeDraw(int id) {
		draw.removeLinkObject(id);
	}

	/**
	 * removes object with a certain name. Prints a message that states if the
	 * object is removed or not found
	 * 
	 * @param id
	 *            of an object in drawList
	 */
	public void removeDraw(String name) {
		draw.removeLinkObject(name);
	}

	/*
	 * not recommended public void removeDraw(Draw c){ draw.removeLinkObject(c);
	 * }
	 */
	/**
	 * creates new empty drawList
	 */
	public void clearDraws() {
		draw.clear();
	}

	/**
	 * moves the object one step of length <code>speed</code> towards
	 * <code>x & y</code>
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @param speed
	 *            step length
	 */
	public void approach(int x, int y, double speed) {
		double theta = Math.atan(Math.abs((this.y - y) / (this.x - x)));

		this.x += ((this.x < x) ? 1 : -1) * speed * Math.cos(theta);
		this.y += ((this.y < y) ? 1 : -1) * speed * Math.sin(theta);
	}

	/**
	 * add an action to the object.... not sure if this work, use approach.
	 * 
	 * @param x
	 *            stands for pokemonX
	 * @param y
	 *            do u question my function?
	 * @param speed
	 *            need more speed
	 * @return it's a void function god damn it.
	 */
	// TODO not sure what I did there. Should use 'approach' and also check the
	// spelling.
	public void moveTo(final int x, final int y, final double speed) {

		addAction(new Action(String.format("|id%s moveTo %s %s %.2f by %s ",
				id, x, y, speed, name), this) {

			double theta;
			double speedX;
			double speedY;

			@Override
			public void init() {
				theta = Math.atan(Math.abs((go.y - y) / (go.x - x)));
				speedX = ((go.x < x) ? 1 : -1) * speed * Math.cos(theta);
				speedY = ((go.y < y) ? 1 : -1) * speed * Math.sin(theta);
			}

			@Override
			public void action() {
				/*
				 * if(inBetween(go.x(), x- (int)speedX/2, x+(int)speedX/2 ) &&
				 * inBetween(go.y(),(y-(int)speedY/2), (y+(int)speedY/2)) ){
				 * go.x = x; go.y = y; this.deactivate(); go.removeAction(id); }
				 * go.x += speedX; go.y += speedY;
				 */
				if (inBetween(go.x(), x - (int) ((speedX + 3) / 2), x
						+ (int) (speedX + 3) / 2)
						&& inBetween(go.y(), (y - (int) (speedY + 3) / 2),
								(y + (int) (speedY + 3) / 2))) {
					go.x = x;
					go.y = y;
					this.deactivate();
					go.removeAction(id);
				}
				double theta = Math.atan(Math.abs((go.y - y) / (go.x - x)));

				go.x += speedX;
				go.y += speedY;

				speedX = ((go.x < x) ? 1 : -1) * speed * Math.cos(theta);
				speedY = ((go.y < y) ? 1 : -1) * speed * Math.sin(theta);

			}
		});

	}

	/**
	 * adds an action. Calls <code>moveTo(p.x ,p.y ,speed)</code>
	 * 
	 * @param p
	 *            point
	 * @param speed
	 *            speed
	 * @see GameObject#moveTo(int, int, double)
	 */
	public void moveTo(Point p, double speed) {
		moveTo(p.x, p.y, speed);
	}

	/**
	 * adds an action. Calls <code>moveTo(p.x ,p.y ,1.0)</code>
	 * 
	 * @param p
	 *            point
	 * @see GameObject#moveTo(int, int, double)
	 */
	public void moveTo(Point p) {
		moveTo(p.x, p.y, 1);
	}

	/**
	 * Adds an action to actionList. Calls <code>moveTo(x ,y ,1.0)</code>
	 * 
	 * @param x
	 *            coordinates
	 * @param y
	 *            coordinates
	 * @see #moveTo(int, int, double)
	 */
	public void moveTo(int x, int y) {
		moveTo(x, y, 1.0);
	}

	/**
	 * Adds an action that moves the object up to height <code>y</code> with
	 * speed <code>s</code>
	 * 
	 * @param y
	 *            max height
	 * @param s
	 *            speed
	 */
	public void moveUp(final int y, final double s) {

		this.addAction(new Action(String.format("%s MoveUp max:%s speed:%s",
				this.name, y, s), this) {

			@Override
			public void action() {

				if (go.y - s > y)
					go.y -= s;
				else {
					go.y = y;
					deactivate();
					go.removeAction(this.id);
				}
			}
		});
	}// end move

	/**
	 * Adds an action that moves the object up to height <code>y</code> with
	 * speed 1 Calls <code>moveUp(y, 1);</code>
	 * 
	 * @param y
	 *            max height
	 * @see #moveUp(int, double)
	 */
	public void moveUp(final int y) {
		moveUp(y, 1);
	}

	/**
	 * Adds an action that moves the object up. Calls
	 * <code>moveUp(Integer.MIN_VALUE)</code>
	 * 
	 * @see #moveUp(int)
	 */
	public void moveUp() {
		moveUp(Integer.MIN_VALUE);
	}

	/**
	 * Adds an action to the object. The object will continue to move down with
	 * speed <code>speed</code> until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 * @param speed
	 *            speed
	 */
	public void moveDown(final int max, final double speed) {

		this.addAction(new Action(String.format("%s MoveDown max:%s speed:%s",
				this.name, max, speed), this) {

			@Override
			public void action() {

				if (go.y + speed < y)
					go.y += speed;
				else {
					go.y = max;
					deactivate();
					go.removeAction(this.id);
				}
			}
		});
	}// end move

	/**
	 * Adds an action to the object. The object will continue to move down with
	 * speed 1 until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 * @see #moveDown(int, double)
	 */
	public void moveDown(int max) {
		moveDown(max, 1);
	}

	/**
	 * Adds an action to the object. The object will continue to move down
	 * forever
	 * 
	 * @see #moveDown(int, double)
	 */
	public void moveDown() {
		moveDown(Integer.MAX_VALUE);
	}

	/**
	 * Adds an action to the object. The object will continue to move right with
	 * speed <code>speed</code> until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 * @param speed
	 *            speed in which the object will move each frame
	 */
	public void moveRight(final int max, final double speed) {

		this.addAction(new Action(String.format("%s MoveRight max:%s speed:%s",
				this.name, max, speed), this) {

			@Override
			public void action() {

				if (go.x + speed < max)
					go.x += speed;
				else {
					go.x = max;
					deactivate();
					go.removeAction(this.id);
				}
			}
		});
	}// end move

	/**
	 * Adds an action to the object. The object will continue to move right
	 * until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 */
	public void moveRight(final int max) {
		moveRight(max, 1);
	}

	/**
	 * Adds an action to the object. The object will continue to move right
	 * forever
	 * 
	 * @param max
	 *            maximum point
	 * @param speed
	 *            speed in which the object will move each frame
	 */
	public void moveRight() {
		moveRight(Integer.MAX_VALUE);
	}

	/**
	 * Adds an action to the object. The object will continue to move left with
	 * speed <code>speed</code> until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 * @param speed
	 *            speed in which the object will move each frame
	 */
	public void moveLeft(final int max, final double speed) {

		this.addAction(new Action(String.format("%s MoveLeft max:%s speed:%s",
				this.name, max, speed), this) {

			@Override
			public void action() {

				if (go.x - speed > max)
					go.x -= speed;
				else {
					go.x = max;
					deactivate();
					go.removeAction(this.id);
				}
			}
		});
	}// end move

	/**
	 * Adds an action to the object. The object will continue to move right
	 * until reaching <code>max</code>
	 * 
	 * @param max
	 *            maximum point
	 */
	public void moveLeft(final int max) {
		moveLeft(max, 1);
	}

	/**
	 * Adds an action to the object. The object will continue to move left
	 * forever
	 */
	public void moveLeft() {
		moveLeft(Integer.MIN_VALUE);
	}

	/**
	 * Adds an action to the gameObject and lets it orbit around it's current
	 * point.
	 * 
	 * @param r
	 *            radius
	 * @param angelerVelocity
	 *            in radian
	 */
	public void orbit(final int r, final double angelerVelocity) {

		addAction(new Action("Orbit", this) {
			double pi = 0;
			double oX = go.x;
			double oY = go.y;

			@Override
			public void action() {

				go.x = oX + r * Math.cos(pi);

				go.y = oY + r * Math.sin(pi);

				pi += angelerVelocity;
			}

		});

	}

	/**
	 * Adds an action to the gameObject and lets it orbit around the mouse.
	 * 
	 * @param r
	 *            radius
	 * @param angelerVelocity
	 *            in radian
	 * @see {@link KLD.Input#point}
	 */
	public void orbitMouse(final int r, final double angelerVelocity) {

		addAction(new Action("OrbitMouse", this) {
			double pi = 0;

			@Override
			public void action() {

				go.x = Input.point.x + r * Math.cos(pi);

				go.y = Input.point.y + r * Math.sin(pi);

				pi += angelerVelocity;
			}

		});

	}

	/**
	 * Adds a draw that clones (draws) the game object after cloning the
	 * animation. Note that this animation does not effect the object's
	 * animation.
	 * 
	 * @param x
	 *            shift
	 * @param y
	 *            shift
	 * @see {@link KLD.Animation#getAnimation()}
	 * 
	 */
	public void cloneAnimation(final int x, final int y) {

		addDraw(new Draw("cloneAnimation", this) {

			Animation ani;

			@Override
			public void init() {
				ani = go.animation.clone();
				ani.setFrame(go.animation.getCurrentFrame());
			}

			@Override
			public void draw(Graphics g) {
				g.drawImage(ani.getNextFrame(), go.x() + x, go.y() + y, null);
			}

		});
	}

	/**
	 * @return an action that calls all Action of this object
	 * @see #callAllAction()
	 */
	public Action getAction() {
		return new Action(name + "'s Action", this) {

			@Override
			public void action() {
				go.callAllAction();

			}
		};
	}

	/**
	 * @return a draw that calls all Draw of this object
	 * @see #callAllDraw(Graphics)
	 */
	public Draw getDraw() {

		return new Draw(name + "'s Draw", this) {

			@Override
			public void draw(Graphics g) {
				go.callAllDraw(g);

			}
		};
	}

	/**
	 * creates a new command list
	 */
	public void createCommandList() {
		command = new CommandList("GameObject CommandList"); // commands :>
	}

	/**
	 * creates a new action list
	 */
	public void createActionList() {
		action = new ActionList("GameObject ActionList"); // commands <:
	}

	/**
	 * creates a new rule list
	 * 
	 * protected void createRuleList() { rule = new
	 * ActionList("GameObject RulesList"); }
	 */
	/**
	 * creates a new draw list
	 */
	public void createDrawList() {
		draw = new DrawList("GameObject DrawList"); // commands (:<
	}

	@Override
	public Command getCommand() {

		return new Command(this.name + "Command", this) {

			@Override
			protected void draw(Graphics g) {
				go.getDraw().callDraw(g);
			}

			@Override
			protected void action() {
				go.getAction().callAction();

			}
		};
	}

}// end GameObject

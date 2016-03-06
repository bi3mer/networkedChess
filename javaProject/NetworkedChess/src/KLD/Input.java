package KLD;

/*READ ME:
 * Last update:  June 12, 2013
 * 
 * Definition: 
 * 	Input class creates object that handles KeyListener, MouseListener, MouseMotionListener. 
 * 	
 * Purpose: 
 * The main purpose of the class is to keep track of every single key on your keyboard using boolean types.
 * 	
 * How to use: 
 *  <KeyCode>
 *  You either use Input.[name of the key] or use KeyEvent.VK_[name of key] to get the keyCode
 * 
 * 
 *  <KEYS AND KEYBOARD>
 * 1-keyIsDown(keyCode : int) returns true as long as the passed keyCode is pressed or down
 * 2-keyIsUp (keyCode : int) returns the opposite of keyIsDown(keyCode)
 * 3-keyIsClicked(keyCode: int) return true only the first time the key is down, and resets when key is released
 * 
 * 4-anyKeyIsDown() returns true if any key is down. 
 * 5-anyKeyIsUp() returns !anyKeyIsDown
 * 6-anyKeyIsClicked() return any clicked key. 
 * 
 * 7-getKeyString() returns the last key pressed as a string
 * 
 * 
 *  <MOUSE> 
 * 1-point : Point keeps track of the mouse point. 
 * 2-mouseIsDown() returns true if the mouse is down. 
 * 3-mouseIsUp() returns !mouseIsDown()
 * 4-mouseClicked() returns true when you click.. same as keyIsClick
 *  
 *  
 *  Future: TODO
 * 		rename mouseClicked to mouseIsSingleRightClicked. 
 *  	add mouseIsDoubleRightClicked
 * 		add mouseIsSingleLeftClicked
 * 		add mouseIsDoubleLeftClicked
 * 		add mouseIsThirdClicked
 * 		add mouseIsScrollingUp
 * 		add mouseIsScrollingDown
 *      add mouseIsMoving
 *      add mouseIsNotMoving
 *      
 * old random description. no need to read them, but I'm keeping them as a memory :) 
 * Using this class will allow you to interact with keys and mouse
 * ###############################################################
 * This class is mainly used for games that uses keys
 * Feel free to use it
 * It is created by KLD and I got the idea from Slick 
 * ###############################################################
 * Three main methods were created : 
 * keyIsDown(int keyCode), keyIsClicked(int keyCode), keyIsUp(int keyCode) : boolean
 * #########################3#####################################
 * One additional function is also provided which is : setListener(Component c): void
 *  use Event.KeyCode(VK_"anyKey") : int    and pass it to the three main functions. 
 */

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

	//Keys
	public final static int UP = KeyEvent.VK_UP; 
	public final static int DOWN = KeyEvent.VK_DOWN; 
	public final static int LEFT = KeyEvent.VK_LEFT; 
	public final static int RIGHT = KeyEvent.VK_RIGHT; 
	
	public final static int A = KeyEvent.VK_A; 
	public final static int B = KeyEvent.VK_B; 
	public final static int C = KeyEvent.VK_C; 
	public final static int D = KeyEvent.VK_D; 
	public final static int E = KeyEvent.VK_E; 
	public final static int F = KeyEvent.VK_F; 
	public final static int G = KeyEvent.VK_G; 
	public final static int H = KeyEvent.VK_H; 
	public final static int I = KeyEvent.VK_I; 
	public final static int J = KeyEvent.VK_J; 
	public final static int K = KeyEvent.VK_K; 
	public final static int L = KeyEvent.VK_L; 
	public final static int M = KeyEvent.VK_M; 
	public final static int N = KeyEvent.VK_N; 
	public final static int O = KeyEvent.VK_O; 
	public final static int P = KeyEvent.VK_P; 
	public final static int Q = KeyEvent.VK_Q; 
	public final static int R = KeyEvent.VK_R; 
	public final static int S = KeyEvent.VK_S; 
	public final static int T = KeyEvent.VK_T; 
	public final static int U = KeyEvent.VK_U; 
	public final static int V = KeyEvent.VK_V; 
	public final static int W = KeyEvent.VK_W; 
	public final static int X = KeyEvent.VK_X; 
	public final static int Y = KeyEvent.VK_Y; 
	public final static int Z = KeyEvent.VK_Z; 
	public final static int N0 = KeyEvent.VK_0; 
	public final static int N1  = KeyEvent.VK_1; 
	public final static int N2 = KeyEvent.VK_2; 
	public final static int N3 = KeyEvent.VK_3; 
	public final static int N4 = KeyEvent.VK_4; 
	public final static int N5 = KeyEvent.VK_5; 
	public final static int N6 = KeyEvent.VK_6; 
	public final static int N7 = KeyEvent.VK_7; 
	public final static int N8 = KeyEvent.VK_8; 
	public final static int N9 = KeyEvent.VK_9; 
	public final static int NP0 = KeyEvent.VK_NUMPAD0; 
	public final static int NP1 = KeyEvent.VK_NUMPAD1; 
	public final static int NP2 = KeyEvent.VK_NUMPAD2; 
	public final static int NP3 = KeyEvent.VK_NUMPAD3; 
	public final static int NP4 = KeyEvent.VK_NUMPAD4; 
	public final static int NP5 = KeyEvent.VK_NUMPAD5; 
	public final static int NP6 = KeyEvent.VK_NUMPAD6; 
	public final static int NP7 = KeyEvent.VK_NUMPAD7; 
	public final static int NP8 = KeyEvent.VK_NUMPAD8; 
	public final static int NP9 = KeyEvent.VK_NUMPAD9; 
	public final static int F1 = KeyEvent.VK_F1; 
	public final static int F2 = KeyEvent.VK_F2; 
	public final static int F3 = KeyEvent.VK_F3; 
	public final static int F4 = KeyEvent.VK_F4; 
	public final static int F5 = KeyEvent.VK_F5; 
	public final static int F6 = KeyEvent.VK_F6; 
	public final static int F7 = KeyEvent.VK_F7; 
	public final static int F8 = KeyEvent.VK_F8; 
	public final static int F9 = KeyEvent.VK_F9; 
	public final static int F10 = KeyEvent.VK_F10; 
	public final static int F11 = KeyEvent.VK_F11; 
	public final static int F12 = KeyEvent.VK_F12; 
	
	public final static int DELETE = KeyEvent.VK_DELETE; 
	public final static int TAB = KeyEvent.VK_TAB; 
	public final static int CAPS_LOCK = KeyEvent.VK_CAPS_LOCK; 
	public final static int CONTROL = KeyEvent.VK_CONTROL; 
	public final static int ALT = KeyEvent.VK_ALT; 
	public final static int COMMA = KeyEvent.VK_COMMA; 
	public final static int PERIOD = KeyEvent.VK_PERIOD; 
	public final static int BACK_SPLASH = KeyEvent.VK_BACK_SLASH; 
	public final static int COLON = KeyEvent.VK_COLON; 
	public final static int DECIMAL = KeyEvent.VK_DECIMAL; 
	public final static int ESCAPE = KeyEvent.VK_ESCAPE; 
	public final static int ADD = KeyEvent.VK_ADD; 
	public final static int EQUALS = KeyEvent.VK_EQUALS; 
	public final static int BRACERIGHT = KeyEvent.VK_BRACERIGHT; 
	public final static int BRACELEFT = KeyEvent.VK_BRACELEFT; 
	public final static int SLASH = KeyEvent.VK_SLASH; 
	public final static int MINUS = KeyEvent.VK_MINUS;
	public final static int SHIFT = KeyEvent.VK_SHIFT; 
	public final static int SPACE = KeyEvent.VK_SPACE; 
	public final static int ENTER = KeyEvent.VK_ENTER; 
	public static final int BACKSPACE = KeyEvent.VK_BACK_SPACE;
	
	private static boolean[] keyPressed;
	private static boolean[] keyClicked;
	private static boolean mouseClicked;
	private static boolean mousePressed;

	private static boolean anyKeyPressed;
	private static boolean anyKeyClicked;
	/**
	 * 
	 * Hold the mouse's current point
	 */
	public static Point point;

	String lastKeyString; 
	
	private int keyCode; 
	
	public Input() {
		// 400 is just an assumption of the maximum number of keys. 
		keyPressed = new boolean[600];
		keyClicked = new boolean[600];

		point = new Point(6, 0);
		// Initializing
		for (int i = 0; i < 400; i++) {
			keyPressed[i] = false;
			keyClicked[i] = false;
		}

		mouseClicked = false;
		mousePressed = false;

		anyKeyPressed = false;
	}

	public Input(Component c) {
		this();
		addListenerTo(c); //TODO do I really need this? Does it hurt? 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyCode = e.getKeyCode();
		
		if (!keyPressed[keyCode])
			keyClicked[keyCode] = true;
		if (!anyKeyPressed)
			anyKeyClicked = true;

		keyPressed[keyCode] = true;
		anyKeyPressed = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyPressed[keyCode] = false;
		keyClicked[keyCode] = false;
		anyKeyPressed = false;
		anyKeyClicked = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Returns true with is key is down and false with the key is up.
	// This method is usable for motion in games
	public boolean keyIsDown(int keyCode) {
		return keyPressed[keyCode];
	}

	// Returns true with is key is up and false with the key is down.
	public boolean keyIsUp(int keyCode) {
		return !keyPressed[keyCode];
	}

	// This method returns true only the first time the key is hit.
	// It's useful when you what to play a sound with a key
	public boolean keyIsClicked(int keyCode) {

		if (keyClicked[keyCode]) {
			keyClicked[keyCode] = false;
			return true;
		}

		return false;
	}

	// You can use this method to add Listener
	public void addListenerTo(Component c) {
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		point = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		point = e.getPoint();

	}

	@Override 
	public void mouseClicked(MouseEvent e) {
		point = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		point = e.getPoint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		point = e.getPoint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!mousePressed)
			mouseClicked = true;
		mousePressed = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked = false;
		mousePressed = false;
	}

	public boolean mouseIsClicked() {
		if (mouseClicked) {
			mouseClicked = false;
			return true;
		} else
			return false;
	}
	
	public boolean mouseIsDown(){
		return mousePressed;
	}
	public boolean mouseIsUp(){
		return !mousePressed;
	}
	public boolean anyKeyIsPressed() {
		return anyKeyPressed;
	}

	public boolean anyKeyIsClicked() {
		return anyKeyClicked;
	}
	
	public String getKeyString(){
		return KeyEvent.getKeyText(keyCode);
	}

	
	
	
	
	
	@Override 
	public String toString(){
		return "YOLO";
	}

	
	//TODO this should be developed more such that: return if click and release occur inside Shape
	public boolean mouseIsClicked(Shape s) {
		
		if(s.contains(point))
			if(mouseIsClicked())
				return true;
		return false;
	}
	
	public boolean isIn(Shape s)
	{
		return (s.contains(point)); 
	}
	
	
	
	
}// end class

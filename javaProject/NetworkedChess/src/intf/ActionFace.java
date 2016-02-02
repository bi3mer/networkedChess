package intf;

import KLD.cmd.Action;
import KLD.cmd.ActionList;


public interface ActionFace {
	
/**
 * @return <code>action</> object that contains the classes action <- WHAT!!
 */
	public Action getAction(); 
	
	/**
	 * @param a adds an action to an action List
	 */
	public void addAction(Action a);
	
	public void removeAction(int id);
	
	public void createActionList();
	
	public void callAllAction();
	
}

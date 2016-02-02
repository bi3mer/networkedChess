package KLD.cmd;

public class ActionList extends KLDList {

	
	public ActionList(String name) {
		super(name);
	}
	
	public void callAllAction(){
		LinkObject current = first; 
		current = first;     //start at first
		while((current != null)){ //keep going until you reach null 
			Action a = (Action) current; 
			a.callAction();
			current = current.next;  // go to the next one	
		}
	}
	
}

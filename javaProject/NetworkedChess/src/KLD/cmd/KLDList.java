package KLD.cmd;

import KLD.obj.IdObject;
 
//TODO addAt(lo:LinkObject,n:int);


public class KLDList extends IdObject {
	
	/**
	 * Points to the fist element of the list
	 */
	protected LinkObject first;
	/**
	 * Points to the last element in the list 
	 */
	protected LinkObject last ; //currently useless
	/**
	 * temporary holds a current pointer
	 */
	
	/**
	 * Holds an <code>int</code> value of the list's length 
	 */
	protected int length ; 
	
	/**
	 * creates an empty list with name : UnnnamedLoist and prints statement
	 */
	public KLDList(){
		super("UnnammedList");
		length = 0; 
		
		System.out.println("Created a list called " + name );
	}
	/**
	 *  creates a list and set the name of the list to the passed string 
	 * @param s name 
	 */
	public KLDList(String s) {
		super(s);
		length = 0; 
		System.out.println("Created a list called " + name  );
	}

	/**
	 *  adds a link object to the list 
	 * @param lo LinkObject 
	 */
	public void addLinkObject(LinkObject lo){
		lo.owner = this;
		if(length==0){
			first = lo;
			last = lo;
			System.out.println("new first Command " + lo.name+" added to "+name);
		}
		else {
		connect(last, lo);
		last = lo;
		System.out.println("new Command " + lo.name +  " added to "+name);
		}
		
		length++; 
	}
		
	/**
	 * Removes the objects whose id matches one of the linkobjects in the list 
	 * <p>[0] - first object was removed</p>
	 * <p>[1] - last object was removed</p>
	 * <p>[2] - object found and removed</p>
	 * 
	 * @param id linkobject's id
	 */
	public void removeLinkObject(int id){
		System.out.print("Removing command with id" + id+" from " + this.name +" ");
		if(length<1){
			System.out.println(" List is already empty");
			return; 
		}
		if(length==1 && first.id == id){
			System.out.println(" List had one element. Now it's empty");
			first = null;
			length=0; 
			return; 
		}
		
		if(id == first.id){
			first = first.next;
			System.out.println(first.name + " Command removed [0]");
			length--; 
			return; 
		}
		if(id == last.id){
			last = last.previous;
			System.out.println(last.name + " Command removed [1]");
			length--; 
			return; 
		}
		
		LinkObject current = search(id); 
		 if(current == null){
			 System.out.println(" Cannot find Command with id " + id);
			 return; 
		 }
		 
			 System.out.println(current.name + " Command removed [2]");
			 connect(current.previous,current.next );
			 current = null; 
			 length--; 
			 return ;
		 	}
	public void removeLinkObject(LinkObject l){
		if(length==1){
			first = null; 
			return; 
		}
		LinkObject current = getLinkObject(indexOf(l));
		length--; 
		connect(current.previous,current.next );
		current = null; 
	}
	public void removeLinkObject(String name){
		if(length==1){
			first = null; 
			return; 
		}
		LinkObject current = search(name);
		
		if(current == null) //if search fails to find an object
			return; 
		
		System.out.println("KLDList: object found and removed");
		length--; 
		
		if(current.equals(first)) //if we are removing the first element
			first = current.next; //set the second to be the first
		else if(current.equals(last))
			last = current.previous;
		else
			connect(current.previous,current.next );
		
		current = null; //delete the current (kinda)
	}
	
	
	public int indexOf(LinkObject l){
		
		LinkObject current = first; 
		
		int i=0;
		
		while(!(current.equals(l))){
			current = current.next;
			i++;
			if(current==null)
				return -1;
		}
		return i;
	}
	
	

	public LinkObject getLinkObject(int id){
	try{
		LinkObject current = first; 
		 if(id<0)
			 throw new Exception("n must be greater than -1");
			 
		while(current != null){
			if(current.id == id)
				break;
			current = current.next; 
		}
		
		return current;
	}
	catch(Exception e){
		e.printStackTrace();
		return null;
	}
	
	
	}

	public LinkObject search(String name){
		LinkObject current= first; 
		
		try{
		
		while(!(current.name.equals(name))){
			
			current = current.next;
		}
		
		return current; 
		
		}
		catch(Exception e){
			return null;
		}
	}
	public LinkObject search(int ID){
		LinkObject current= first; 
		
		try{
		
		while(!(current.id==ID)){
			
			current = current.next;
		}
		
		return current; 
		
		}
		catch(Exception e){
			return null;
		}
	}
	
	
	
	
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	private void connect(LinkObject a, LinkObject b){
		a.next =b; 
		b.previous = a;
	}
	
	
	public int length(){//TODO use length -_-
		/*
		int n =0; 
		current = first; 
		
		while((current !=null)){
			n++; 
			current = current.next; 
		}
		return n; 
		*/
		return length;
	}
	/**
	 * sets first and last to null. 
	 */
	public void clear(){
		System.out.println(this.name + " is cleared");
		first = null; 
		last = null; 
		length = 0; 
	}
/**
 * return String of the list's name and the name of all objects in it.
 */
	public String toString(){
		String s = "KLDList: " + name + "[";
		
		if(first ==null)
			return s+"]";
		
		s+= first.name ;
		LinkObject current= first; 
		
		while(current != null){
			s+= "," + current.name ;  
		}
			
	
		
		return s + "]";
	}
	
	
}//end class



 
	
	
	
	
	




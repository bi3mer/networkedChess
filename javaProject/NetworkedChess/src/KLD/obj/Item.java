package KLD.obj;


public abstract class Item extends GameObject {

	
	public boolean isNotUsed; 
	public boolean isNotPicked; 
	
	public static final Item empty = new Item(){
		public void init(){}
		public void effect(Player p) {}
		public void consume(Player p) {}
		
			
				
	};

	public Item(){
		super(0,0);
		isNotUsed = true; 
		isNotPicked = true; 
		init();
	}
	
	public abstract void init();
	
	public abstract void effect(Player p);
	
	public abstract void consume(Player p);
	
	
}//end Item

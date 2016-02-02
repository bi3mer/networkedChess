package KLD.util;

public class KLDMathTree {

	private String str; 
	
	boolean motherTree; 
	
	KLDMathTree mama; 
	KLDMathTree left; 
	KLDMathTree right; 
	String o; 
	
	public KLDMathTree(String s){
		str = s; 
		motherTree = true;
		
		String leftPart= "";//TODO
		String rightPart = ""; //TODO
		
		//get the operator
		
		left = new KLDMathTree(leftPart, false);
		right = new KLDMathTree(rightPart, false);
	}
	private KLDMathTree(String s, boolean isMother){
		this(s);
		motherTree = isMother;
	}
	
}

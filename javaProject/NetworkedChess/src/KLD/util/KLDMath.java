package KLD.util;

import java.util.Scanner;

public class KLDMath {
	
	final static boolean SHOWSTEPS = false; 
	static int numberCounter =0; 
	
		public static void main(String[] argv) {
		 
			final Scanner input = new Scanner(System.in); 
			
			while(true){
			System.out.println("Enter an equation");
			 
			
			solve(input.nextLine());
			 
			}
		
		
			//input.close();
			
	}//end main
	
	static void solve(String s){
		
		s = s.replaceAll(" ", ""); 
		
			
		System.out.println("Answer is "+  calc(s));
		
	}
	
	
	
	static int calc(String s){
		s +=" "; 
		
		if(SHOWSTEPS)
			System.out.println("Solving "+ s);
		
		//get length of numbers. 
		String temp  = s;
		temp =temp.replaceAll("-?\\d+", "_");
		
		//numbers 
		 int[] numberList = new int[temp.split("_").length-1]; 
		 System.out.println("Length of numberList: " + (temp.split("_").length-1)); 
		int np=0; 
		
		//operators
		char[] operatorList = new char[temp.split("_").length-2]; 
		int op =0; 
		
		//chars 
		char[] charList = s.toCharArray();
		int cp = 0; 
		char current = charList[cp]; 
		
		boolean niga = false; 
		
		if(current=='-'){
			niga = true; 
			current = charList[++cp];
		}
		
		
		while(current!=' '){
			
			if(current=='('){
				String sec=  ""; 
				cp++; 
				while(current != ')'){
					sec += current; 
					current = charList[++cp]; 
				}
				
				numberList[++np] =  calc(sec);
				
			}//end if () 
			
			
				int sum=0; 
				
				if(isDigit(current)){
					//System.out.println("my first digit= "+ current);
					sum= Integer.parseInt(""+current);
					current = charList[++cp];
					
					while(isDigit(current)){
					
						sum *= 10;
						sum+=Integer.parseInt(""+current);
						
				
						current = charList[++cp];
						
					}
					///System.out.println("found number= "+ sum);
					numberList[np++] = (niga)? -sum: sum; 
					numberCounter++; 
					niga = false; 
				}
				
				if(isOpetator(current)){
					
					operatorList[op++] = current;
					
					current = charList[++cp];
					
					if(charList[cp]=='-'){
						niga = true; 
						current = charList[++cp];
					}
					
					
				}
			
			
		}//end awesome while
		/*
		if(s.contains("(")){
			if(SHOWSTEPS)
			System.out.println("found  "+ "(");
			
			int firstIndex = s.indexOf("("); 
			int lastIndex =  s.indexOf( ")"); 
			
			String firstPart = s.substring(0, firstIndex);
			String middlePart = s.substring(firstIndex+1, lastIndex);
			String lastPart = ""; 
			if(lastIndex != s.length())
				 lastPart = s.substring(lastIndex+1, s.length());
			//2 + 4 * (2+3)
			if(SHOWSTEPS){
				System.out.println("firstPart "+ firstPart);
				System.out.println("middlePart "+ middlePart);
				System.out.println("lastPart "+ lastPart);
			}
			
			
			return calc(firstPart+ calc(middlePart)+lastPart);
			
		}
	
		else if(s.contains("*") || s.contains("/") || s.contains("+") || s.contains("-")){
			
			if(SHOWSTEPS)
			System.out.println("found an oporator ");
			
			String sum ;
			String sub; 
			String o = "" ; 
			
			if(s.contains("*")){
				o="*";
			}else if (s.contains("/")){
				o="/";
			}else if (s.contains("-")){
				o="-";
			}else if (s.contains("+")){
				o="+";
			}
			
			int indexOfM = s.indexOf(o);
			
			if(SHOWSTEPS)
				System.out.print("Operation " + o);
			
			String n1 = matchNumberRev(s.substring(0,indexOfM).toCharArray());
			String n2 = matchNumber(s.substring(indexOfM+1, s.length()).toCharArray());
	
			
			
			sub = n1+o+n2; 
			sum = getNumber(n1, n2, o); 
			try{
			int i = s.indexOf(sub);
			//System.out.println("sub " +sub);
			//System.out.println("sum " +sum);

			s = s.substring(0, i) + sum + s.subSequence(i+sub.length(), s.length());
			
			//System.out.println("\nnew s " +s);
			return calc(s); 
	
			}
			catch(Exception e){
				System.out.print( "I'm am error: " + s); 
			}
		}	
		
		*/
		
		
		
		System.out.println( "I'm done" ); 
		System.out.print( "Numbers = " ); 
		for(int i :  numberList){
			System.out.print(i + ",");
		}
		System.out.println();
		
		System.out.print( "Ops: " ); 
		for(char i :  operatorList){
			System.out.print(i + ",");
		}
		System.out.println();
		
		//calculate the total 
		int sum=numberList[0];
	
		for(int i=1; i< numberList.length;i++){
			sum = getNumber(sum, numberList[i], operatorList[i-1]);
		}
		
		
		
		return  sum; 
	}
	
	
	
	
	
	
	static String matchNumber(char[] c){
		int sum = 0; 
		for(int i=0; i< c.length; i++ ){
			if(Character.isDigit(c[i])){
				sum *=  10; 
				sum += Integer.parseInt(""+c[i]); 
				
			}
			else { 
				if(SHOWSTEPS)
					System.out.println("Number on right=" + sum);
				return ""+sum;
			}
			
		}
		if(SHOWSTEPS)
			System.out.println("Number on right=" + sum);
		return ""+sum; 
	}
	
   static String matchNumberRev(char[] c){
	   int p = 1; 
		int sum = 0; 
		for(int i=c.length-1; i>=0; i-- ){
			if(Character.isDigit(c[i])){
				sum += p* Integer.parseInt(""+c[i]); 
				p*=10; 
			}
			else {
				if(SHOWSTEPS)
					System.out.println("Number on left=" + sum);
				return ""+sum;
			}
			
		}
		if(SHOWSTEPS)
			System.out.println("Number on left=" + sum);
		return ""+sum; 
	}
	
	
	static int getNumber(int n1, int n2,  char o){
		
		
		int sum ; 
		if(o=='+')
			sum=  n1+n2; 
		else if(o=='-')
			sum=  n1-n2;
		else if(o=='*')
			sum=  n1*n2; 
		else 
			sum=  n1/n2; 
		if(SHOWSTEPS)
			System.out.println("n1, O, n2, sum, "  + n1 + o + n2 + "="+ sum);
		return sum; 
	}
	
	
	static boolean isDigit(char c){
		return Character.isDigit(c);
	}
	
	static boolean isOpetator(char c){
		return (c=='+' | c=='-' || c=='*' | c=='/');
	}
	
	
	
}//end class

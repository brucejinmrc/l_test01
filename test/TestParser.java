package test;
import java.util.*;

public class TestParser {

	public static void main(String[] args) {
		String s = "&F1/2+100 * &F2 - F3";
		Calc calc = new Calc("CALCULA001", s, 10, "02");
		
		List fields = new ArrayList();
		fields.add(new Field("F1", 10, "02"));
		fields.add(new Field("F2", 9, "02"));
		fields.add(new Field("F3", 6, "00"));
		
		fields.add(new Field("C1", 10, "  "));
		fields.add(new Field("C2", 20, "  "));
		fields.add(new Field("C3", 30, "  "));
		
		String error = parse(calc, fields);
		
	}
	
	static String parse(Calc c, List fields) {
	
	    //do someting
	    return null; //no error!
	}	
 
}

class Calc {
 
		String message;
		boolean hasError;
 
		String	CALC;
		String  FLDDS;
		String	STRING;
		String  STRINGds;
		int	FLDLN;
		String  FLDDC;
		
		Calc (String name,  String calcString,
			int len, String dc) {
			this.CALC = name;
			this.STRING = calcString;
			this.FLDLN = len;
			this.FLDDC = dc;		
		}
 
}	

class Field {

	public String FIELD; //I,R,S,M
 
	public int FLDLN; //I,R,S,
	public String FLDDC; 
	
	Field (String name, int len, String dc) {
		this.FIELD = name;
		this.FLDLN = len;
		this.FLDDC = dc;
	}
}
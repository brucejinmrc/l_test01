/**
 * 
 */
package lucene;

/**
 * Query text
 * @author bruce
 *
 */
public class TextQuery {

	/***********************************************************
	 * caseSensitive: "2"-Change the search value to uppercase</option>
                      "1"-No case conversion
                      "0"-Change both the search value and the DB field to uppercase using the UPPER keyword
	 * Ckeck if text pass the test defined by relation and value.
	 * Return true if test is satisfied.
	 ***********************************************************/
	boolean test(String text, String relation, String value, int caseSensitive) {
		
		if (text == null || value == null) {
			return false;
		}
		
		text = text.trim();
		value = value.trim();
		
		switch (caseSensitive) {
		case 2: 
			value = value.toUpperCase();
		case 0:
			value = value.toUpperCase();
			text = text.toUpperCase();
		}
		
		boolean pass = true;
		
		if (relation.equals("EQ")) {
			if (!text.equals(value)) {
				pass = false;
			}
		} 
		else if (relation.equals("NE")) {
			if (text.equals(value)) {
				pass = false;
			}
		} 
		else if (relation.equals("GE")) {//>=
			if (text.compareTo(value) < 0 || text.equals(value)) {
				pass = false;
			}
		}
		else if (relation.equals("GT")) { //>
			if (text.compareTo(value) < 0 ) {
				pass = false;
			}
		} 
		else if (relation.equals("LE")) {
			if (text.compareTo(value) > 0 || text.equals(value)) {
				pass = false;
			}
		} 
		else if (relation.equals("LT")) {
			if (text.compareTo(value) > 0) {
				pass = false;
			}
		} 
		else if (relation.equals("LS")) { //in the list
			 pass = testLS(text, value);
		} 
		else if (relation.equals("NS")) { // not in the list
			 pass = testNS(text, value);
		} 
		else if (relation.equals("RG") ) { //in the range
			 pass = testRG(text, value, relation);
		} 
		else if (relation.equals("NG")) { // not in the range
			 pass = testRG(text, value, relation);
		} 
		else if (relation.equals("CT")) { // contain
			 if (text.indexOf(value) < 0) {
				 pass = false;
			 }
		} 
		else if (relation.equals("SW")) { // start with
			 if (!text.startsWith(value)) {
				 pass = false;
			 }
		} 
		else if (relation.equals("CA")) { // contain all
			 pass = testCA(text, value);
		} 
		else if (relation.equals("CO")) { // contain any
			 pass = testCO(text, value);
		} 
		else if (relation.equals("IN")) { // is null
			 if (text.length() > 0) {
				 pass = false;
			 }
		} 
		else if (relation.equals("NN")) { // is not null
			if (text.length() == 0) {
				 pass = false;
			 }
		} 

		return pass;
	}
	
	/***********************************************************
	 * Test in the list (LS)
	 ***********************************************************/
	boolean testLS(String text, String value) {
		boolean itIs = false;
		String[]  values = value.split(" ");
		for (String val : values) {
			if (val.trim().equals(text)) {
				itIs = true;
				break;
			}
		}
		return itIs;
	}
	/***********************************************************
	 * Test not in the list (NS)
	 ***********************************************************/
	boolean testNS(String text, String value) {
		boolean itIs = true;
		String[]  values = value.split(" ");
		for (String val : values) {
			if (val.trim().equals(text)) {
				itIs = false;
				break;
			}
		}
		return itIs;
	}
	/***********************************************************
	 * Test in the range (RG)
	 * test if text is inside 'xxx ... yyy'
	 ***********************************************************/
	boolean testRG(String text, String value, String relation) {
		boolean itIs = false;
		int pos1 = value.indexOf(" ");
		int pos2 = value.lastIndexOf(" ");
		if (pos1 < 0) {
			return false;
		}
		String val1 = value.substring(0,pos1);
		String val2 = value.substring(pos2).trim();

		int comp1 = text.compareTo(val1);
		int comp2 = text.compareTo(val2);
		
		if (relation.equals("RG") && comp1 > 0 && comp2 < 0) {
			itIs = true;
		}
		
		if (relation.equals("NG") && (comp1 < 0 || comp2 > 0)) {
			itIs = true;
		}
		return itIs;
	}
	
	/***********************************************************
	 * Test contains all (CA)
	 ***********************************************************/
	boolean testCA(String text, String value) {
		boolean itIs = true;
		String[]  values = value.split(" ");
		for (String val : values) {
			if (text.indexOf(val.trim()) < 0) {
				itIs = false;
				break;
			}
		}
		return itIs;
	}
	
	/***********************************************************
	 * Test contains any (CO)
	 ***********************************************************/
	boolean testCO(String text, String value) {
		boolean itIs = false;
		String[]  values = value.split(" ");
		for (String val : values) {
			if (text.indexOf(val.trim()) >= 0) {
				itIs = true;
				break;
			}
		}
		return itIs;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextQuery test = new TextQuery();
		System.out.println(test.test("a", "EQ", "a",1));
		System.out.println(test.test("c", "GE", "b",1));
		System.out.println(test.test("a", "GT", "b",1));
		System.out.println(test.test("ccc", "LS",   "b ccc  kk kkkk pp", 1));
		System.out.println(test.test("ccc", "LS",   "b cc  kk kkkk pp", 1));
		System.out.println(test.test("ccc", "NS",   "b ccc  kk kkkk pp", 1));
		System.out.println(test.test("ccc", "NS",   "b cc  kk kkkk pp", 1));
		System.out.println("RG:   " + test.test("r", "RG",   "a x", 1));
		System.out.println("NG:   " + test.test("z", "NG",   "a x", 1));
		System.out.println("CT:   " + test.test("abcdeee", "CT",   "ab", 1));
		System.out.println("SW:   " + test.test("abcdeee", "SW",   "zab", 1));
		System.out.println("CA:   " + test.test("abcdeee", "CA",   "ab ee cd ", 1));//T
		System.out.println("CA:   " + test.test("abcdeee", "CA",   "ab ee cd aa", 1));//F
		System.out.println("CO:   " + test.test("abcdeee", "CO",   "xx yy cd zz", 1));//T
		System.out.println("CO:   " + test.test("abcdeee", "CO",   "xx yy hh zz", 1));//F
		System.out.println("CO:   " + test.test("abcdeee", "CO",   "xx yy CD zz", 1));//F - case
		System.out.println("CO:   " + test.test("abcdeee", "CO",   "xx yy CD zz", 0));//T - case
	}

}

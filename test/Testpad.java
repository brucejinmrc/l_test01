package test;
//import java.util.*;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Testpad {
 
    
	public static void main(String[] args) {
		
		 
		String ip0 = "opop <map klklkl>OPOPOPOP OPOPOPOPO t</map>jjkjkjk\nklklklk";
		 
		String str1 = cutSection(ip0, "<map", "</map>");
		System.out.println(str1);
		
		DecimalFormat df = new DecimalFormat();
		Number num = df.parse("1.2300E2", new ParsePosition(3));
		int ans = num.intValue();
		System.out.println(ans); // This prints 123
		
		double ss =222231220.0000001;
		BigDecimal bb = new BigDecimal(ss);
		bb.setScale(7, BigDecimal.ROUND_HALF_UP);
		NumberFormat formatter = new DecimalFormat("###.#######");  
		   
		String f = formatter.format(bb);  
		System.out.println(f);// output --> 0.00007  
		
		Color red = Color.green ;
		String hexStr = Integer.toHexString( red.getRGB() );
		System.out.println(hexStr);// output --> 0.00007  
 	}
	/**
	 * Cut a sction from a string 
	 * @param str the string
	 * @param tag1 example "<map"
	 * @param tag2 example "</map>"
	 * @return return xxxx yyyy from 'xxxx<map .....>.....</map>yyyy'
	 */
	public static String cutSection(String str, String tag1, String tag2) {
		String str1 = str;
		
		String sec = tag1 + ".*?" + tag2;
		Pattern pattern0 = Pattern.compile( sec );
		Matcher m2 = pattern0.matcher( str );
 
		if (m2.find()) {
			int start = m2.start();
			int end = m2.end();
			String seg = str.substring(start, end);
			str1 = StringUtils.replace(str, seg, " ");
		}
		
		return str1;
	}
	
	 
}

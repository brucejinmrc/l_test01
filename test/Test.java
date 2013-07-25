/*
 * Test01.java   Created on May 1, 2007, 4:03:01 PM
 *
 */
package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;


//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.time.DateUtils;


public class Test {
   
	@uuu
	public String test;
	
    public static void main(String[] args) {
    	char[] cs0 = {'\u2018','\u2019','\u201C','\u201D', };
    	char[] csr = {'\'','\'','"','"', };
    	String ppp = "ppppdon’t and don’t";
    	String ppp1 = StringUtils.replaceChars(ppp, "\u2018\u2019\u201C\u201D", "''\"\"");
    	System.out.println("-"  + ppp);
    	System.out.println("-"  + ppp1);

    	
    	Date today = new Date();
    	Date d2 = DateUtils.addDays(today, -1);
    	Date d3 = DateUtils.addMonths(today, -1);
    	System.out.println(d2 + "---" + d3);
    	URL url;
		try {
			url = new URL("http://www.mrc-productivity.com/index.html");
			String mrc = IOUtils.toString(url);
			String mrc2 = IOUtils.toString(url);
		//	System.out.println(mrc);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String ss = "<td class=\"pppp\">oop <p></td>\n m,m <td>oop111 </td>\n uiui <td>222oop </td>\n hhh <td>o333 yyop </td>\n";
		String[] ss2 = StringUtils.substringsBetween(ss, "<td ", "</td>");
		System.out.println(Arrays.toString(ss2));
		String ss3 = StringUtils.substringBetween(ss, "class=\"", "\"");
		System.out.println(ss3);
		
		String sss = "2..";
		boolean isnum = NumberUtils.isNumber(sss);
		System.out.println(isnum);
		return;
    }

}

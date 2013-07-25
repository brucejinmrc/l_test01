/**
 * 
 */
package test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.json.simple.JSONObject;

/**
 * @author bruce
 *
 */
public class JSONTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();

	}
	
	static void test() {
		  StringWriter out = new StringWriter();
	        
		  JSONObject obj = new JSONObject();
		  HashMap<String, Object> m1 = new HashMap<String, Object>();
		 // LinkedList l1 = new LinkedList();
		  List<String> l1 = new ArrayList<String>();
		  obj.put("k1", "v1");
		  obj.put("k2", m1);
		  obj.put("k3", l1);
		  m1.put("mk1", "mv1");
		  l1.add("lv1");
		  l1.add("lv2");
		  m1.put("mk2", l1);
		        
		  try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		  System.out.println("jsonString:");
		  System.out.println(out.toString());
		  String jsonString = obj.toJSONString();
		  System.out.println(jsonString);
	}

}

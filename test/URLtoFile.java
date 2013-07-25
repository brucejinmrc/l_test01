package test;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

public class URLtoFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://localhost:8011/mrcjava/license.html";
		try {
			URL ur = new URL(url);
			File urlf = FileUtils.toFile(ur);
			System.out.println(urlf);
		} catch (MalformedURLException e) {
			 
			e.printStackTrace();
		}
		
		new URLtoFile().test();

	}
	
	void test() {
		try {
	        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	        System.out.println("XXXXX" + file.getAbsolutePath());
	        //print XXXXXC:/jjjjj/my.jar
	    } catch (Exception e1) {

	        e1.printStackTrace();
	    } 

	}

}
